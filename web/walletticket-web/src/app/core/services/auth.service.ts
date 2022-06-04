import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthLoginDto } from '../entity/login/authLogin.dto';
import { AuthLoginResponse } from '../entity/login/AuthLoginResponse';
import { OkApiResponse, OkApiResponseList } from '../util/apiResponseInterface';


const AUTH_BASE_URL = 'auth';
const DEFAULT_HEADERS = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  authBaseUrl = `${environment.apiLocalUrl}/${AUTH_BASE_URL}`;

  constructor(private http: HttpClient) { }

  login(loginDto: AuthLoginDto): Observable<AuthLoginResponse> {
    let requestUrl = `${this.authBaseUrl}/login`;
    return this.http.post<AuthLoginResponse>(requestUrl, loginDto, DEFAULT_HEADERS);
  }

  getAllUsers(): Observable<any> {
    let requestUrl = `${this.authBaseUrl}/all/`;
    return this.http.get<OkApiResponseList<AuthLoginResponse>>(requestUrl, DEFAULT_HEADERS);
  }




  // LocalStorage Saved

  setLocalRequestToken(token: string) {
    localStorage.setItem('request_token', token);
  }

  getLocalRequestToken() {
    return localStorage.getItem('request_token');
  }


  setRole(role: string) {
    localStorage.setItem('role', role);
  }

  getRole() {
    return localStorage.getItem('role');
  }

  setUserName(name: string) {
    localStorage.setItem('userName', name);
  }

  getUserName() {
    return localStorage.getItem('userName');
  }

  setUserEmail(email: string) {
    localStorage.setItem('userEmail', email);
  }

  getUserEmail() {
    return localStorage.getItem('userEmail');
  }

}