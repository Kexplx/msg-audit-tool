import { Pipe, PipeTransform } from '@angular/core';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';

@Pipe({
  name: 'criteriaByFactorId',
})
export class CriteriaByFactorIdPipe implements PipeTransform {
  transform(facCrits: FacCrit[], factorId: number): FacCrit[] {
    return facCrits?.filter(x => x.referenceId === factorId);
  }
}
