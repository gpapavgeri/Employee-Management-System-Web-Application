import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssetTypeListingComponent } from './asset-type-listing.component';

describe('AssetTypeListingComponent', () => {
  let component: AssetTypeListingComponent;
  let fixture: ComponentFixture<AssetTypeListingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssetTypeListingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssetTypeListingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
