import { Pipe, PipeTransform } from '@angular/core';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';

@Pipe({
  name: 'criteriaByFactor',
})
export class CriteriaByFactorPipe implements PipeTransform {
  transform(facCrits: FacCrit[], factorId: string): FacCrit[] {
    return facCrits.filter(x => x.referenceId === factorId);
  }
}
