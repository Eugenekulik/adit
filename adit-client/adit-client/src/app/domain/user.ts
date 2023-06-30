import {Role} from "./role";
import {Advertisement} from "./advertisement";

export interface User {
  userId: bigint;
  login: string;
  firstName: string;
  lastName: string;
  phone: string;
  age: Date;
  email: string;

  roles:Role[];

  favourites: Advertisement[];

}
