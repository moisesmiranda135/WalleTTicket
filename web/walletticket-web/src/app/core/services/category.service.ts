import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CategoryResponse } from '../entity/category/categoryListResponse';
import { OkApiResponseList } from '../util/apiResponseInterface';


const AUTH_BASE_URL = 'category';
var token = localStorage.getItem('request_token');
const DEFAULT_HEADERS = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  })
};

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  categoryBaseUrl = `${environment.apiLocalUrl}/${AUTH_BASE_URL}`;

  constructor(private http: HttpClient) { }

  getList(): Observable<any> {
    let requestUrl = `${this.categoryBaseUrl}/`;
    return this.http.get<OkApiResponseList<CategoryResponse>>(requestUrl, DEFAULT_HEADERS);
  }

  createCategory(category: CategoryResponse): Observable<any> {
    let requestUrl = `${this.categoryBaseUrl}/`;

    let headers = new HttpHeaders({'Content-Type':'multipart/form-data'});
    let formData = new FormData();

    // formData.append("json", category);

    return this.http.post<OkApiResponseList<CategoryResponse>>(requestUrl, category, DEFAULT_HEADERS);
  }

  editCategory(idCategory: number, category: CategoryResponse): Observable<any> {
    let requestUrl = `${this.categoryBaseUrl}/${idCategory}`;
    return this.http.put<OkApiResponseList<CategoryResponse>>(requestUrl, category, DEFAULT_HEADERS);
  }

  deleteCategory(idCategory: number): Observable<any> {
    let requestUrl = `${this.categoryBaseUrl}/${idCategory}`;
    return this.http.delete<OkApiResponseList<CategoryResponse>>(requestUrl, DEFAULT_HEADERS);
  }
}
