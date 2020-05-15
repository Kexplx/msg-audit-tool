import { FactorListComponent } from './factor-list.component';

describe('FactorListComponent', () => {
  it('should create', () => {
    const storeSpy = jasmine.createSpyObj('Store', ['dispatch']);
    const routeSpy = jasmine.createSpyObj('ActivatedRoute', ['dispatch']);

    const component = new FactorListComponent(storeSpy, routeSpy);

    expect(component).toBeTruthy();
  });
});
