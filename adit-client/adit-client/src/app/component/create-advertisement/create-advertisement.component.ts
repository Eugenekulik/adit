import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FormArray, FormControl, FormGroup} from "@angular/forms";
import {Advertisement} from "../../domain/advertisement";
import {AuthorizationService} from "../../service/authorization.service";

@Component({
  selector: 'app-create-advertisement',
  templateUrl: './create-advertisement.component.html',
  styleUrls: ['./create-advertisement.component.css']
})
export class CreateAdvertisementComponent implements OnInit {

  advForm = new FormGroup({
    name: new FormControl(''),
    description: new FormControl(''),
    price: new FormControl(''),
  })

  images:any[]= [];

  constructor(private http: HttpClient, private auth: AuthorizationService) {
  }

  ngOnInit(): void {
  }

  createAdvertisement() {
    const adv = {
      name: this.advForm.value.name,
      description: this.advForm.value.description,
      price: this.advForm.value.price,
      user: this.auth.getUser()
    }
    console.log(adv);
    this.http.post<Advertisement>("http://localhost:8080/advertisement", adv).subscribe(res => {
      console.log(res);
      const form = new FormData();
      this.images.forEach(img=>{
        form.append('file',img,img.name);
      })
      form.append('advertisementId', res.id.toString())
      this.http.post("http://localhost:8080/images/add", form)
        .subscribe(resp => console.log(resp))
    })
  }

  onFileSelect(event:any) {
    this.images.push(event.target.files[0])
    console.log(this.images);
  }
}
