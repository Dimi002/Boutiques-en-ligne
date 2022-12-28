package com.dimsoft.clinicStackProd.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static String LOCAL_STORAGE = "Storage",
            LOCAL_IMAGE_STORAGE = "Storage/Images";

    public static void uploadFile() {

    }

    public static void createFolderIfNotExists(String dirName) throws SecurityException {
        File theDir = new File(dirName);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
    }

    public static void saveToFile(InputStream inStream, String target) throws IOException {
        OutputStream out = null;
        int read = 0;
        byte[] bytes = new byte[1024];
        out = new FileOutputStream(new File(target));
        while ((read = inStream.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        out.flush();
        out.close();
    }

    public static String generateRandomCode() {
        Random rand = new Random();
        String code = "";
        for (int i = 0; i < 4; i++) {
            char c = (char) (rand.nextInt(60) + 69);
            code += c;
        }
        return Base64.getEncoder().withoutPadding().encodeToString(code.getBytes());
    }

    public static Object getObjectFromJson(String jsonObject, Class<?> objectClass) {
        try {
            Object obj;
            final GsonBuilder builder = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            final Gson gson = builder.create();
            obj = gson.fromJson(jsonObject, objectClass);
            return obj;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getJsonFromObject(Object obj, Class<?> ojectClass) {
        try {
            String json = null;
            final GsonBuilder builder = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            final Gson gson = builder.create();
            json = gson.toJson(obj, ojectClass);
            return json;
        } catch (Exception e) {
            String error = "Errors while converting";
            String json = null;
            final GsonBuilder builder = new GsonBuilder();
            final Gson gson = builder.create();
            json = gson.toJson(error, String.class);
            e.printStackTrace();
            return json;
        }
    }

    public static String generateRandomUserPassword() {
        Random rand = new Random();
        String password = "";
        for (int i = 0; i < 6; i++) {
            char c = (char) (rand.nextInt(60) + 69);
            password += c;
        }
        return Base64.getEncoder().withoutPadding().encodeToString(password.getBytes());
    }

    public static List<Object> castToObject(List<?> DTOList) {
        List<Object> objList = new ArrayList<>();
        for (Object charge : DTOList)
            objList.add(charge);
        return objList;
    }

    public static int getDayoFWeekAsNumber(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static String fromDateToHHmm(Date date) {
        String pattern = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String output = simpleDateFormat.format(date);
        return output;
    }

    public static Boolean checkDateInRange(Date dateToCheck, Date startDate, Date endDate) {
        return dateToCheck.getTime() >= startDate.getTime() &&
                dateToCheck.getTime() <= endDate.getTime();
    }

    public static float fromHourToInt(String inputHour) {
        float outputHour = 0;
        if (inputHour != null) {
            String[] arrOfStr = inputHour.split(" ", 2);
            String time = arrOfStr[0];
            String suffix = arrOfStr[1];
            String[] timeArray = time.split(":", 2);
            String hourInString = timeArray[0];
            String minuteInString = timeArray[1];
            int hour = Integer.parseInt(hourInString);
            int minute = Integer.parseInt(minuteInString);
            if (suffix.equals("AM")) {
                float minuteFloat = (float) minute / 100;
                outputHour = (float) (hour + minuteFloat);
            } else if (suffix.equals("PM")) {
                outputHour = hour + minute + 12;
            }
        }
        return outputHour;
    }

    public static Boolean matchWithPattern(String inpuString) {
        Pattern pattern = Pattern.compile(Constants.APPOINTMENT_HOUR_PATTERN);
        Matcher matcher = pattern.matcher(inpuString);
        return matcher.matches();
    }
}
