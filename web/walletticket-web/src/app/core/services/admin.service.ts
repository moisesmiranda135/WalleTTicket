import { UserDto } from '../entity/user/UserDto';
import { UserResponse } from '../entity/user/UserResponse';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { OkApiResponseList } from '../util/apiResponseInterface';


let token = localStorage.getItem('request_token');
const DEFAULT_HEADERS = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  })
};

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  adminBaseUrl = `${environment.apiLocalUrl}`;

  constructor(private http: HttpClient) { }

  getAdminList(): Observable<any> {
    let requestUrl = `${this.adminBaseUrl}/auth/all/admin`;
    return this.http.get<OkApiResponseList<UserResponse>>(requestUrl, DEFAULT_HEADERS);
  }

  createAdmin(admin: UserDto): Observable<any> {
    let requestUrl = `${this.adminBaseUrl}/auth/register/admin`;
    return this.http.post<OkApiResponseList<UserResponse>>(requestUrl, admin, DEFAULT_HEADERS);
  }

  editAdmin(admin: UserDto, idAdmin: number): Observable<any> {
    let requestUrl = `${this.adminBaseUrl}/employee/${idAdmin}`;
    return this.http.put<OkApiResponseList<UserResponse>>(requestUrl, admin, DEFAULT_HEADERS);
  }

  deleteAdmin(idAdmin: number): Observable<any> {
    let requestUrl = `${this.adminBaseUrl}/admin/${idAdmin}`;
    return this.http.delete<OkApiResponseList<UserResponse>>(requestUrl, DEFAULT_HEADERS);
  }
}