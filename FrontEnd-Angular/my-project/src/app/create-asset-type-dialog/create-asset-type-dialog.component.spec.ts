import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAssetTypeDialogComponent } from './create-asset-type-dialog.component';

describe('CreateAssetTypeDialogComponent', () => {
  let component: CreateAssetTypeDialogComponent;
  let fixture: ComponentFixture<CreateAssetTypeDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateAssetTypeDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateAssetTypeDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
