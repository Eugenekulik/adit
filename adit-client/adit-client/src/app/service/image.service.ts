import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  baseUrl = environment.baseUrl;

  constructor(private http:HttpClient) { }


  getImageUrl(id:bigint):string{
    return this.baseUrl + 'images/' + id.toString();
  }

  saveImage(image:any, advertisementId:string){
    const form = new FormData();
    form.append('file',image,image.name);
    form.append('advertisementId', advertisementId)
    return this.http.post(this.baseUrl+"images/add", form);
  }
}
