import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { SearchResponse } from '../utils/SearchResponse';

/**
 * @author Tchamou Ramses
 * @email tchamouramses@gmail.com
*/
@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private http: HttpClient,) { }

  public search(keyword: string) {
    return this.http.get<SearchResponse[]>(`${environment.basePath}/search/records/${keyword}`);
  }
}
