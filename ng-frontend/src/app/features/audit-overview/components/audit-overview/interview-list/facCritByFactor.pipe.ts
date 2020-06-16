import { Pipe, PipeTransform } from '@angular/core';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { retry } from 'rxjs/operators';

@Pipe({
  name: 'facCritByFactor',
})
export class FacCritByFactorPipe implements PipeTransform {
  transform(facCrits: FacCrit[], factorId: string): FacCrit[] {
    const result = facCrits.filter(x => x.id === factorId || x.referenceId == factorId);
    return result;
  }
}
