/**
 * Api Documentation
 * Api Documentation
 *
 * OpenAPI spec version: 1.0
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 *//* tslint:disable:no-unused-variable member-ordering */

import { Inject, Injectable, Optional }                      from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams,
         HttpResponse, HttpEvent }                           from '@angular/common/http';
import { CustomHttpUrlEncodingCodec }                        from '../encoder';

import { Observable }                                        from 'rxjs';

import { Speciality } from '../model/speciality';
import { SpecialityMin } from '../model/specialityMin';

import { BASE_PATH, COLLECTION_FORMATS }                     from '../variables';
import { Configuration }                                     from '../configuration';


@Injectable()
export class SpecialityControllerService {

    protected basePath = '//localhost:8077/';
    public defaultHeaders = new HttpHeaders();
    public configuration = new Configuration();

    constructor(protected httpClient: HttpClient, @Optional()@Inject(BASE_PATH) basePath: string, @Optional() configuration: Configuration) {
        if (basePath) {
            this.basePath = basePath;
        }
        if (configuration) {
            this.configuration = configuration;
            this.basePath = basePath || configuration.basePath || this.basePath;
        }
    }

    /**
     * @param consumes string[] mime-types
     * @return true: consumes contains 'multipart/form-data', false: otherwise
     */
    private canConsumeForm(consumes: string[]): boolean {
        const form = 'multipart/form-data';
        for (const consume of consumes) {
            if (form === consume) {
                return true;
            }
        }
        return false;
    }


    /**
     * create
     *
     * @param body speciality
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public createUsingPOST3(body: Speciality, observe?: 'body', reportProgress?: boolean): Observable<Speciality>;
    public createUsingPOST3(body: Speciality, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Speciality>>;
    public createUsingPOST3(body: Speciality, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Speciality>>;
    public createUsingPOST3(body: Speciality, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (body === null || body === undefined) {
            throw new Error('Required parameter body was null or undefined when calling createUsingPOST3.');
        }

        let headers = this.defaultHeaders;

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            'application/json'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
            'application/json'
        ];
        const httpContentTypeSelected: string | undefined = this.configuration.selectHeaderContentType(consumes);
        if (httpContentTypeSelected != undefined) {
            headers = headers.set('Content-Type', httpContentTypeSelected);
        }

        return this.httpClient.request<Speciality>('post',`${this.basePath}/specialities/createSpeciality`,
            {
                body: body,
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * deleteSpeciality
     *
     * @param specialityId specialityId
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public deleteSpecialityUsingGET(specialityId: number, observe?: 'body', reportProgress?: boolean): Observable<Speciality>;
    public deleteSpecialityUsingGET(specialityId: number, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Speciality>>;
    public deleteSpecialityUsingGET(specialityId: number, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Speciality>>;
    public deleteSpecialityUsingGET(specialityId: number, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (specialityId === null || specialityId === undefined) {
            throw new Error('Required parameter specialityId was null or undefined when calling deleteSpecialityUsingGET.');
        }

        let headers = this.defaultHeaders;

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            'application/json'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.request<Speciality>('get',`${this.basePath}/specialities/deleteSpeciality/${encodeURIComponent(String(specialityId))}`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * findAllSpecialitiesMin
     *
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public findAllSpecialitiesMinUsingGET(observe?: 'body', reportProgress?: boolean): Observable<Array<SpecialityMin>>;
    public findAllSpecialitiesMinUsingGET(observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Array<SpecialityMin>>>;
    public findAllSpecialitiesMinUsingGET(observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Array<SpecialityMin>>>;
    public findAllSpecialitiesMinUsingGET(observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        let headers = this.defaultHeaders;

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            'application/json'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.request<Array<SpecialityMin>>('get',`${this.basePath}/specialities/findAllSpecialitiesMin`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * findSpecialityById
     *
     * @param specialityId specialityId
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public findSpecialityByIdUsingGET(specialityId: number, observe?: 'body', reportProgress?: boolean): Observable<Speciality>;
    public findSpecialityByIdUsingGET(specialityId: number, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Speciality>>;
    public findSpecialityByIdUsingGET(specialityId: number, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Speciality>>;
    public findSpecialityByIdUsingGET(specialityId: number, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (specialityId === null || specialityId === undefined) {
            throw new Error('Required parameter specialityId was null or undefined when calling findSpecialityByIdUsingGET.');
        }

        let headers = this.defaultHeaders;

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            'application/json'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.request<Speciality>('get',`${this.basePath}/specialities/findSpeciality/${encodeURIComponent(String(specialityId))}`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * getAllActivatedSpecialities
     *
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getAllActivatedSpecialitiesUsingGET(observe?: 'body', reportProgress?: boolean): Observable<Array<Speciality>>;
    public getAllActivatedSpecialitiesUsingGET(observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Array<Speciality>>>;
    public getAllActivatedSpecialitiesUsingGET(observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Array<Speciality>>>;
    public getAllActivatedSpecialitiesUsingGET(observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        let headers = this.defaultHeaders;

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            'application/json'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.request<Array<Speciality>>('get',`${this.basePath}/specialities/getAllActivatedSpecialities`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * getAllSpecialities
     *
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getAllSpecialitiesUsingGET(observe?: 'body', reportProgress?: boolean): Observable<Array<Speciality>>;
    public getAllSpecialitiesUsingGET(observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Array<Speciality>>>;
    public getAllSpecialitiesUsingGET(observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Array<Speciality>>>;
    public getAllSpecialitiesUsingGET(observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        let headers = this.defaultHeaders;

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            'application/json'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.request<Array<Speciality>>('get',`${this.basePath}/specialities/getAllSpecialities`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * updateSpeciality
     *
     * @param body speciality
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public updateSpecialityUsingPOST(body: Speciality, observe?: 'body', reportProgress?: boolean): Observable<Speciality>;
    public updateSpecialityUsingPOST(body: Speciality, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Speciality>>;
    public updateSpecialityUsingPOST(body: Speciality, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Speciality>>;
    public updateSpecialityUsingPOST(body: Speciality, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (body === null || body === undefined) {
            throw new Error('Required parameter body was null or undefined when calling updateSpecialityUsingPOST.');
        }

        let headers = this.defaultHeaders;

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            'application/json'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
            'application/json'
        ];
        const httpContentTypeSelected: string | undefined = this.configuration.selectHeaderContentType(consumes);
        if (httpContentTypeSelected != undefined) {
            headers = headers.set('Content-Type', httpContentTypeSelected);
        }

        return this.httpClient.request<Speciality>('post',`${this.basePath}/specialities/updateSpeciality`,
            {
                body: body,
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

}
