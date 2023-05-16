import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {FormControl, FormGroup} from "@angular/forms";
import {Advertisement} from "../../domain/advertisement";
import {AuthorizationService} from "../../service/authorization.service";
import {Category} from "../../domain/category";
import {faSearch} from "@fortawesome/free-solid-svg-icons/faSearch";
import {ModalDismissReasons, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {faTimes} from "@fortawesome/free-solid-svg-icons/faTimes";
import {faPlus} from "@fortawesome/free-solid-svg-icons/faPlus";
import {Feature} from "../../domain/feature";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-create-advertisement',
  templateUrl: './create-advertisement.component.html',
  styleUrls: ['./create-advertisement.component.css']
})
export class CreateAdvertisementComponent implements OnInit {
  private baseUrl = environment.baseUrl;
  advForm = new FormGroup({
    name: new FormControl(''),
    description: new FormControl(''),
    price: new FormControl(''),
  })

  faCross = faTimes
  images:any[]= [];
  words = new FormControl('');
  categories: Category[];

  currentCategory: Category | null;

  features: Feature[] = [];
  totalCategoryPage: number;
  faSearch = faSearch;

  closeResult = '';
  categoryPage =  0;
  faPlus = faPlus;
  showFeatures = false;
  constructor(private http: HttpClient, private auth: AuthorizationService, private modalService: NgbModal) {
  }

  ngOnInit(): void {
  }

  createAdvertisement() {
    const adv = {
      name: this.advForm.value.name,
      description: this.advForm.value.description,
      price: this.advForm.value.price,
      user: this.auth.getUser(),
      category: this.currentCategory,
      features: this.features
    }
    console.log(adv);
    this.http.post<Advertisement>(this.baseUrl+"advertisement", adv).subscribe(res => {
      console.log(res);
      this.images.forEach(img=>{
        const form = new FormData();
        form.append('file',img,img.name);
        form.append('advertisementId', res.id.toString())
        this.http.post(this.baseUrl+"images/add", form)
          .subscribe(resp => console.log(resp));
      })
    })
  }

  onFileSelect(event:any) {
    this.images.push(event.target.files[0])
    console.log(this.images);
  }

  searchCategories(page:number) {
    this.categoryPage = page;
    this.http.get(this.baseUrl+"category/search", {
      params: new HttpParams()
        .append('words', this.words.value==null?'':this.words.value)
        .append('page', page)
    }).subscribe((res:any) => {
      this.categories = res.content;
      this.totalCategoryPage = res.totalPages;
    })
  }

  chooseCategory(category: Category, modal: any) {
    this.currentCategory = category;
    this.features = this.recursiveFeatures(category);
    this.categories = [];
    this.words.setValue('');
    modal.close('close');
  }

  recursiveFeatures(category:Category):Feature[]{
    let features = new Array<Feature>();
    category.features.forEach(f=>{
      features.push(f);
    })
    if(category.parent != null){
      this.recursiveFeatures(category.parent).forEach(f=>{
        features.push(f);
      })
    }
    return features;
  }

  deleteCategory() {
    this.currentCategory = null;
  }


  open(content:any) {
    this.words.setValue('');
    this.categories = [];
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

  deleteImage(image: any) {
    const index = this.images.indexOf(image);
    if(index>-1)this.images.splice(index,1);
  }

  changeFeatureValue(event: any, feature: Feature) {
    feature.value = event.target.value;
  }
}
