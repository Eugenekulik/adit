import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-feature',
  templateUrl: './create-feature.component.html',
  styleUrls: ['./create-feature.component.css']
})
export class CreateFeatureComponent implements OnInit {
  baseUrl = environment.baseUrl;

  feature = new FormGroup({
    name: new FormControl(''),
    description: new FormControl('')
  });

  constructor(private http: HttpClient, private router:Router) { }

  ngOnInit(): void {
  }

  createFeature() {
    this.http.post(this.baseUrl + 'feature', {
      name: this.feature.value.name,
      description: this.feature.value.description
    }).subscribe((res:any)=>{
      this.router.navigate(['/administrate/feature']);
    })
  }
}
