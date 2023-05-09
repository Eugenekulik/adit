import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute} from "@angular/router";
import {Advertisement} from "../../../../domain/advertisement";

@Component({
  selector: 'app-advertisement-edit',
  templateUrl: './advertisement-edit.component.html',
  styleUrls: ['./advertisement-edit.component.css']
})
export class AdvertisementEditComponent implements OnInit {

  advertisement: Advertisement;

  constructor(private http:HttpClient) {
  }

  ngOnInit(): void {
    this.advertisement = JSON.parse(localStorage.getItem('adv')!);
    localStorage.removeItem('adv');
  }

}
