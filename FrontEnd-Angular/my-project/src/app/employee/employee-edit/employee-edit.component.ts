import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormBuilder, Validators, FormGroup, FormArray } from '@angular/forms'

import { Employee } from '../employee';
import { EmployeeService } from '../../shared/employee.service';
import { Office } from '../../office/office';
import { OfficeService } from '../../shared/office.service';
import { Branch } from 'src/app/branch/branch';
import { BranchService } from 'src/app/shared/branch.service';
import { EmployeeOffice } from '../employeeOffice';
import { CompanyService } from 'src/app/shared/company.service';
import { Company } from 'src/app/company/company';
import { invalid } from '@angular/compiler/src/render3/view/util';

@Component({
  selector: 'app-employee-edit',
  templateUrl: './employee-edit.component.html',
  styleUrls: ['./employee-edit.component.css']
})
export class EmployeeEditComponent implements OnInit {

  employeeForm: FormGroup;
  id: string;
  existed = true;
  companyIsSelected = false;

  company: Company;
  companies: Company[];
  branches: Branch[];
  offices: Office[];

  constructor(
    private route: ActivatedRoute,
    private employeeService: EmployeeService,
    private branchService: BranchService,
    private officeService: OfficeService,
    private companyService: CompanyService,
    private location: Location,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    if (this.id != null) {
      this.getCompanyOfEmployee(this.id);
      // this.getEmployee();
      this.companyIsSelected = true;
    } else {
      this.getCompanies();
      this.employeeForm = this.buildForm();
      this.existed = false;
    }
  }

  getEmployee(): void {
    this.employeeService.getEmployee(this.id)
      .subscribe(employee => {
        this.employeeForm = this.buildForm(employee);
        if (employee.offices != null && Array.isArray(employee.offices)) {
          employee.offices.forEach(office => {
            (this.employeeForm.get('offices') as FormArray).push(this.addOfficeFormGroup(office));
          });
        }
      });
  }

  buildForm(employee?: Employee): FormGroup {
    const formGroup = this.fb.group({
      id: [employee != null ? employee.id : null],
      firstName: [employee != null ? employee.firstName : null, Validators.required],
      lastName: [employee != null ? employee.lastName : null, Validators.required],
      company: [employee != null && this.company != null ? this.company.name : null, Validators.required],
      offices: this.fb.array([])
    });
    return formGroup;
  }

  addOfficeFormGroup(office?: EmployeeOffice): FormGroup {
    return this.fb.group({
      officeId: [office != null ? office.officeId : null, Validators.required],
      dateFrom: [office != null ? office.dateFrom : null, Validators.required],
      dateTo: [office != null && office.dateTo != null ? office.dateTo : null],
    });
  }

  addOffice(): void {
    (<FormArray>this.employeeForm.get('offices')).push(this.addOfficeFormGroup());
  }

  removeOffice(officeGroupIndex: number): void {
    const officesFormArray = <FormArray>this.employeeForm.get('offices');
    officesFormArray.removeAt(officeGroupIndex);
    if (officesFormArray.length == 0) {
      this.addOffice();
      alert("The employee MUST be assigned to at least ONE office!");
    }
  }

  getCompanyOfEmployee(employeeId: string) {
    this.companyService.getCompanyForEmployee(employeeId)
      .subscribe(company => {
        this.company = company;
        this.getEmployee();
        this.getBranches();
      });
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

  getCompanies() {
    this.companyService.getTotalCount().subscribe(size => {
      this.companyService.getCompanies(1, size, 'name')
        .subscribe(data => this.companies = data);
    });
  }

  onCompanySelection(event: any) {
    this.company = event.value;
    this.getBranches();
    this.companyIsSelected = true;
    if ((this.employeeForm.get('offices') as FormArray).length == 0) {
      this.addOffice();
    }
  }

  goBack(): void {
    this.location.back();
  }

  onSubmit() {
    const employee = {
      id: this.employeeForm.get('id').value,
      firstName: this.employeeForm.get('firstName').value,
      lastName: this.employeeForm.get('lastName').value,
      offices: this.employeeForm.get('offices').value
    }
    if (employee.id != null && employee.id.length > 0) {
      // Update Company
      this.employeeService.updateEmployee(employee)
        .subscribe(() => this.goBack());
    } else {
      // Add Company
      this.employeeService.addEmployee(employee)
        .subscribe(() => this.goBack());
    }
  }

  compareFn(office1: Office, office2: Office): boolean {
    return office1.id === office2.id;
  }

  compareCompany(company1: Company, company2: Company): boolean {
    return company1.id === company2.id;
  }

}
