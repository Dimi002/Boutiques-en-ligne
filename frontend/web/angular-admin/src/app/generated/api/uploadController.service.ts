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

import { FileSystemResource } from '../model/fileSystemResource';
import { Resource } from '../model/resource';
import { UploadResponse } from '../model/uploadResponse';

import { BASE_PATH, COLLECTION_FORMATS }                     from '../variables';
import { Configuration }                                     from '../configuration';


@Injectable()
export class UploadControllerService {

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
     * deleteFile
     *
     * @param fileKey fileKey
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public deleteFileUsingPOST(fileKey?: string, observe?: 'body', reportProgress?: boolean): Observable<any>;
    public deleteFileUsingPOST(fileKey?: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<any>>;
    public deleteFileUsingPOST(fileKey?: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<any>>;
    public deleteFileUsingPOST(fileKey?: string, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {


        let queryParameters = new HttpParams({encoder: new CustomHttpUrlEncodingCodec()});
        if (fileKey !== undefined && fileKey !== null) {
            queryParameters = queryParameters.set('fileKey', <any>fileKey);
        }

        let headers = this.defaultHeaders;

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            '*/*'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.request<any>('post',`${this.basePath}/file/deletefile`,
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
     * downloadFile
     *
     * @param fileKey fileKey
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public downloadFileUsingGET(fileKey: string, observe?: 'body', reportProgress?: boolean): Observable<FileSystemResource>;
    public downloadFileUsingGET(fileKey: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<FileSystemResource>>;
    public downloadFileUsingGET(fileKey: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<FileSystemResource>>;
    public downloadFileUsingGET(fileKey: string, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (fileKey === null || fileKey === undefined) {
            throw new Error('Required parameter fileKey was null or undefined when calling downloadFileUsingGET.');
        }

        let queryParameters = new HttpParams({encoder: new CustomHttpUrlEncodingCodec()});
        if (fileKey !== undefined && fileKey !== null) {
            queryParameters = queryParameters.set('fileKey', <any>fileKey);
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

        return this.httpClient.request<FileSystemResource>('get',`${this.basePath}/file/download`,
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
     * getFile
     *
     * @param filename filename
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getFileUsingGET(filename: string, observe?: 'body', reportProgress?: boolean): Observable<Resource>;
    public getFileUsingGET(filename: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Resource>>;
    public getFileUsingGET(filename: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Resource>>;
    public getFileUsingGET(filename: string, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (filename === null || filename === undefined) {
            throw new Error('Required parameter filename was null or undefined when calling getFileUsingGET.');
        }

        let headers = this.defaultHeaders;

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            '*/*'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.request<Resource>('get',`${this.basePath}/file/${encodeURIComponent(String(filename))}`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * uploadImageFile
     *
     * @param files files
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public uploadImageFileUsingPOST(files: Array<Blob>, observe?: 'body', reportProgress?: boolean): Observable<Array<UploadResponse>>;
    public uploadImageFileUsingPOST(files: Array<Blob>, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Array<UploadResponse>>>;
    public uploadImageFileUsingPOST(files: Array<Blob>, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Array<UploadResponse>>>;
    public uploadImageFileUsingPOST(files: Array<Blob>, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (files === null || files === undefined) {
            throw new Error('Required parameter files was null or undefined when calling uploadImageFileUsingPOST.');
        }

        let queryParameters = new HttpParams({encoder: new CustomHttpUrlEncodingCodec()});
        if (files) {
            files.forEach((element) => {
                queryParameters = queryParameters.append('files', <any>element);
            })
        }

        let headers = this.defaultHeaders;

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            '*/*'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.request<Array<UploadResponse>>('post',`${this.basePath}/file/uploadimage`,
            {
                params: queryParameters,
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

}
