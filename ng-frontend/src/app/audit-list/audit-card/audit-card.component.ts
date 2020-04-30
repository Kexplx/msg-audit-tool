import { Component, OnInit } from '@angular/core';
import { NbMenuService } from '@nebular/theme';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-audit-card',
  templateUrl: './audit-card.component.html',
  styleUrls: ['./audit-card.component.scss'],
})
export class AuditCardComponent implements OnInit {
  items = [{ title: 'Löschen' }, { title: 'Infos' }];

  constructor(private nbMenuService: NbMenuService) {}

  ngOnInit() {
    this.nbMenuService
      .onItemClick()
      .pipe(map(x => x.item.title))
      .subscribe(x => console.log(x));
  }
}
