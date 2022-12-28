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

import { Appointment } from '../model/appointment';
import { Specialist } from '../model/specialist';

import { BASE_PATH, COLLECTION_FORMATS }                     from '../variables';
import { Configuration }                                     from '../configuration';


@Injectable()
export class AppointmentControllerService {

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
     * createAppointment
     *
     * @param body appointment
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public createAppointmentUsingPOST(body: Appointment, observe?: 'body', reportProgress?: boolean): Observable<any>;
    public createAppointmentUsingPOST(body: Appointment, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<any>>;
    public createAppointmentUsingPOST(body: Appointment, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<any>>;
    public createAppointmentUsingPOST(body: Appointment, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (body === null || body === undefined) {
            throw new Error('Required parameter body was null or undefined when calling createAppointmentUsingPOST.');
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

        return this.httpClient.request<any>('post',`${this.basePath}/appointments/createAppointment`,
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
     * deleteAppoitment
     *
     * @param appointId appointId
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public deleteAppoitmentUsingGET(appointId: number, observe?: 'body', reportProgress?: boolean): Observable<Appointment>;
    public deleteAppoitmentUsingGET(appointId: number, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Appointment>>;
    public deleteAppoitmentUsingGET(appointId: number, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Appointment>>;
    public deleteAppoitmentUsingGET(appointId: number, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (appointId === null || appointId === undefined) {
            throw new Error('Required parameter appointId was null or undefined when calling deleteAppoitmentUsingGET.');
        }

        let queryParameters = new HttpParams({encoder: new CustomHttpUrlEncodingCodec()});
        if (appointId !== undefined && appointId !== null) {
            queryParameters = queryParameters.set('appointId', <any>appointId);
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

        return this.httpClient.request<Appointment>('get',`${this.basePath}/appointments/deleteAppoitment`,
            {
                params: queryParameters,
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * getAllActivedAppoitment
     *
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getAllActivedAppoitmentUsingGET(observe?: 'body', reportProgress?: boolean): Observable<Array<Appointment>>;
    public getAllActivedAppoitmentUsingGET(observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Array<Appointment>>>;
    public getAllActivedAppoitmentUsingGET(observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Array<Appointment>>>;
    public getAllActivedAppoitmentUsingGET(observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

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

        return this.httpClient.request<Array<Appointment>>('get',`${this.basePath}/appointments/getAllActivedAppoitment`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * getAllAppointmentSpecialist
     *
     * @param id id
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getAllAppointmentSpecialistUsingGET(id: number, observe?: 'body', reportProgress?: boolean): Observable<any>;
    public getAllAppointmentSpecialistUsingGET(id: number, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<any>>;
    public getAllAppointmentSpecialistUsingGET(id: number, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<any>>;
    public getAllAppointmentSpecialistUsingGET(id: number, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (id === null || id === undefined) {
            throw new Error('Required parameter id was null or undefined when calling getAllAppointmentSpecialistUsingGET.');
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

        return this.httpClient.request<any>('get',`${this.basePath}/appointments/getAllAppointmentSpecialist/${encodeURIComponent(String(id))}`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * getAllAppointment
     *
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getAllAppointmentUsingGET(observe?: 'body', reportProgress?: boolean): Observable<any>;
    public getAllAppointmentUsingGET(observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<any>>;
    public getAllAppointmentUsingGET(observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<any>>;
    public getAllAppointmentUsingGET(observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

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

        return this.httpClient.request<any>('get',`${this.basePath}/appointments/getAllAppointment`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * getAllAppointments
     *
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getAllAppointmentsUsingGET(observe?: 'body', reportProgress?: boolean): Observable<any>;
    public getAllAppointmentsUsingGET(observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<any>>;
    public getAllAppointmentsUsingGET(observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<any>>;
    public getAllAppointmentsUsingGET(observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

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

        return this.httpClient.request<any>('get',`${this.basePath}/appointments/getAllAppointments`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * getAllArchivedAppoitment
     *
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getAllArchivedAppoitmentUsingGET(observe?: 'body', reportProgress?: boolean): Observable<Array<Appointment>>;
    public getAllArchivedAppoitmentUsingGET(observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Array<Appointment>>>;
    public getAllArchivedAppoitmentUsingGET(observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Array<Appointment>>>;
    public getAllArchivedAppoitmentUsingGET(observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

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

        return this.httpClient.request<Array<Appointment>>('get',`${this.basePath}/appointments/getAllArchivedAppoitment`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * getAllByIdAppoitment
     *
     * @param appointId appointId
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getAllByIdAppoitmentUsingGET(appointId: number, observe?: 'body', reportProgress?: boolean): Observable<Appointment>;
    public getAllByIdAppoitmentUsingGET(appointId: number, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Appointment>>;
    public getAllByIdAppoitmentUsingGET(appointId: number, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Appointment>>;
    public getAllByIdAppoitmentUsingGET(appointId: number, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (appointId === null || appointId === undefined) {
            throw new Error('Required parameter appointId was null or undefined when calling getAllByIdAppoitmentUsingGET.');
        }

        let queryParameters = new HttpParams({encoder: new CustomHttpUrlEncodingCodec()});
        if (appointId !== undefined && appointId !== null) {
            queryParameters = queryParameters.set('appointId', <any>appointId);
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

        return this.httpClient.request<Appointment>('get',`${this.basePath}/appointments/getAllByIdAppoitment`,
            {
                params: queryParameters,
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * getAllById
     *
     * @param appointId appointId
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getAllByIdUsingGET(appointId: number, observe?: 'body', reportProgress?: boolean): Observable<number>;
    public getAllByIdUsingGET(appointId: number, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<number>>;
    public getAllByIdUsingGET(appointId: number, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<number>>;
    public getAllByIdUsingGET(appointId: number, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (appointId === null || appointId === undefined) {
            throw new Error('Required parameter appointId was null or undefined when calling getAllByIdUsingGET.');
        }

        let queryParameters = new HttpParams({encoder: new CustomHttpUrlEncodingCodec()});
        if (appointId !== undefined && appointId !== null) {
            queryParameters = queryParameters.set('appointId', <any>appointId);
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

        return this.httpClient.request<number>('get',`${this.basePath}/appointments/getAllById`,
            {
                params: queryParameters,
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * getAllDistinctPatients
     *
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getAllDistinctPatientsUsingGET(observe?: 'body', reportProgress?: boolean): Observable<any>;
    public getAllDistinctPatientsUsingGET(observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<any>>;
    public getAllDistinctPatientsUsingGET(observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<any>>;
    public getAllDistinctPatientsUsingGET(observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

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

        return this.httpClient.request<any>('get',`${this.basePath}/appointments/getAllDistinctPatients`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * getAllSupTodayAppointment
     *
     * @param id id
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getAllSupTodayAppointmentUsingGET(id: number, observe?: 'body', reportProgress?: boolean): Observable<any>;
    public getAllSupTodayAppointmentUsingGET(id: number, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<any>>;
    public getAllSupTodayAppointmentUsingGET(id: number, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<any>>;
    public getAllSupTodayAppointmentUsingGET(id: number, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (id === null || id === undefined) {
            throw new Error('Required parameter id was null or undefined when calling getAllSupTodayAppointmentUsingGET.');
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

        return this.httpClient.request<any>('get',`${this.basePath}/appointments/getAllSupTodayAppointment/${encodeURIComponent(String(id))}`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * getAllTodayAppointment
     *
     * @param id id
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getAllTodayAppointmentUsingGET(id: number, observe?: 'body', reportProgress?: boolean): Observable<any>;
    public getAllTodayAppointmentUsingGET(id: number, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<any>>;
    public getAllTodayAppointmentUsingGET(id: number, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<any>>;
    public getAllTodayAppointmentUsingGET(id: number, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (id === null || id === undefined) {
            throw new Error('Required parameter id was null or undefined when calling getAllTodayAppointmentUsingGET.');
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

        return this.httpClient.request<any>('get',`${this.basePath}/appointments/getAllTodayAppointment/${encodeURIComponent(String(id))}`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * getByspecialistIdAppoitment
     *
     * @param body specialistId
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getByspecialistIdAppoitmentUsingGET(body: Specialist, observe?: 'body', reportProgress?: boolean): Observable<Appointment>;
    public getByspecialistIdAppoitmentUsingGET(body: Specialist, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Appointment>>;
    public getByspecialistIdAppoitmentUsingGET(body: Specialist, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Appointment>>;
    public getByspecialistIdAppoitmentUsingGET(body: Specialist, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (body === null || body === undefined) {
            throw new Error('Required parameter body was null or undefined when calling getByspecialistIdAppoitmentUsingGET.');
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

        return this.httpClient.request<Appointment>('get',`${this.basePath}/appointments/getByspecialistIdAppoitment`,
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
     * getCountCountDoctorDashbord
     *
     * @param id id
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getCountCountDoctorDashbordUsingGET(id: number, observe?: 'body', reportProgress?: boolean): Observable<any>;
    public getCountCountDoctorDashbordUsingGET(id: number, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<any>>;
    public getCountCountDoctorDashbordUsingGET(id: number, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<any>>;
    public getCountCountDoctorDashbordUsingGET(id: number, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (id === null || id === undefined) {
            throw new Error('Required parameter id was null or undefined when calling getCountCountDoctorDashbordUsingGET.');
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

        return this.httpClient.request<any>('get',`${this.basePath}/appointments/getCountAllAppointment/${encodeURIComponent(String(id))}`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * updateSpecialistState
     *
     * @param id id
     * @param state state
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public updateSpecialistStateUsingGET(id: number, state: string, observe?: 'body', reportProgress?: boolean): Observable<any>;
    public updateSpecialistStateUsingGET(id: number, state: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<any>>;
    public updateSpecialistStateUsingGET(id: number, state: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<any>>;
    public updateSpecialistStateUsingGET(id: number, state: string, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (id === null || id === undefined) {
            throw new Error('Required parameter id was null or undefined when calling updateSpecialistStateUsingGET.');
        }

        if (state === null || state === undefined) {
            throw new Error('Required parameter state was null or undefined when calling updateSpecialistStateUsingGET.');
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

        return this.httpClient.request<any>('get',`${this.basePath}/appointments/updateSpecialistState/${encodeURIComponent(String(id))}/${encodeURIComponent(String(state))}`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

}
