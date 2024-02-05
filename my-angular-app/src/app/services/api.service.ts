import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private apiUrl = 'http://localhost:8080/app/api';

  constructor(private http: HttpClient, private authService: AuthService) {}

  getResults(formData: any): Observable<any> {
    const params = new HttpParams({
      fromObject: formData,
    });

    if (localStorage.getItem("auth")=="true"){

    }

    const headers = new HttpHeaders({
      'Authorization': `${this.authService.token}`
    });

    return this.http.get(`${this.apiUrl}`, { params, headers });
  }

  getResultsSVG(x: String ,y: String,r: String): Observable<any>{
    localStorage.setItem("1", "1");
    const params = new HttpParams().set('x', `${x}`).set('y', `${y}`).set('r', `${r}`);

    if (localStorage.getItem("auth")=="true"){
      
    }

    const headers = new HttpHeaders({
      'Authorization': `${this.authService.token}`,
      'ngrok-skip-browser-warning': 'true',
      'Access-Control-Allow-Origin': '*'
    });
    
    return this.http.get(`${this.apiUrl}`, { params, headers });
  }

  clearResults(){
    const headers = new HttpHeaders({
      'Authorization': `${this.authService.token}`,
      'ngrok-skip-browser-warning': 'true',
      'Access-Control-Allow-Origin': '*'
    });
    if (localStorage.getItem("auth")=="true"){
      
    }
    return this.http.delete(`${this.apiUrl}`, {headers});
  }

  logout(){
    const headers = new HttpHeaders({
      'Authorization': `${this.authService.token}`,
      'ngrok-skip-browser-warning': 'true',
      'Access-Control-Allow-Origin': '*'
    });
    if (localStorage.getItem("auth")=="true"){
      
    }
    return this.http.delete('http://localhost:8080/app/auth', {headers}); 
  }
}