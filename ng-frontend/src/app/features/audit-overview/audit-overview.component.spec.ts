import { AuditOverviewComponent } from './audit-overview.component';

describe('AuditOverviewComponent', () => {
  it('should create', () => {
    const storeSpy = jasmine.createSpyObj('Store', ['dispatch']);

    const component = new AuditOverviewComponent(storeSpy);

    expect(component).toBeTruthy();
  });
});
