import {Component, OnInit} from '@angular/core';
import {User} from "../../../domain/user";
import {HttpClient, HttpParams} from "@angular/common/http";
import {FormArray, FormControl, FormGroup} from "@angular/forms";
import {ModalDismissReasons, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Role} from "../../../domain/role";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-administrate-user',
  templateUrl: './administrate-user.component.html',
  styleUrls: ['./administrate-user.component.css']
})
export class AdministrateUserComponent implements OnInit {
  private baseUrl = environment.baseUrl;
  page: number = 0;
  users: User[] = [];
  totalPages: number;
  allRoles: Role[] = [];

  current: User;
  form =  new FormGroup({
    login: new FormControl(''),
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    age: new FormControl(Date.now().toString()),
    phone: new FormControl(''),
    email: new FormControl(''),
    id: new FormControl(0),
    roles: new FormArray([new FormControl(0)])
  });
  closeResult = '';

  constructor(private http: HttpClient, private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.getUsers(this.page)
  }

  getUsers(page: number) {
    this.page = page;
    return this.http.get(this.baseUrl+"user", {
      params: new HttpParams().append('page', page)
    }).subscribe((res: any) => {
      this.users = res.content;
      this.totalPages = res.totalPages;
    });
  }

  deleteUser(user: User) {
    this.http.delete(this.baseUrl+"user/" + user.id).subscribe(res => {
      const index = this.users.indexOf(user);
      if (index > -1) this.users.splice(index, 1);
    });
  }


  isAdmin(user: User): boolean {
    let result = false;
    user.roles?.forEach(role => {
      if (role.name == 'admin') result = true;
    })
    return result;
  }

  updateUser(modal: any) {
    const user = {
      login: this.form.value.login,
      firstName: this.form.value.firstName,
      lastName: this.form.value.lastName,
      age: this.form.value.age,
      phone: this.form.value.phone,
      email: this.form.value.email,
      roles: this.form.value.roles?.map(role=>{
        return this.allRoles.find(x=>x.roleId == role);
      }),
      id: this.form.value.id
    }
    this.http.patch(this.baseUrl+"user", user).subscribe((res:any) => {
      modal.close('save');
      this.users.splice(this.users.findIndex(usr=>usr.id == user.id),1);
      this.users.push(res);
    });

  }


  open(content: any, user: User) {
    this.current = user;
    this.http.get(this.baseUrl+"role").subscribe((res: any) => this.allRoles = res.content as Role[]);
    this.form.setControl('login', new FormControl(user.login));
    this.form.setControl('lastName', new FormControl(user.lastName));
    this.form.setControl('firstName', new FormControl(user.firstName));
    this.form.setControl('email', new FormControl(user.email));
    this.form.setControl('phone', new FormControl(user.phone));
    this.form.setControl('age', new FormControl(user.age.toString()));
    this.form.setControl('id', new FormControl(user.id));
    const rl = new FormArray(user.roles.map(role=>{
      return new FormControl(role.roleId);
    }));
    this.form.setControl('roles',rl);
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title', size: 'lg'}).result.then(
      (result) => {
        this.closeResult = `Closed with: ${result}`;
      },
      (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      },
    );
  }

  onCheckChange(event: any) {
    const formArray: FormArray = this.form.get('roles') as FormArray;

    /* Selected */
    if (event.target.checked) {
      // Add a new control in the arrayForm
      formArray.push(new FormControl(event.target.value));
    }
    /* unselected */
    else {
      // find the unselected element
      let i: number = 0;

      formArray.controls.forEach((ctrl: any) => {
        if (ctrl.value == event.target.value) {
          // Remove the unselected element from the arrayForm
          formArray.removeAt(i);
          return;
        }
        i++;
      });
    }
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

  checkedDef(role: Role): boolean {
    let index = this.current.roles.findIndex(x => x.name == role.name);
    return index > -1;
  }
}
