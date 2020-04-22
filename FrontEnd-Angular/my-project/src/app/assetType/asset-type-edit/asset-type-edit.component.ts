import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { AssetType } from '../assetType';
import { AssetTypeService } from '../../shared/assetType.service';

@Component({
  selector: 'app-asset-type-edit',
  templateUrl: './asset-type-edit.component.html',
  styleUrls: ['./asset-type-edit.component.css']
})
export class AssetTypeEditComponent implements OnInit {

  assetTypeForm: FormGroup;
  id: string;
  existed = true;

  constructor(
    private route: ActivatedRoute,
    private assetTypeService: AssetTypeService,
    private location: Location,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    if (this.id != null) {
      this.getAssetType();
    } else {
      this.assetTypeForm = this.buildForm();
      this.existed = false;
    }
  }

  getAssetType(): void {
    this.assetTypeService.getAssetType(this.id)
      .subscribe(assetType => this.assetTypeForm = this.buildForm(assetType));
  }

  buildForm(assetType?: AssetType): FormGroup {
    return this.fb.group({
      id: [assetType != null ? assetType.id : null],
      type: [assetType != null ? assetType.type : null, Validators.required]
    });
  }

  goBack(): void {
    this.location.back();
  }

  onSubmit() {
    const assetType = this.assetTypeForm.value;
    if (assetType.id != null && assetType.id.length > 0) {
      // UPDATE ASSETTYPE
      this.assetTypeService.updateAssetType(assetType)
        .subscribe(() => this.goBack());
    } else {
      // ADD ASSETTYPE
      this.assetTypeService.addAssetType(assetType)
        .subscribe(() => this.goBack());
    }
  }

}
