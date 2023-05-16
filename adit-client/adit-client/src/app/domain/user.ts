import {Role} from "./role";

export interface User {
  id: number;
  login: string;
  firstName: string;
  lastName: string;
  phone: string;
  age: Date;
  email: string;

  roles:Role[];

}
