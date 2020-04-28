import { Component, OnInit } from '@angular/core';
import { NbMenuService } from '@nebular/theme';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-project-card',
  templateUrl: './project-card.component.html',
  styleUrls: ['./project-card.component.scss'],
})
export class ProjectCardComponent implements OnInit {
  items = [{ title: 'Löschen' }, { title: 'Infos' }];

  constructor(private nbMenuService: NbMenuService) {}

  ngOnInit() {
    this.nbMenuService
      .onItemClick()
      .pipe(map(x => x.item.title))
      .subscribe(x => console.log(x));
  }
}
