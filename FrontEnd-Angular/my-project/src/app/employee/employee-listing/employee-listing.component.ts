import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';

import { Employee } from '../employee';
import { EmployeeService } from '../../shared/employee.service';
import { Office } from '../../office/office';
import { OfficeService } from '../../shared/office.service';
import { DialogService } from '../../shared/dialog.service';
import { NotificationService } from 'src/app/shared/notification.service';
import { Company } from 'src/app/company/company';
import { CompanyService } from 'src/app/shared/company.service';
import { Branch } from 'src/app/branch/branch';
import { BranchService } from 'src/app/shared/branch.service';

@Component({
  selector: 'app-employee-listing',
  templateUrl: './employee-listing.component.html',
  styleUrls: ['./employee-listing.component.css']
})
export class EmployeeListingComponent implements OnInit {

  employees: Employee[];
  employeeFields = [
    { value: "id", name: "Id" },
    { value: "firstName", name: "FirstName" },
    { value: "lastName", name: "LastName" }
  ];

  companies: Company[];
  company: Company;
  branches: Branch[];
  branch: Branch;
  offices: Office[];
  officeId: string;

  orderBy: string;

  // MatPaginator Inputs
  length = 100;
  pageSize = 8;
  pageIndex = 1;
  pageSizeOptions: number[] = [2, 4, 6, 8, 10];

  // MatPaginator Output
  page: PageEvent;

  constructor(
    private employeeService: EmployeeService,
    private companyService: CompanyService,
    private branchService: BranchService,
    private officeService: OfficeService,
    private dialogService: DialogService,
    private notificationService: NotificationService
  ) { }

  ngOnInit() {
    this.getEmployees();
    this.getCompanies();
    this.getOffices();
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
          this.getOffices();
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

  getEmployees() {
    this.employeeService.getTotalCount(this.company, this.branch, this.officeId).subscribe(size => {
      this.length = size;
      this.employeeService.getEmployees(this.company, this.branch, this.officeId, this.pageIndex, this.pageSize, this.orderBy)
        .subscribe(data => this.employees = data);
    })
  }

  delete(employee: Employee): void {
    this.dialogService.openConfirmDialog()
      .afterClosed().subscribe(res => {
        if (res) {
          this.employeeService.deleteEmployee(employee).subscribe(result => {
            this.notificationService.warn('Deleted successfully!');
            this.employeeService.getTotalCount().subscribe(data => {
              this.length = data;
              this.getEmployees();
            })
          });
        }
      })
  }

  selectCompany() {
    this.getEmployees();
    if (this.company != null) {
      this.companies.splice(0, this.companies.length, this.company);
      if (this.company.branches != null) {
        this.company.branches.length = 0;
      }
    }
    this.getBranches();
    this.getOffices();
  }

  selectBranch() {
    this.getEmployees();
    if (this.branch != null) {
      this.branches.splice(0, this.branches.length, this.branch);
      if (this.branch.offices != null) {
        this.branch.offices.length = 0;
      }
    }
    this.getOffices();
  }

  refreshCompanies() {
    this.company = undefined;
    this.branch = undefined;
    this.officeId = '';
    this.getEmployees();
    this.getCompanies();
    this.getBranches();
  }

  refreshBranches() {
    this.officeId = '';
    this.getBranches();
  }

  refreshEmployees() {
    this.getEmployees();
  }

  clearFilters() {
    this.orderBy = '';
    this.refreshCompanies();
  }

  paginateEmployees(page: PageEvent) {
    this.pageIndex = page.pageIndex + 1;
    this.pageSize = page.pageSize;
    this.getEmployees();
  }

  compareFn(company1: Company, company2: Company): boolean {
    return company1.id === company2.id;
  }



}
