import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';

import { Branch } from '../branch';
import { BranchService } from '../../shared/branch.service';
import { Company } from '../../company/company';
import { CompanyService } from '../../shared/company.service';
import { DialogService } from '../../shared/dialog.service';
import { NotificationService } from 'src/app/shared/notification.service';

@Component({
  selector: 'app-branch-listing',
  templateUrl: './branch-listing.component.html',
  styleUrls: ['./branch-listing.component.css']
})
export class BranchListingComponent implements OnInit {

  branches: Branch[];
  branchFields = [
    { value: "id", name: "Id" },
    { value: "name", name: "Name" },
    { value: "company.name", name: "CompanyName" }
  ];

  companies: Company[];
  company: Company;
  orderBy: string;

  // MatPaginator Inputs
  length = 100;
  pageSize = 10;
  pageIndex = 1;
  pageSizeOptions: number[] = [2, 4, 6, 8, 10];

  // MatPaginator Output
  page: PageEvent;

  constructor(
    private branchService: BranchService,
    private companyService: CompanyService,
    private dialog: MatDialog,
    private dialogService: DialogService,
    private notificationService: NotificationService
  ) { }

  ngOnInit() {
    this.getBranches();
    this.getCompanies();
  }

  getBranches() {
    this.branchService.getTotalCount(this.company).subscribe(size => {
      this.length = size;
      this.branchService.getBranches(this.company, this.pageIndex, this.pageSize, this.orderBy)
        .subscribe(data => this.branches = data);
    });
  }

  getCompanies() {
    this.companyService.getTotalCount().subscribe(size => {
      this.companyService.getCompanies(1, size, 'name')
        .subscribe(data => this.companies = data);
    });
  }

  delete(branch: Branch): void {
    this.dialogService.openConfirmDialog()
      .afterClosed().subscribe(res => {
        if (res) {
          this.branchService.deleteBranch(branch).subscribe(result => {
            this.notificationService.warn('Deleted successfully!');
            this.branchService.getTotalCount(this.company).subscribe(data => {
              this.length = data;
              this.getBranches();
            })
          });
        }
      })
  }

  refreshBranches() {
    this.getBranches();
  }

  paginateBranches(page: PageEvent) {
    this.pageIndex = page.pageIndex + 1;
    this.pageSize = page.pageSize;
    this.getBranches();
  }

  clearFilters() {
    this.orderBy = '';
    this.company = undefined;
    this.getBranches();
  }

}
