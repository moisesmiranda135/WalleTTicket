import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CompanyResponse } from '../entity/company/companyResponse';
import { OkApiResponseList } from '../util/apiResponseInterface';


const AUTH_BASE_URL = 'company';
let token = localStorage.getItem('request_token');
const DEFAULT_HEADERS = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  })
};

const FILE_HEADERS = {
  headers: new HttpHeaders({
  })
};


@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  CompanyBaseUrl = `${environment.apiLocalUrl}/${AUTH_BASE_URL}`;

  constructor(private http: HttpClient) { }

  getList(): Observable<any> {
    let requestUrl = `${this.CompanyBaseUrl}/`;
    return this.http.get<OkApiResponseList<CompanyResponse>>(requestUrl, DEFAULT_HEADERS);
  }

  createCompany(company: CompanyResponse, image: File): Observable<any> {
    const formData = new FormData();
    formData.append('json', new Blob ([JSON.stringify(company)], {type: 'application/json'}));
    formData.append('file', image);

    let requestUrl = `${this.CompanyBaseUrl}/`;
    return this.http.post<OkApiResponseList<CompanyResponse>>(requestUrl, formData , FILE_HEADERS);
  }

  editCompany(idCompany: number, company: CompanyResponse): Observable<any> {
    let requestUrl = `${this.CompanyBaseUrl}/${idCompany}`;
    return this.http.put<OkApiResponseList<CompanyResponse>>(requestUrl, company, DEFAULT_HEADERS);
  }

  deleteCompany(idCompany: number): Observable<any> {
    let requestUrl = `${this.CompanyBaseUrl}/${idCompany}`;
    return this.http.delete<OkApiResponseList<CompanyResponse>>(requestUrl, DEFAULT_HEADERS);
  }
}
