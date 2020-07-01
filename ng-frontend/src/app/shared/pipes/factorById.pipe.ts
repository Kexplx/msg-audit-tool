import { Pipe, PipeTransform } from '@angular/core';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';

@Pipe({
  name: 'factorById',
})
export class FactorByIdPipe implements PipeTransform {
  transform(facCrits: FacCrit[], id: number): FacCrit {
    return facCrits.find(x => x.id === id);
  }
}
