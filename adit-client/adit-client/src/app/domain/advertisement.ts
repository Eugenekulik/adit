import {User} from "./user";
import {Feature} from "./feature";
import {Image} from "./image";
import { Category } from "./category";


export interface Advertisement {

  id:bigint;
  name:string;
  description:string;
  images:Image[];
  placedAt: Date;
  status:number;
  user: User;
  price: number;

  categories: Category;
  features: Feature[];
}
