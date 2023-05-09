import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Advertisement} from "../../domain/advertisement";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit {

  page = 0;
  totalPages: number;
  set words(value: string) {
    this._words = value;
    this.find().subscribe((res:any)=>{
      this.advertisements = res.content;
      this.totalPages = res.totalPages;
    })
  }

  private _words:string;

  constructor(private http:HttpClient, private route:ActivatedRoute) {
  }

  advertisements: Advertisement[] = [];
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.words = params['words'];
    });
    this.find().subscribe((res:any)=>{
      this.advertisements = res.content;
      this.totalPages = res.totalPages;
    })
  }
  find(){
    return this.http.get("http://localhost:8080/search",{
      params: new HttpParams()
        .append("words",this._words)
        .append('page', this.page)
    });
  }
}