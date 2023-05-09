import { Component, OnInit } from '@angular/core';
import {Feature} from "../../../domain/feature";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";

@Component({
  selector: 'app-administrate-feature',
  templateUrl: './administrate-feature.component.html',
  styleUrls: ['./administrate-feature.component.css']
})
export class AdministrateFeatureComponent implements OnInit {


  page = 0;
  features: Feature[] = [];
  totalPages: number;
  constructor(private http:HttpClient) { }
  ngOnInit(): void {
    this.getFeatures(this.page);
  }

  deleteFeature(feature: Feature) {
    const index = this.features.indexOf(feature);
    if (index > -1) {
      this.features.splice(index, 1);
    }
    this.http.delete("http://localhost:8080/feature/" + feature.featureId).subscribe();
  }


  getFeatures(page:number){
    this.page = page;
    return this.http.get("http://localhost:8080/feature",{
      params: new HttpParams().append('page', page)
    }).subscribe((res:any)=>{
      this.features = res.content;
      this.totalPages = res.totalPages;
    })
  }
}
