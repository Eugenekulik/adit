import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'advStatus'
})
export class AdvStatusPipe implements PipeTransform {

  transform(value: number): string {
    return value == 1?"в обработке":value==2?"активный":"в архиве";
  }

}
