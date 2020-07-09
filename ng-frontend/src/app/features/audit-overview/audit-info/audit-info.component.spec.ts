import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CoreModule } from 'src/app/core/core.module';
import { AuditInfoComponent } from './audit-info.component';
import { Router } from '@angular/router';

describe('AuditInfoComponent', () => {
  let component: AuditInfoComponent;
  let fixture: ComponentFixture<AuditInfoComponent>;

  beforeEach(async(() => {
    const routerStub = { url: '/audits/123/interviews' };

    TestBed.configureTestingModule({
      declarations: [AuditInfoComponent],
      imports: [CoreModule],
      providers: [{ provide: Router, useValue: routerStub }],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditInfoComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
