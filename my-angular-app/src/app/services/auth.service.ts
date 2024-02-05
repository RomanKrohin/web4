import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private authUrl = 'http://localhost:8080/app/auth';
  private auth = false;
  public token="";

  constructor(private http: HttpClient) {}

  getResults(formData: any): Observable<any> {
    const params = new HttpParams({
      fromObject: formData,
    });
    const headers = new HttpHeaders({
      'ngrok-skip-browser-warning': 'true',
      'Access-Control-Allow-Origin': '*'
    });

    return this.http.get(`${this.authUrl}`, { params, headers });
  }

  login(){
    sessionStorage.setItem("auth", "true");
    this.auth=true;
  }

  logout(){
    sessionStorage.setItem("auth", "false");
    this.auth=false;
  }

  isAuthenticated(): boolean{
    return this.auth;
  }
}