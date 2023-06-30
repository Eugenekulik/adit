import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Category} from "../domain/category";

@Injectable({
  providedIn: 'root'
})
export class AdvertisementService {

  baseUrl = environment.baseUrl;
  constructor(private http:HttpClient) { }


  getPage(page:number, field:string, direction:boolean){
    if(field == '') field = 'placedAt';
    return this.http.get(this.baseUrl + 'advertisement',{
      params: new HttpParams()
        .append('page',page)
        .append('field',field)
        .append('direction',direction)
    })
  }


  getFavourites(userId: bigint){
      return this.http.get(this.baseUrl + 'advertisement/favourites',{
        params: new HttpParams()
          .append('userId', userId.toString())
      })
  }

  getByUser(userId: bigint, page:number){
    return this.http.get(this.baseUrl + 'advertisement/user/' + userId.toString(),{
      params: new HttpParams().append('page',page)
    });
  }

  getByCategory(category: Category|null, page: number, field:string, direction:boolean){
    if(field == '') field = 'placedAt';
    return this.http.get(this.baseUrl + 'advertisement/category',{
      params: new HttpParams()
        .append('page', page)
        .append('categoryId', category?category.categoryId.toString():'0')
        .append('field',field)
        .append('direction',direction)
    })
  }

  search(page: number, words:string, field:string, direction:boolean){
    return this.http.get(this.baseUrl + 'search',{
      params: new HttpParams()
        .append('page',page)
        .append('words',words)
        .append('field',field)
        .append('direction',direction)
    })
  }

  createAdvertisement(advertisment:any){
    return this.http.post(this.baseUrl + 'advertisement', advertisment);
  }

  getAdvertisement(id: string){
    return this.http.get(this.baseUrl + 'advertisement/'+ id)
  }

  addToFavorites(userId:string, advertisementId:string){
    return this.http.patch(this.baseUrl + 'user/favourites',{},{
      params: new HttpParams()
        .append("userId", userId)
        .append("advertisementId",advertisementId)
    })
  }




  deleteFromFavourites(userId: string, advertisementId: string) {
    return this.http.delete(this.baseUrl + 'user/favourites', {
      params: new HttpParams()
        .append('advertisementId', advertisementId)
        .append('userId', userId)
    });
  }
}
