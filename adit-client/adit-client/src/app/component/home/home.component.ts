import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Category} from "../../domain/category";
import {faAd} from "@fortawesome/free-solid-svg-icons/faAd";
import {Advertisement} from "../../domain/advertisement";
import {faArrowLeft} from "@fortawesome/free-solid-svg-icons/faArrowLeft";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  faBack = faArrowLeft;
  faAd = faAd;
  categories: Category[];

  parent: Category|null;

  advertisements: Advertisement[] = [];

  constructor(private http:HttpClient) { }

  ngOnInit(): void {
    this.getCategories(null);
  }

  up(parent:Category|null){
    this.parent = parent;
    this.getCategories(parent);
  }
  getCategories(parent:Category|null){
    if(parent != null){
    this.http.get<Category[]>("http://localhost:8080/category/children", {
      params: new HttpParams().append('parentId', parent.categoryId.toString())
    }).subscribe(res => {
      this.categories = res;
    });
    this.getAdv(0,parent);
    }else {
      this.http.get<Category[]>("http://localhost:8080/category/children").subscribe(res => {
        this.categories = res;
      })
      this.advertisements = [];
    }
  }

  getAdv(page:number, category:Category){
    this.http.get("http://localhost:8080/advertisement/category", {
      params: new HttpParams()
        .append('categoryId', category.categoryId.toString())
        .append('page', page)
    }).subscribe((res:any)=>{
      console.log(res);
      this.advertisements = res.content;
    })
  }

  back() {
    this.parent = this.parent!.parent;
    this.getCategories(this.parent);
  }
}
