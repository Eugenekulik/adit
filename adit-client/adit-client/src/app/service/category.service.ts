import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  baseUrl = environment.baseUrl;
  constructor(private http:HttpClient) { }

  getPage(page:number, field:string,direction:boolean){
    return this.http.get(this.baseUrl + 'category/page', {
      params: new HttpParams()
        .append('page',page)
        .append('field',field)
        .append('direction',direction)
    })
  }

}
