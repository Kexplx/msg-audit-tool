import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'byId',
})
export class ByIdPipe implements PipeTransform {
  transform(values: any, id: number): any {
    return values?.find((v: { id: number }) => v.id === id);
  }
}
