import { Pipe, PipeTransform } from '@angular/core';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';

@Pipe({
  name: 'factors',
})
export class FactorsPipe implements PipeTransform {
  transform(facCrits: FacCrit[]) {
    return facCrits?.filter(x => !x.referenceId);
  }
}
