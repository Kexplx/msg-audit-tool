import { Pipe, PipeTransform } from '@angular/core';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';

@Pipe({
  name: 'criterias',
})
export class CriteriasPipe implements PipeTransform {
  transform(facCrits: FacCrit[], factorId: number) {
    return facCrits?.filter(x => x.referenceId === factorId);
  }
}
