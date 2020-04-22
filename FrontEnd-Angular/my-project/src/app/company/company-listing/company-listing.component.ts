import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';

import { Company } from '../company';
import { CompanyService } from '../../shared/company.service';
import { DialogService } from '../../shared/dialog.service';
import { NotificationService } from '../../shared/notification.service';

@Component({
  selector: 'app-company-listing',
  templateUrl: './company-listing.component.html',
  styleUrls: ['./company-listing.component.css']
})
export class CompanyListingComponent implements OnInit {

  companies: Company[];
  orderBy: string;
  companyFields = [
    { value: "id", name: "Id" },
    { value: "name", name: "Name" }
  ];

  // MatPaginator Inputs
  length = 100;
  pageSize = 10;
  pageIndex = 1;
  pageSizeOptions: number[] = [2, 4, 6, 8, 10];

  // MatPaginator Output
  page: PageEvent;

  constructor(
    private companyService: CompanyService,
    private dialogService: DialogService,
    private notificationService: NotificationService
  ) { }

  ngOnInit() {
    this.getCompanies();
    this.getTotalCount();
  }

  getCompanies() {
    this.companyService.getCompanies(this.pageIndex, this.pageSize, this.orderBy)
      .subscribe(data => this.companies = data);
  }

  getTotalCount() {
    this.companyService.getTotalCount()
      .subscribe(data => this.length = data);
  }

  delete(company: Company): void {
    this.dialogService.openConfirmDialog()
      .afterClosed().subscribe(res => {
        if (res) {
          this.companyService.deleteCompany(company).subscribe(result => {
            this.notificationService.warn('Deleted successfully!');
            this.companyService.getTotalCount().subscribe(data => {
              this.length = data;
              this.getCompanies();
            });
          });
        }
      });
  }

  sortCompanies() {
    this.getCompanies();
  }

  paginateCompanies(page: PageEvent) {
    this.pageSize = page.pageSize;
    this.pageIndex = page.pageIndex + 1;
    this.getCompanies();
  }

  clearFilters() {
    this.orderBy = '';
    this.getCompanies();
  }

}
