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

import { AgGridResponse } from '../model/agGridResponse';
import { BudgetDTO } from '../model/budgetDTO';
import { CatSetDTOWrapper } from '../model/catSetDTOWrapper';
import { CategorieDTO } from '../model/categorieDTO';
import { ImgFactDto } from '../model/imgFactDto';
import { RemoveFromBilanComptaDtoWrapper } from '../model/removeFromBilanComptaDtoWrapper';
import { SearchCriteria } from '../model/searchCriteria';
import { StatusResponse } from '../model/statusResponse';

import { BASE_PATH, COLLECTION_FORMATS }                     from '../variables';
import { Configuration }                                     from '../configuration';


@Injectable()
export class BudgetControllerService {

    protected basePath = '//localhost:8075/';
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
     * addImgFileNameToBudget
     * 
     * @param body img
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public addImgFileNameToBudgetUsingPOST(body: ImgFactDto, observe?: 'body', reportProgress?: boolean): Observable<StatusResponse>;
    public addImgFileNameToBudgetUsingPOST(body: ImgFactDto, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<StatusResponse>>;
    public addImgFileNameToBudgetUsingPOST(body: ImgFactDto, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<StatusResponse>>;
    public addImgFileNameToBudgetUsingPOST(body: ImgFactDto, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (body === null || body === undefined) {
            throw new Error('Required parameter body was null or undefined when calling addImgFileNameToBudgetUsingPOST.');
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

        return this.httpClient.request<StatusResponse>('post',`${this.basePath}/budgets/addImgFileNameToBudget`,
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
     * addSetCategories
     * 
     * @param body catSetDTOS
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public addSetCategoriesUsingPOST(body: CatSetDTOWrapper, observe?: 'body', reportProgress?: boolean): Observable<StatusResponse>;
    public addSetCategoriesUsingPOST(body: CatSetDTOWrapper, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<StatusResponse>>;
    public addSetCategoriesUsingPOST(body: CatSetDTOWrapper, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<StatusResponse>>;
    public addSetCategoriesUsingPOST(body: CatSetDTOWrapper, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (body === null || body === undefined) {
            throw new Error('Required parameter body was null or undefined when calling addSetCategoriesUsingPOST.');
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

        return this.httpClient.request<StatusResponse>('post',`${this.basePath}/budgets/addSetCategories`,
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
     * categorieList
     * 
     * @param term term
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public categorieListUsingGET(term: string, observe?: 'body', reportProgress?: boolean): Observable<Array<CategorieDTO>>;
    public categorieListUsingGET(term: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Array<CategorieDTO>>>;
    public categorieListUsingGET(term: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Array<CategorieDTO>>>;
    public categorieListUsingGET(term: string, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (term === null || term === undefined) {
            throw new Error('Required parameter term was null or undefined when calling categorieListUsingGET.');
        }

        let queryParameters = new HttpParams({encoder: new CustomHttpUrlEncodingCodec()});
        if (term !== undefined && term !== null) {
            queryParameters = queryParameters.set('term', <any>term);
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

        return this.httpClient.request<Array<CategorieDTO>>('get',`${this.basePath}/budgets/categorielist`,
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
     * create
     * 
     * @param body budget
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public createUsingPOST(body: BudgetDTO, observe?: 'body', reportProgress?: boolean): Observable<StatusResponse>;
    public createUsingPOST(body: BudgetDTO, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<StatusResponse>>;
    public createUsingPOST(body: BudgetDTO, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<StatusResponse>>;
    public createUsingPOST(body: BudgetDTO, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (body === null || body === undefined) {
            throw new Error('Required parameter body was null or undefined when calling createUsingPOST.');
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

        return this.httpClient.request<StatusResponse>('post',`${this.basePath}/budgets/create`,
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
     * delete
     * 
     * @param id id
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public deleteUsingDELETE(id: number, observe?: 'body', reportProgress?: boolean): Observable<StatusResponse>;
    public deleteUsingDELETE(id: number, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<StatusResponse>>;
    public deleteUsingDELETE(id: number, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<StatusResponse>>;
    public deleteUsingDELETE(id: number, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (id === null || id === undefined) {
            throw new Error('Required parameter id was null or undefined when calling deleteUsingDELETE.');
        }

        let queryParameters = new HttpParams({encoder: new CustomHttpUrlEncodingCodec()});
        if (id !== undefined && id !== null) {
            queryParameters = queryParameters.set('id', <any>id);
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

        return this.httpClient.request<StatusResponse>('delete',`${this.basePath}/budgets/delete`,
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
     * listImgFiles
     * 
     * @param budgetId budgetId
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public listImgFilesUsingGET(budgetId: number, observe?: 'body', reportProgress?: boolean): Observable<Array<string>>;
    public listImgFilesUsingGET(budgetId: number, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Array<string>>>;
    public listImgFilesUsingGET(budgetId: number, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Array<string>>>;
    public listImgFilesUsingGET(budgetId: number, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (budgetId === null || budgetId === undefined) {
            throw new Error('Required parameter budgetId was null or undefined when calling listImgFilesUsingGET.');
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

        return this.httpClient.request<Array<string>>('get',`${this.basePath}/budgets/listImgFiles/${encodeURIComponent(String(budgetId))}`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * records
     * 
     * @param body filterCriteriaList
     * @param username username
     * @param page page
     * @param size size
     * @param sidx sidx
     * @param sort sort
     * @param filters filters
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public recordsUsingPOST(body: Array<SearchCriteria>, username?: string, page?: number, size?: number, sidx?: string, sort?: string, filters?: string, observe?: 'body', reportProgress?: boolean): Observable<AgGridResponse>;
    public recordsUsingPOST(body: Array<SearchCriteria>, username?: string, page?: number, size?: number, sidx?: string, sort?: string, filters?: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<AgGridResponse>>;
    public recordsUsingPOST(body: Array<SearchCriteria>, username?: string, page?: number, size?: number, sidx?: string, sort?: string, filters?: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<AgGridResponse>>;
    public recordsUsingPOST(body: Array<SearchCriteria>, username?: string, page?: number, size?: number, sidx?: string, sort?: string, filters?: string, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (body === null || body === undefined) {
            throw new Error('Required parameter body was null or undefined when calling recordsUsingPOST.');
        }







        let queryParameters = new HttpParams({encoder: new CustomHttpUrlEncodingCodec()});
        if (username !== undefined && username !== null) {
            queryParameters = queryParameters.set('username', <any>username);
        }
        if (page !== undefined && page !== null) {
            queryParameters = queryParameters.set('page', <any>page);
        }
        if (size !== undefined && size !== null) {
            queryParameters = queryParameters.set('size', <any>size);
        }
        if (sidx !== undefined && sidx !== null) {
            queryParameters = queryParameters.set('sidx', <any>sidx);
        }
        if (sort !== undefined && sort !== null) {
            queryParameters = queryParameters.set('sort', <any>sort);
        }
        if (filters !== undefined && filters !== null) {
            queryParameters = queryParameters.set('filters', <any>filters);
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

        return this.httpClient.request<AgGridResponse>('post',`${this.basePath}/budgets/records`,
            {
                body: body,
                params: queryParameters,
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * removeFromBilanCompta
     * 
     * @param body removeFromBilanComptaDTOS
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public removeFromBilanComptaUsingPOST(body: RemoveFromBilanComptaDtoWrapper, observe?: 'body', reportProgress?: boolean): Observable<StatusResponse>;
    public removeFromBilanComptaUsingPOST(body: RemoveFromBilanComptaDtoWrapper, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<StatusResponse>>;
    public removeFromBilanComptaUsingPOST(body: RemoveFromBilanComptaDtoWrapper, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<StatusResponse>>;
    public removeFromBilanComptaUsingPOST(body: RemoveFromBilanComptaDtoWrapper, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (body === null || body === undefined) {
            throw new Error('Required parameter body was null or undefined when calling removeFromBilanComptaUsingPOST.');
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

        return this.httpClient.request<StatusResponse>('post',`${this.basePath}/budgets/removeFromBilanCompta`,
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
     * removePreValFact
     * 
     * @param body removeFromBilanComptaDTOS
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public removePreValFactUsingPOST(body: RemoveFromBilanComptaDtoWrapper, observe?: 'body', reportProgress?: boolean): Observable<StatusResponse>;
    public removePreValFactUsingPOST(body: RemoveFromBilanComptaDtoWrapper, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<StatusResponse>>;
    public removePreValFactUsingPOST(body: RemoveFromBilanComptaDtoWrapper, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<StatusResponse>>;
    public removePreValFactUsingPOST(body: RemoveFromBilanComptaDtoWrapper, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (body === null || body === undefined) {
            throw new Error('Required parameter body was null or undefined when calling removePreValFactUsingPOST.');
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

        return this.httpClient.request<StatusResponse>('post',`${this.basePath}/budgets/removePreValFact`,
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
     * update
     * 
     * @param body budget
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public updateUsingPOST(body: BudgetDTO, observe?: 'body', reportProgress?: boolean): Observable<StatusResponse>;
    public updateUsingPOST(body: BudgetDTO, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<StatusResponse>>;
    public updateUsingPOST(body: BudgetDTO, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<StatusResponse>>;
    public updateUsingPOST(body: BudgetDTO, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (body === null || body === undefined) {
            throw new Error('Required parameter body was null or undefined when calling updateUsingPOST.');
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

        return this.httpClient.request<StatusResponse>('post',`${this.basePath}/budgets/update`,
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
     * validerFact
     * 
     * @param body removeFromBilanComptaDTOS
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public validerFactUsingPOST(body: RemoveFromBilanComptaDtoWrapper, observe?: 'body', reportProgress?: boolean): Observable<StatusResponse>;
    public validerFactUsingPOST(body: RemoveFromBilanComptaDtoWrapper, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<StatusResponse>>;
    public validerFactUsingPOST(body: RemoveFromBilanComptaDtoWrapper, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<StatusResponse>>;
    public validerFactUsingPOST(body: RemoveFromBilanComptaDtoWrapper, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (body === null || body === undefined) {
            throw new Error('Required parameter body was null or undefined when calling validerFactUsingPOST.');
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

        return this.httpClient.request<StatusResponse>('post',`${this.basePath}/budgets/validerFact`,
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