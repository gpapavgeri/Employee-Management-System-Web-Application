import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormBuilder, Validators, FormGroup, FormArray } from '@angular/forms';

import { Asset } from '../asset';
import { AssetService } from '../../shared/asset.service';
import { Company } from '../../company/company';
import { CompanyService } from '../../shared/company.service';
import { AssetType } from 'src/app/assetType/assetType';
import { AssetTypeService } from '../../shared/assetType.service';
import { DialogService } from '../../shared/dialog.service';
import { AssetOffice } from '../assetOffice';
import { Branch } from 'src/app/branch/branch';
import { BranchService } from 'src/app/shared/branch.service';
import { Office } from 'src/app/office/office';
import { OfficeService } from 'src/app/shared/office.service';

@Component({
  selector: 'app-asset-edit',
  templateUrl: './asset-edit.component.html',
  styleUrls: ['./asset-edit.component.css']
})
export class AssetEditComponent implements OnInit {

  assetForm: FormGroup;
  id: string;
  existed = true;
  assetInOffices = false;

  companies: Company[];
  company: Company;
  branches: Branch[];
  offices: Office[];
  assetTypes: AssetType[];

  constructor(
    private route: ActivatedRoute,
    private assetService: AssetService,
    private branchService: BranchService,
    private officeService: OfficeService,
    private location: Location,
    private fb: FormBuilder,
    private companyService: CompanyService,
    private assetTypeService: AssetTypeService,
    private dialogService: DialogService
  ) { }

  ngOnInit(): void {
    this.getCompanies();
    this.getAssetTypes();
    this.id = this.route.snapshot.paramMap.get('id');
    if (this.id != null) {
      this.getAsset();
    } else {
      this.assetForm = this.buildForm();
      this.existed = false;
    }

  }

  getCompanies() {
    this.companyService.getTotalCount().subscribe(size => {
      this.companyService.getCompanies(1, size, 'name')
        .subscribe(data => this.companies = data);
    });
  }

  getAssetTypes() {
    this.assetTypeService.getTotalCount().subscribe(size => {
      this.assetTypeService.getAssetTypes(1, size, 'type')
        .subscribe(data => this.assetTypes = data);
    });
  }

  getAsset(): void {
    this.assetService.getAsset(this.id)
      .subscribe(asset => {
        this.assetForm = this.buildForm(asset);
        this.company = asset.company;
        this.getBranches();
        if (asset.offices != null && Array.isArray(asset.offices)) {
          asset.offices.forEach(office => {
            (this.assetForm.get('offices') as FormArray).push(this.addOfficeFormGroup(office));
          });
          this.assetInOffices = true;
        }
      });
  }

  buildForm(asset?: Asset): FormGroup {
    return this.fb.group({
      id: [asset != null ? asset.id : null],
      serialNumber: [asset != null ? asset.serialNumber : null, Validators.required],
      brand: [asset != null ? asset.brand : null, Validators.required],
      company: [asset != null ? asset.company.name : null, Validators.required],
      assetType: [asset != null ? asset.assetType : null, Validators.required],
      offices: this.fb.array([])
    });
  }

  addOfficeFormGroup(office?: AssetOffice): FormGroup {
    return this.fb.group({
      officeId: [office != null ? office.officeId : null, Validators.required],
      dateFrom: [office != null && office.dateFrom != null ? office.dateFrom : null],
      dateTo: [office != null && office.dateTo != null ? office.dateTo : null],
    });
  }

  addOffice(): void {
    (<FormArray>this.assetForm.get('offices')).push(this.addOfficeFormGroup());
  }

  removeOffice(officeGroupIndex: number): void {
    const officesFormArray = <FormArray>this.assetForm.get('offices');
    officesFormArray.removeAt(officeGroupIndex);
    if (officesFormArray.length == 0) {
      this.assetInOffices = false;
    }
  }

  goBack(): void {
    this.location.back();
  }

  onCompanySelection(event: any) {
    this.company = event.value;
    this.getBranches();
  }

  getBranches() {
    if (this.company != null) {
      this.branchService.getTotalCount().subscribe(size => {
        this.branchService.getBranches(this.company, 1, size, 'name')
          .subscribe(data => {
            this.branches = data;
            this.getOffices();
          });
      })
    }
  }

  getOffices() {
    this.officeService.getTotalCount().subscribe(size => {
      this.officeService.getOffices(this.company, null, 1, size, 'code')
        .subscribe(data => {
          this.offices = data;
          this.assignOfficesToBranches();
        });
    });
  }

  assignOfficesToBranches() {
    if (this.branches != null) {
      this.branches.forEach(branch => {
        if (branch.offices != null) {
          branch.offices.length = 0;
        }
      });
      this.offices.forEach(office => {
        this.branches.forEach(branch => {
          if (office.branch.id === branch.id) {
            if (branch.offices == null) {
              branch.offices = [];
            }
            branch.offices.push(office);
          }
        });
      });
    }
  }

  onSubmit() {
    const asset = this.assetForm.getRawValue();
    console.log(asset);
    if (asset.id != null && asset.id.length > 0) {
      this.assetService.updateAsset(asset)
        .subscribe(() => this.goBack());
    } else {
      this.assetService.addAsset(asset)
        .subscribe(() => this.goBack());
    }
  }

  openDialog() {
    this.dialogService.openNewAssetTypeDialog()
      .afterClosed().subscribe(res => {
        if (res) {
          let newAssetType: AssetType = {
            id: '',
            type: res
          };
          this.assetForm.get('assetType').setValue(newAssetType);
          this.assetForm.get('assetType').disable();
        }
      });
  }

  openOffices() {
    this.assetInOffices = true;
    this.addOffice();
    this.getBranches();
  }

  compareFn(assetType1: AssetType, assetType2: AssetType): boolean {
    return assetType1.id === assetType2.id;
  }

  compareCompany(company1: Company, company2: Company): boolean {
    return company1.id === company2.id;
  }

}
