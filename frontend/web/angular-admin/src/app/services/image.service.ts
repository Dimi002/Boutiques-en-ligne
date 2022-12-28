import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { observable, Observable, switchMap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UploadResponse } from '../generated';

/**
 * @author Arléon Zemtsop
 * @email arleonzemtsop@gmail.com
*/
@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor(private http: HttpClient) { }

  public getCover(url: string | undefined): string {
    if (url)
      return environment.basePath + '/file/download?fileKey=' + url;
    return '';
  }

  getData(url: string): Observable<string> {
    return this.http.get(url, { responseType: 'blob' })
      .pipe(
        switchMap(response => this.readFile(response))
      );
  }


  public toBase64 = (file: File): Promise<string | ArrayBuffer | null> => new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
  });

  public getImage(imageUrl: string): Observable<Blob> {
    return this.http.get(imageUrl, { responseType: "blob" });
  }

  private readFile(blob: Blob): Observable<string> {
    return Observable.create((obs: any) => {
      const reader = new FileReader();

      reader.onerror = err => obs.error(err);
      reader.onabort = err => obs.error(err);
      reader.onload = () => obs.next(reader.result);
      reader.onloadend = () => obs.complete();

      return reader.readAsDataURL(blob);
    });
  }

  public blobToFile = (theBlob: Blob, fileName: string): File => {
    var b: any = theBlob;
    b.lastModifiedDate = new Date();
    b.name = fileName;
    return <File>theBlob;
  }


  public getCovers(setting: any): string[] {
    if (setting) {
      const parseUrls: string[] = setting.coverUrls;
      if (parseUrls && parseUrls.length > 0) {
        const computedUrls: string[] = [];
        parseUrls.forEach(url => {
          computedUrls.push(environment.basePath + '/file/download?fileKey=' + url);
        })
        return computedUrls;
      } else {
        return [];
      }
    } else {
      return [];
    }
  }

  public upload(files: File[]): Observable<Array<UploadResponse>> {
    const formData: FormData = new FormData();
    files.forEach(file => {
      formData.append('files', file, file.name);
    })
    return this.http.post<Array<UploadResponse>>(`${environment.basePath}/file/uploadimage`, formData);
  }

  public delete(fileKey: string): Observable<any> {
    return this.http.post<any>(`${environment.basePath}/file/deletefile?fileKey=${fileKey}`, null);
  }

  public deleteOldImage(fileKey: string): void {
    this.delete(fileKey).toPromise();
  }
}
