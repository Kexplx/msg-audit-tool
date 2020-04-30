import { AuditListComponent } from './audit-list.component';

describe('AuditListComponent', () => {
  let component: AuditListComponent;

  beforeEach(() => {
    const dialogServiceStub = jasmine.createSpyObj('NbDialogService', ['open']);
    component = new AuditListComponent(dialogServiceStub);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
