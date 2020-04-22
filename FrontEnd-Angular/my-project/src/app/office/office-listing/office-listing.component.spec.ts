import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OfficeListingComponent } from './office-listing.component';

describe('OfficeListingComponent', () => {
  let component: OfficeListingComponent;
  let fixture: ComponentFixture<OfficeListingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OfficeListingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OfficeListingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
