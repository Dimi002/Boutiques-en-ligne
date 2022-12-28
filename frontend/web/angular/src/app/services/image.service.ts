import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

/**
 * @author Arléon Zemtsop
 * @email arleonzemtsop@gmail.com
*/
@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor() { }

  public getCover(url: string | undefined): string {
    if (url)
      return environment.basePath + '/file/download?fileKey=' + url;
    return '';
  }
}
