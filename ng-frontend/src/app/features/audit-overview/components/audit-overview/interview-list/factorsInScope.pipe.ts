import { Pipe, PipeTransform } from '@angular/core';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';

@Pipe({
  name: 'factorsInScope',
})
export class FactorsInScopePipe implements PipeTransform {
  transform(scope: FacCrit[], all: FacCrit[]): FacCrit[] {
    const result = all
      .filter(x => !x.referenceId)
      .filter(f => scope.findIndex(k => k.id || k.referenceId === f.id) != -1);
    return result;
  }
}
