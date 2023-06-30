import { Pipe, PipeTransform } from '@angular/core';
import {DatePipe} from "@angular/common";

@Pipe({
  name: 'customDatePipe'
})
export class CustomDatePipePipe implements PipeTransform {

  constructor(private date:DatePipe) {
  }
  transform(value: Date){
    if(new Date(value).getMilliseconds() > Date.now() - 3600*24*1000){
      return this.date.transform(value, 'shortTime');
    }
    return this.date.transform(value,'mediumDate');
  }

}
