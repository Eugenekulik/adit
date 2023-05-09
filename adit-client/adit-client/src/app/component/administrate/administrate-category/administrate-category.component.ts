import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Category} from "../../../domain/category";
import {Observable} from "rxjs";
import {ModalDismissReasons, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {faSearch} from "@fortawesome/free-solid-svg-icons/faSearch"
import {Feature} from "../../../domain/feature";

@Component({
  selector: 'app-administrate-category',
  templateUrl: './administrate-category.component.html',
  styleUrls: ['./administrate-category.component.css']
})
export class AdministrateCategoryComponent implements OnInit {
  page = 0;
  closeResult = '';
  words = '';
  current: Category;

  searchResult: Feature[] = [];

  faSearch =  faSearch;
  totalFeaturesPages: number;
  totalCategoryPages: number;

  constructor(private http:HttpClient, private modalService:NgbModal) { }
  open(content:any, category:Category) {
    this.words = '';
    this.searchResult = [];
    this.current = category;
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title', size: 'lg' }).result.then(
      (result) => {
        this.closeResult = `Closed with: ${result}`;
      },
      (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      },
    );
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  categories: Category[]

  ngOnInit(): void {
    this.getCategories(this.page);
  }

  getCategories(page:number){
    this.page = page;
    return this.http.get("http://localhost:8080/category", {
      params: new HttpParams().append('page', page)
    }).subscribe((res:any) =>{
      this.categories = res.content;
      this.totalCategoryPages = res.totalPages;
    });
  }

  deleteCategory(category: Category) {
    this.http.delete("http://localhost:8080/category/" + category.categoryId).subscribe(res=>{
      console.log(res);
    });
  }

  searchFeatures() {
    this.http.get("http://localhost:8080/feature/search", {
      params: new HttpParams().append('name', this.words)
    }).subscribe((res:any)=>{
      this.searchResult = res.content;
      this.totalFeaturesPages = res.totalPages;
    })
  }


  addFeature(feature: Feature) {
    this.current.features.push(feature);
    this.http.patch("http://localhost:8080/category/feature/add", {
      'categoryId':this.current.categoryId.toString(),
      'featureId': feature.featureId.toString()
    }).subscribe(res=>{
      console.log(res);
    })
  }

  deleteFeature(feature: Feature) {
    const index = this.current.features.indexOf(feature);
    if (index > -1) {
      this.current.features.splice(index, 1);
    }
    this.http.patch("http://localhost:8080/category/feature/delete", {
      'categoryId':this.current.categoryId.toString(),
      'featureId': feature.featureId.toString()
    }).subscribe(res=>{
      console.log(res);
    })
  }
}
