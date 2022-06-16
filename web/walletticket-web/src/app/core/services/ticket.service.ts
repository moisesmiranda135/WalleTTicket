import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CategoryResponse } from '../entity/category/categoryListResponse';
import { OkApiResponseList } from '../util/apiResponseInterface';


const AUTH_BASE_URL = 'ticket';
let token = localStorage.getItem('request_token');
const DEFAULT_HEADERS = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  })
};


@Injectable({
  providedIn: 'root'
})
export class TicketService {

  categoryBaseUrl = `${environment.apiLocalUrl}/${AUTH_BASE_URL}`;

  constructor(private http: HttpClient) { }

  getList(): Observable<any> {
    let requestUrl = `${this.categoryBaseUrl}/all`;
    return this.http.get<OkApiResponseList<CategoryResponse>>(requestUrl, DEFAULT_HEADERS);
  }

}
