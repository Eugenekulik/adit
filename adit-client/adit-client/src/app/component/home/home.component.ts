import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Category} from "../../domain/category";
import {Advertisement} from "../../domain/advertisement";
import {environment} from "../../../environments/environment";
import {AddressService} from "../../service/address.service";
import {Router} from "@angular/router";
import {ImageService} from "../../service/image.service";
import {AdvertisementService} from "../../service/advertisement.service";
import {AuthorizationService} from "../../service/authorization.service";
import {Feature} from "../../domain/feature";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  baseUrl = environment.baseUrl;

  //Sort attributes

  field: string;
  direction: boolean;



  categories: Category[];
  parent: Category | null;
  advertisements: Advertisement[] = [];
  page = 0;
  totalPages: number;

  constructor(private http: HttpClient,
              public addressService: AddressService,
              private router: Router,
              public imageService: ImageService,
              private advertisementService: AdvertisementService,
              private auth: AuthorizationService) {
  }

  ngOnInit(): void {
    this.getCategories(null);
    this.getAdv(0,null)
  }

  up(parent: Category | null) {
    this.parent = parent;
    this.getCategories(parent);
  }

  changeSort(field:string, direction:boolean){
    this.direction = direction;
    this.field = field;
    this.page = 0;
    this.advertisementService.getByCategory(this.parent,0,field,direction)
      .subscribe((res:any)=>{
        this.advertisements = res.content;
        this.totalPages = res.totalPages;
      })
  }

  getCategories(parent: Category | null) {
    if (parent != null) {
      this.http.get<Category[]>(this.baseUrl + "category/children", {
        params: new HttpParams().append('parentId', parent.categoryId.toString())
      }).subscribe(res => {
        this.categories = res.sort(this.sortByName);
      });
      this.getAdv(0, parent);
    } else {
      this.http.get<Category[]>(this.baseUrl + "category/children").subscribe(res => {
        this.categories = res.sort(this.sortByName);
      })
      this.advertisements = [];
    }
  }

  getAdv(page: number, category: Category|null) {
    this.page = page;
    this.advertisementService.getByCategory(category,page,'',true)
      .subscribe((res:any)=>{
        this.advertisements = res.content;
        this.totalPages = res.totalPages;
      })
  }

  back() {
    this.parent = this.parent!.parent;
    this.getCategories(this.parent);
    this.getAdv(0,this.parent);
  }

  sortByName(a: Category, b: Category): number {
    return a.name < b.name ? -1 : 1
  }

  openAdvertisement(advertisement: Advertisement) {
    this.router.navigate(['/advertisement'], {queryParams: {id: advertisement.id.toString()}});
  }

}
