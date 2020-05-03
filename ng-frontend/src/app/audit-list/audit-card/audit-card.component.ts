import { Component, OnInit, Input } from '@angular/core';
import { NbMenuService, NbMenuItem } from '@nebular/theme';
import { map } from 'rxjs/operators';
import { Audit } from 'src/app/data/models/audit.model';

@Component({
  selector: 'app-audit-card',
  templateUrl: './audit-card.component.html',
  styleUrls: ['./audit-card.component.scss'],
})
export class AuditCardComponent implements OnInit {
  @Input() audit: Audit;
  items: NbMenuItem[] = [
    { title: 'Bearbeiten', icon: 'edit-outline' },
    { title: 'Löschen', icon: 'trash-outline' },
  ];

  constructor(private nbMenuService: NbMenuService) {}

  ngOnInit() {
    this.nbMenuService
      .onItemClick()
      .pipe(map(x => x.item.title))
      .subscribe(x => console.log(x));
  }
}
