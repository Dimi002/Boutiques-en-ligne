package com.ibrasoft.storeStackProd.sms.services.impl;

import com.ibrasoft.storeStackProd.sms.models.MerchantSMSModel;
import com.ibrasoft.storeStackProd.sms.models.PhoneNumberModel;
import com.ibrasoft.storeStackProd.sms.services.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;
import com.google.common.net.HttpHeaders;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SmsServiceImpl implements SmsService {

    private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    @Value("${app.bulk_sms_base_url}")
    private String bulk_sms_base_url;

    @Value("${app.bulk_sms_merchant_key}")
    private String bulk_sms_merchant_key;

    @Override
    public void sendSms(String phone, String message) {
        MerchantSMSModel merchantSms = new MerchantSMSModel();
        List<PhoneNumberModel> phoneNumbers = new ArrayList<>();
        PhoneNumberModel buyerPhone = this.getBuyerPhoneNumber(phone);
        if (buyerPhone != null) {

            // Initialisation de informations du message à envoyer
            phoneNumbers.add(buyerPhone);
            merchantSms.setContacts(phoneNumbers);
            merchantSms.setMerchantKey(bulk_sms_merchant_key);
            merchantSms.setMessage(message);

            WebClient client = WebClient.builder()
                    .baseUrl(bulk_sms_base_url)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultUriVariables(Collections.singletonMap("url", bulk_sms_base_url))
                    .build();
            UriSpec<RequestBodySpec> uriSpec = client.post();
            RequestBodySpec bodySpec = uriSpec.uri("/api/v1.0/merchant/sendsms")
                    .contentType(MediaType.APPLICATION_JSON);
            RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue(merchantSms);
            headersSpec.header(
                    HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                    .acceptCharset(StandardCharsets.UTF_8)
                    .ifNoneMatch("*")
                    .ifModifiedSince(ZonedDateTime.now())
                    .retrieve();
            Mono<Object> response = headersSpec.exchangeToMono(res -> {
                if (res.statusCode()
                        .equals(HttpStatus.OK)) {
                    return res.bodyToMono(Object.class);
                } else if (res.statusCode()
                        .is4xxClientError()) {
                    return Mono.just("Error response");
                } else {
                    return res.createException()
                            .flatMap(Mono::error);
                }
            });

            response.subscribe(res -> {
                logger.info("-----------------------------------------------------------\n"
                        + "Response from 5Care when sending message to user for appointment notification. \n"
                        + "Response from 5Care server: " + res.toString() + "\n"
                        + "-----------------------------------------------------------");
            });
        }
    }

    public PhoneNumberModel getBuyerPhoneNumber(String phone) {
        if (!phone.trim().isEmpty()) {
            String defaultCodePostal = "+237";
            String codePostalFR = "+33";
            String codePostalCM = "+237";
            String codePostalBE = "+32";

            if (phone.length() == 9 && !phone.startsWith("+") && !phone.startsWith("0")) {
                return new PhoneNumberModel(defaultCodePostal, phone);
            }
            if (phone.length() == 10 && phone.startsWith("0")) {
                return new PhoneNumberModel(codePostalFR, phone.substring(1));
            }
            if (phone.length() == 11 && phone.startsWith("33")) {
                return new PhoneNumberModel(codePostalFR, phone.substring(2));
            }
            if (phone.length() == 11 && phone.startsWith("32")) {
                return new PhoneNumberModel(codePostalBE, phone.substring(2));
            }
            if (phone.length() == 12 && phone.startsWith("+33")) {
                return new PhoneNumberModel(codePostalFR, phone.substring(3));
            }
            if (phone.length() == 12 && phone.startsWith("+32")) {
                return new PhoneNumberModel(codePostalBE, phone.substring(3));
            }
            if (phone.length() == 12 && phone.startsWith("237")) {
                return new PhoneNumberModel(codePostalCM, phone.substring(3));
            }
            if (phone.length() == 13 && phone.startsWith("0032")) {
                return new PhoneNumberModel(codePostalBE, phone.substring(4));
            }
            if (phone.length() == 13 && phone.startsWith("0033")) {
                return new PhoneNumberModel(codePostalFR, phone.substring(4));
            }
            if (phone.length() == 13 && phone.startsWith("+237")) {
                return new PhoneNumberModel(codePostalCM, phone.substring(4));
            }
            if (phone.length() == 14 && phone.startsWith("00237")) {
                return new PhoneNumberModel(codePostalCM, phone.substring(5));
            }
            return null;
        }
        return null;
    }
}
