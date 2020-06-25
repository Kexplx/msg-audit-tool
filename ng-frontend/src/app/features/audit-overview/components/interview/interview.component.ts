import { Component, OnInit, HostListener } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Interview } from 'src/app/core/data/models/interview.model';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { Select, Store } from '@ngxs/store';
import { AuditState } from 'src/app/core/ngxs/audit.state';

@Component({
  selector: 'app-interview',
  templateUrl: './interview.component.html',
  styleUrls: ['./interview.component.scss'],
})
export class InterviewComponent implements OnInit {
  interview$: Observable<Interview>;
  facCrit$: Observable<FacCrit>;

  @Select(AuditState.facCrits) facCrits$: Observable<FacCrit[]>;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private store: Store,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      const facCritId = params.get('facCritId');
      const interviewId = params.get('interviewId');

      if (!facCritId || !interviewId) {
        this.router.navigate(['/audits']);
      }

      this.facCrit$ = this.store.select(AuditState.facCrit(facCritId));
      this.interview$ = this.store.select(AuditState.interview(interviewId));

      this.facCrit$.subscribe(facCrit => facCrit ?? this.router.navigate(['/audits']));
      this.interview$.subscribe(interview => interview ?? this.router.navigate(['/audits']));
    });
  }

  /**
   * Handles the transformation of the goals accordeon from standard layout to a fixed position
   */
  @HostListener('window:scroll', [])
  onWindowScroll() {
    // determine current position on page
    const scrollPosition =
      window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
    // determine guard div position on page
    const guardY = document.querySelector('#guard').getBoundingClientRect().y;
    if (scrollPosition >= guardY) {
      // get height and width of "goals-item" accordeon item to set the width and the height after the "goals" accordeon gets taken out of the grid
      const goalHeight = document.querySelector('#goals-item').clientHeight + 40;
      const goalWidth = document.querySelector('#goals-item').clientWidth;
      // add padding to the "pad" div with goalHeight to stop the body from shrinking
      document.getElementById('pad').style.paddingTop = goalHeight.toString() + 'px';
      // fix the width of the "goals" accordeon
      document.getElementById('goals').style.width = goalWidth.toString() + 'px';
      // add css class to "goals" accordeon to set position:fixed
      document.querySelector('#goals').classList.add('goals-not-at-top');
    } else {
      // remove padding
      document.getElementById('pad').style.paddingTop = '0px';
      // remove css class with position:fixed
      document.querySelector('#goals').classList.remove('goals-not-at-top');
    }
  }
}
