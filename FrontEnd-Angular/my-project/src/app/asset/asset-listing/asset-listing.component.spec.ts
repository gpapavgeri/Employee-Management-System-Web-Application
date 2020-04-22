import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssetListingComponent } from './asset-listing.component';

describe('AssetListingComponent', () => {
  let component: AssetListingComponent;
  let fixture: ComponentFixture<AssetListingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssetListingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssetListingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
