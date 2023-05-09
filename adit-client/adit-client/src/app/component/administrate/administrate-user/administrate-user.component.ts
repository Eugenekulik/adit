import { Component, OnInit } from '@angular/core';
import {User} from "../../../domain/user";
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-administrate-user',
  templateUrl: './administrate-user.component.html',
  styleUrls: ['./administrate-user.component.css']
})
export class AdministrateUserComponent implements OnInit {

  page: number = 0;
  users:User[] = [];
  totalPages: number;
  constructor(private http:HttpClient) { }
  ngOnInit(): void {
    this.getUsers(this.page)
  }

  getUsers(page:number){
    this.page = page;
    return this.http.get("http://localhost:8080/user",{
      params: new HttpParams().append('page',page)
    }).subscribe((res:any) =>{
      this.users = res.content;
      this.totalPages = res.totalPages;
    });
  }
}
