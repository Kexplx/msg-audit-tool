import { AuditOverviewComponent } from '../interview/audit-overview/audit-overview.component';

describe('AuditOverviewComponent', () => {
  it('should create', () => {
    const storeSpy = jasmine.createSpyObj('Store', ['dispatch']);
    const routeSpy = jasmine.createSpyObj('ActivatedRoute', ['dispatch']);
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    const component = new AuditOverviewComponent(storeSpy, routerSpy, routeSpy);

    expect(component).toBeTruthy();
  });
});
