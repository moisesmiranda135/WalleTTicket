import { UserDto } from './../entity/user/UserDto';
import { UserResponse } from './../entity/user/UserResponse';
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
export class EmployeesService {
  employeeBaseUrl = `${environment.apiLocalUrl}`;

  constructor(private http: HttpClient) { }

  getEmployeeList(): Observable<any> {
    let requestUrl = `${this.employeeBaseUrl}/auth/all/employee`;
    return this.http.get<OkApiResponseList<UserResponse>>(requestUrl, DEFAULT_HEADERS);
  }

  createEmployee(employee: UserDto): Observable<any> {
    let requestUrl = `${this.employeeBaseUrl}/auth/register/employee`;
    return this.http.post<OkApiResponseList<UserResponse>>(requestUrl, employee, DEFAULT_HEADERS);
  }

  deleteEmployee(idEmployee: number): Observable<any> {
    let requestUrl = `${this.employeeBaseUrl}/employee/${idEmployee}`;
    return this.http.delete<OkApiResponseList<UserResponse>>(requestUrl, DEFAULT_HEADERS);
  }

  enabledEmployee(idEmployee: number): Observable<any> {
    let requestUrl = `${this.employeeBaseUrl}/employee/enabled/${idEmployee}`;
    return this.http.post<Observable<any>>(requestUrl, idEmployee, DEFAULT_HEADERS);
  }

  disbledEmployee(idEmployee: number): Observable<any> {
    let requestUrl = `${this.employeeBaseUrl}/employee/disabled/${idEmployee}`;
    return this.http.post<OkApiResponseList<UserResponse>>(requestUrl, idEmployee, DEFAULT_HEADERS);
  }
}