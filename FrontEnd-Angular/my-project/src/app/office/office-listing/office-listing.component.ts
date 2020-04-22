import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';

import { Office } from '../office';
import { OfficeService } from '../../shared/office.service';
import { Company } from '../../company/company';
import { CompanyService } from '../../shared/company.service';
import { Branch } from '../../branch/branch';
import { BranchService } from '../../shared/branch.service';
import { DialogService } from '../../shared/dialog.service';
import { NotificationService } from 'src/app/shared/notification.service';


@Component({
  selector: 'app-office-listing',
  templateUrl: './office-listing.component.html',
  styleUrls: ['./office-listing.component.css']
})
export class OfficeListingComponent implements OnInit {

  offices: Office[];

  officeFields = [
    { value: "id", name: "Id" },
    { value: "code", name: "Code" },
    { value: "branch.name", name: "Branch" }
  ];

  companies: Company[];
  company: Company;
  branches: Branch[];
  branch: Branch;
  orderBy: string;

  // MatPaginator Inputs
  length = 100;
  pageSize = 10;
  pageIndex = 1;
  pageSizeOptions: number[] = [2, 4, 6, 8, 10];

  // MatPaginator Output
  page: PageEvent;

  constructor(
    private officeService: OfficeService,
    private companyService: CompanyService,
    private branchService: BranchService,
    private dialogService: DialogService,
    private notificationService: NotificationService
  ) { }

  ngOnInit() {
    this.getOffices();
    this.getCompanies();
    this.getBranches();
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
          this.assignBranchesToCompanies();
        });
    });
  }

  assignBranchesToCompanies() {
    if (this.companies != null) {
      this.companies.forEach(company=>{
        if(company.branches!=null){
          company.branches.length=0;
        }
      });
      this.companies.forEach(company => {
        this.branches.forEach(branch => {
          if (company.id === branch.company.id) {
            if (company.branches == null) {
              company.branches = [];
            }
            company.branches.push(branch);
          }
        })
      });
    }
  }

  getOffices() {
    this.officeService.getTotalCount(this.company, this.branch).subscribe(size => {
      this.length = size;
      this.officeService.getOffices(this.company, this.branch, this.pageIndex, this.pageSize, this.orderBy)
        .subscribe(data => this.offices = data);
    })
  }

  delete(office: Office): void {
    this.dialogService.openConfirmDialog()
      .afterClosed().subscribe(res => {
        if (res) {
          this.officeService.deleteOffice(office).subscribe(result => {
            this.notificationService.warn('Deleted successfully!');
            this.officeService.getTotalCount().subscribe(data => {
              this.length = data;
              this.getOffices();
            })
          });
        }
      })
  }

  selectCompany() {
    this.getOffices();
    if (this.company != null) {
      this.companies.splice(0, this.companies.length, this.company);
      if (this.company.branches != null) {
        this.company.branches.length = 0;
      }
    }
    this.getBranches();
  }

  refreshCompanies() {
    this.branch = undefined;
    this.getCompanies();
    this.getOffices();
  }

  refreshOffices() {
    this.getOffices();
  }

  paginateOffices(page: PageEvent) {
    this.pageIndex = page.pageIndex + 1;
    this.pageSize = page.pageSize;
    this.getOffices();
  }

  clearFilters() {
    this.orderBy = '';
    this.company = undefined;
    this.branch = undefined;
    this.getCompanies();
    this.getBranches();
    this.getOffices();
  }

  compareFn(office1: Office, office2: Office): boolean {
    return office1.id === office2.id;
  }

}
