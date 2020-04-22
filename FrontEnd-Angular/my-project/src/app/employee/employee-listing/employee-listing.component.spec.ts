import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeListingComponent } from './employee-listing.component';

describe('EmployeeListingComponent', () => {
  let component: EmployeeListingComponent;
  let fixture: ComponentFixture<EmployeeListingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmployeeListingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmployeeListingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
