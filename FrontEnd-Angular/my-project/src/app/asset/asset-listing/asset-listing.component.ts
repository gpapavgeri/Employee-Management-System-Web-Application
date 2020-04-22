import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';

import { Asset } from '../asset';
import { AssetService } from '../../shared/asset.service';
import { Company } from '../../company/company';
import { CompanyService } from '../../shared/company.service';
import { AssetType } from '../../assetType/assetType';
import { AssetTypeService } from '../../shared/assetType.service';
import { DialogService } from '../../shared/dialog.service';
import { NotificationService } from 'src/app/shared/notification.service';
import { Branch } from 'src/app/branch/branch';
import { Office } from 'src/app/office/office';
import { BranchService } from 'src/app/shared/branch.service';
import { OfficeService } from 'src/app/shared/office.service';

@Component({
  selector: 'app-asset-listing',
  templateUrl: './asset-listing.component.html',
  styleUrls: ['./asset-listing.component.css']
})
export class AssetListingComponent implements OnInit {

  assets: Asset[];
  assetFields = [
    { value: "id", name: "Id" },
    { value: "serialNumber", name: "SerialNumber" },
    { value: "brand", name: "Brand" },
    { value: "company.name", name: "Company" },
    { value: "assetType.type", name: "AssetType" }
  ];

  companies: Company[];
  company: Company;
  branches: Branch[];
  branch: Branch;
  offices: Office[];
  office: Office;
  assetTypes: AssetType[] = [];
  assetType: AssetType;
  orderBy: string;

  // MatPaginator Inputs
  length = 100;
  pageSize = 10;
  pageIndex = 1;
  pageSizeOptions: number[] = [2, 4, 6, 8, 10];

  // MatPaginator Output
  page: PageEvent;

  constructor(
    private assetService: AssetService,
    private companyService: CompanyService,
    private branchService: BranchService,
    private officeService: OfficeService,
    private assetTypeService: AssetTypeService,
    private dialogService: DialogService,
    private notificationService: NotificationService
  ) { }

  ngOnInit() {
    this.getAssets();
    this.getCompanies();
    this.getBranches();
  }

  getAssets() {
    this.assetService.getTotalCount(this.company, this.office, this.assetType).subscribe(size => {
      this.length = size;
      this.assetService.getAssets(this.company, this.office, this.assetType, this.pageIndex, this.pageSize, this.orderBy)
        .subscribe(data => {
          this.assets = data;
          if (this.assetType == null) {
            this.assetTypes.length = 0;
            this.assets.forEach(asset => {
              if (this.assetTypes.indexOf(asset.assetType) === -1) {
                this.assetTypes.push(asset.assetType);
              }
            });
          }
        });
    });
  }

  getCompanies() {
    this.companyService.getTotalCount().subscribe(size => {
      this.companyService.getCompanies(1, size, 'name')
        .subscribe(data => this.companies = data);
    });
  }

  getBranches() {
    this.branchService.getTotalCount().subscribe(size => {
      this.branchService.getBranches(this.company, 1, size, 'name')
        .subscribe(data => {
          if (this.branches != null) {
            this.branches.length = 0;
          }
          this.branches = data;
          this.getOffices();
        });
    });
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

  delete(asset: Asset): void {
    this.dialogService.openConfirmDialog()
      .afterClosed().subscribe(res => {
        if (res) {
          this.assetService.deleteAsset(asset).subscribe(result => {
            this.notificationService.warn(result.serialNumber + ' deleted successfully!');
            this.assetService.getTotalCount().subscribe(data => {
              this.length = data;
              this.getAssets();
            });
          });
        }
      });
  }

  refresh() {
    this.branch = undefined;
    this.office = undefined;
    this.assetType = undefined;
    this.getAssets();
    this.getCompanies();
    this.getBranches();
  }

  selectCompany() {
    if (this.company != null) {
      this.companies.splice(0, this.companies.length, this.company);
    }
    this.refresh();
  }

  refreshCompanies() {
    this.company = undefined;
    this.refresh();
  }

  refreshAssets() {
    this.getAssets();
  }

  clearFilters() {
    this.orderBy = '';
    this.refreshCompanies();
  }

  paginateAssets(page: PageEvent) {
    this.pageIndex = page.pageIndex + 1;
    this.pageSize = page.pageSize;
    this.getAssets();
  }

  compareFn(asset1: Asset, asset2: Asset): boolean {
    return asset1.id === asset2.id;
  }

}
