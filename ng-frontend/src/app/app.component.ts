import { Component, OnInit } from '@angular/core';
import { NbSidebarService } from '@nebular/theme';
import { Observable } from 'rxjs';
import { Audit } from './core/data/models/audit.model';
import { AuditStore } from './core/data/stores/audit.store';
import { IdService } from './core/id.service';
import { ContactPersonStore } from './core/data/stores/contact-person.store';
import { FacCritStore } from './core/data/stores/faccrit.store';
import { RoutesService } from './core/routes.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  audits$: Observable<Audit[]>;

  constructor(
    private sidebarService: NbSidebarService,
    private auditStore: AuditStore,
    private idService: IdService,
    private routesService: RoutesService,
    private contactPersonStore: ContactPersonStore,
    private facCritStore: FacCritStore,
  ) {}

  ngOnInit() {
    this.audits$ = this.auditStore.audits$;

    // Load initial data
    this.auditStore.loadAudits();
    this.contactPersonStore.loadContactPersons();
    this.facCritStore.loadFacCrits();

    //  Subscribe to router navigation.
    this.idService.subscribeToNavigation();
    this.routesService.subscribeToNavigation();
  }

  toggleSidebar() {
    this.sidebarService.toggle();
  }
}
