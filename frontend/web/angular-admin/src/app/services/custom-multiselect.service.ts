import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { MultiselecItem } from '../models';

/**
 * @author Maestros
 * @email roslyn.temateu@dimsoft.eu
 */
@Injectable({
  providedIn: 'root'
})
export class CustomMultiselectService {

  private allItems: MultiselecItem[] = [];
  private duplicatedItems: MultiselecItem[] = [];

  constructor() {
    this.allItems = [];
    this.duplicatedItems = [...this.allItems]
  }


  /**
   *
   * @param item
   * @returns
   */
  public search(item: string): Observable<Array<MultiselecItem>> {
    let sports: MultiselecItem[] = [...this.duplicatedItems];
    if (typeof item === 'string' && item.trim() === '') return of([])
    let val = item.toLowerCase();
    if (!val) this.allItems = [...this.duplicatedItems]
    else {
      let searched: MultiselecItem[] = sports.filter(x => x.title && x.title.toLowerCase().includes(val));
      if (searched.length > 0) this.allItems = [...searched];
      else {
        this.allItems = [...this.duplicatedItems]
      }
    }
    return of(this.allItems);
  }
}
