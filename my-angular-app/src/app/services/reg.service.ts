import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RegService {
  private authUrl = 'http://localhost:8080/app/reg';

  constructor(private http: HttpClient) {}

  getResults(formData: any): Observable<any> {

    const params = new HttpParams({
      fromObject: formData,
    });

    const headers = new HttpHeaders()
      .set('ngrok-skip-browser-warning', 'true')
      .set('Access-Control-Allow-Origin', '*');

    return this.http.get(this.authUrl, { params, headers });
  }
}