import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {HomeComponent} from "./component/home/home.component";
import {AboutComponent} from "./component/about/about.component";
import {SearchComponent} from "./component/search/search.component";
import {LoginComponent} from "./component/login/login.component";
import {AdministrateComponent} from "./component/administrate/administrate.component";
import {AdministrateAdvertisementComponent} from "./component/administrate/administrate-advertisement/administrate-advertisement.component";
import {AdministrateUserComponent} from "./component/administrate/administrate-user/administrate-user.component";
import {
  AdministrateCategoryComponent
} from "./component/administrate/administrate-category/administrate-category.component";
import {
  AdministrateFeatureComponent
} from "./component/administrate/administrate-feature/administrate-feature.component";
import {
  AdministrateAddressComponent
} from "./component/administrate/administrate-address/administrate-address.component";
import {AdvertisementComponent} from "./component/advertisement/advertisement.component";
import {
  AdvertisementEditComponent
} from "./component/administrate/administrate-advertisement/advertisement-edit/advertisement-edit.component";
import {CreateAdvertisementComponent} from "./component/create-advertisement/create-advertisement.component";


const routes: Routes = [
  {path: 'search/:words', component: SearchComponent},
  {path: 'home', component: HomeComponent},
  {path: 'about', component: AboutComponent},
  {path: 'login', component: LoginComponent},
  {path: 'create', component: CreateAdvertisementComponent},
  {path: 'administrate', component: AdministrateComponent,
    children: [
      {path: 'advertisement', component: AdministrateAdvertisementComponent},
      {path: 'advertisement/edit', component: AdvertisementEditComponent},
      {path: 'user', component: AdministrateUserComponent},
      {path: 'category', component: AdministrateCategoryComponent},
      {path: 'feature', component: AdministrateFeatureComponent},
      {path: 'address', component: AdministrateAddressComponent}
    ]}
]

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
