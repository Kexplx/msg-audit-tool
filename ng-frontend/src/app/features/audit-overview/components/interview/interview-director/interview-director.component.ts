import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { Store } from '@ngxs/store';
import { UpdateInterview } from 'src/app/core/ngxs/actions/inteview.actions';

@Component({
  selector: 'app-interview-director',
  templateUrl: './interview-director.component.html',
  styleUrls: ['./interview-director.component.scss'],
})
export class InterviewDirectorComponent implements OnInit {
  constructor(private store: Store) {}

  ngOnInit() {
    this.goalDebounce$.pipe(debounceTime(1000)).subscribe(goal => {
      // this.store.dispatch(new UpdateInterview())

      console.log(goal);
    });
  }

  goal = 'Hello World';
  goalDebounce$ = new Subject<string>();

  onGoalInput(value: string) {
    this.goalDebounce$.next(value);
  }
  onNavigateForward(facCritId: number) {
    const indexOfFacCrit = this.facCritIds.indexOf(facCritId);

    if (indexOfFacCrit + 1 !== this.facCritIds.length) {
      this.router.navigate([String(this.facCritIds[indexOfFacCrit + 1])], {
        relativeTo: this.activatedRoute,
      });
    }
  }

  onNaviagteBack(facCritId: number) {
    const indexOfFacCrit = this.facCritIds.indexOf(facCritId);

    if (indexOfFacCrit > 0) {
      this.router.navigate([String(this.facCritIds[indexOfFacCrit - 1])], {
        relativeTo: this.activatedRoute,
      });
    }
  }

}
