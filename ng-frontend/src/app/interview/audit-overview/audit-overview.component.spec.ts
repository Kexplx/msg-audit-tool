import { AuditOverviewComponent } from './audit-overview.component';

describe('AuditOverviewComponent', () => {
  it('should create', () => {
    const storeSpy = jasmine.createSpyObj('Store', ['dispatch']);
    const routeSpy = jasmine.createSpyObj('ActivatedRoute', ['dispatch']);

    const component = new AuditOverviewComponent(storeSpy, routeSpy);

    expect(component).toBeTruthy();
  });
});
