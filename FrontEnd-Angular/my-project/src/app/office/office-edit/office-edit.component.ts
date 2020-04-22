import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormBuilder, Validators, FormGroup, FormArray } from '@angular/forms';

import { Office } from '../../office/office';
import { OfficeService } from '../../shared/Office.service';
import { Branch } from '../../branch/branch';
import { BranchService } from '../../shared/branch.service';
import { Company } from 'src/app/company/company';
import { CompanyService } from 'src/app/shared/company.service';
import { OfficeEmployee } from '../officeEmpoyee';
import { EmployeeService } from 'src/app/shared/employee.service';
import { Employee } from 'src/app/employee/employee';
import { Asset } from 'src/app/asset/asset';
import { OfficeAsset } from '../officeAsset';
import { AssetService } from 'src/app/shared/asset.service';


@Component({
  selector: 'app-office-edit',
  templateUrl: './office-edit.component.html',
  styleUrls: ['./office-edit.component.css']
})
export class OfficeEditComponent implements OnInit {

  company: Company;
  companies: Company[];
  branch: Branch;
  branches: Branch[];
  employees: Employee[];
  assets: Asset[];

  officeForm: FormGroup;
  officeEmployees: OfficeEmployee[];
  id: string;

  existed = true;
  companyIsSelected = false;
  employeesSelected = false;
  assetsSelected = false;

  constructor(
    private route: ActivatedRoute,
    private companyService: CompanyService,
    private officeService: OfficeService,
    private employeeService: EmployeeService,
    private assetService: AssetService,
    private location: Location,
    private fb: FormBuilder,
    private branchService: BranchService
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    if (this.id != null) {
      this.getOffice();
      this.getCompanyForOffice(this.id);
      this.companyIsSelected = true;
      this.employeesSelected = true;
      this.assetsSelected = true;
    } else {
      this.getCompanies();
      this.officeForm = this.buildForm();
      this.existed = false;
    }
  }

  getCompanies() {
    this.companyService.getTotalCount().subscribe(size => {
      this.companyService.getCompanies(1, size, 'name')
        .subscribe(data => this.companies = data);
    });
  }

  getBranchesForCompany() {
    if (this.company != null) {
      this.branchService.getTotalCount().subscribe(size => {
        this.branchService.getBranches(this.company, 1, size, 'name')
          .subscribe(data => this.branches = data);
      })
    }
  }

  buildForm(office?: Office): FormGroup {
    const formGroup = this.fb.group({
      id: [office != null ? office.id : null],
      code: [office != null ? office.code : null, Validators.required],
      branch: [office != null ? office.branch : null, Validators.required],
      employees: this.fb.array([]),
      assets: this.fb.array([])
    });
    return formGroup;
  }

  addEmployeeFormGroup(employee?: OfficeEmployee): FormGroup {
    return this.fb.group({
      employeeId: [employee != null ? employee.employeeId : null, Validators.required],
      dateFrom: [employee != null ? employee.dateFrom : null, Validators.required],
      dateTo: [employee != null && employee.dateTo != null ? employee.dateTo : null],
    });
  }

  addAssetFormGroup(asset?: OfficeAsset): FormGroup {
    return this.fb.group({
      assetId: [asset != null ? asset.assetId : null, Validators.required],
      dateFrom: [asset != null ? asset.dateFrom : null],
      dateTo: [asset != null && asset.dateTo != null ? asset.dateTo : null],
    });
  }

  getOffice(): void {
    this.officeService.getOfficeWithLists(this.id)
      .subscribe(office => {
        this.branch = office.branch;
        this.officeForm = this.buildForm(office);
        if (office.employees != null && Array.isArray(office.employees)) {
          office.employees.forEach(employee => {
            (this.officeForm.get('employees') as FormArray).push(this.addEmployeeFormGroup(employee));
          })
        } else {
          this.employeesSelected = false;
        }

        if (office.assets != null && Array.isArray(office.assets)) {
          office.assets.forEach(asset => {
            (this.officeForm.get('assets') as FormArray).push(this.addAssetFormGroup(asset));
          })
        } else {
          this.assetsSelected = false;
        }
      });
  }

  getEmployees() {
    this.employeeService.getTotalCount(this.company, null, null).subscribe(size => {
      this.employeeService.getEmployees(this.company, null, null, 1, size, 'firstName')
        .subscribe(data => this.employees = data);
    })
  }

  getAssets() {
    this.assetService.getTotalCount(this.company, null, null).subscribe(size => {
      this.assetService.getAssets(this.company, null, null, 1, size, 'serialNumber')
        .subscribe(data => this.assets = data);
    })
  }

  addEmployee(): void {
    (<FormArray>this.officeForm.get('employees')).push(this.addEmployeeFormGroup());
  }

  addAsset(): void {
    (<FormArray>this.officeForm.get('assets')).push(this.addAssetFormGroup());
  }

  removeEmployee(officeGroupIndex: number): void {
    const employeesFormArray = <FormArray>this.officeForm.get('employees');
    employeesFormArray.removeAt(officeGroupIndex);
    if (employeesFormArray.length == 0) {
      this.employeesSelected = false;
    }
  }

  removeAsset(officeGroupIndex: number): void {
    const assetsFormArray = <FormArray>this.officeForm.get('assets');
    assetsFormArray.removeAt(officeGroupIndex);
    if (assetsFormArray.length == 0) {
      this.assetsSelected = false;
    }
  }

  getCompanyForOffice(officeId: string) {
    this.companyService.getCompanyForOffice(officeId).subscribe(company => {
      this.company = company;
      this.getBranchesForCompany();
      this.getEmployees();
      this.getAssets();
    });
  }

  onCompanySelection(event: any) {
    this.company = event.value;
    this.companyIsSelected = true;
    this.getBranchesForCompany();
    this.getEmployees();
    this.getAssets();
  }

  openEmployees() {
    this.employeesSelected = true;
    this.addEmployee();
    this.getEmployees();
  }

  openAssets() {
    this.assetsSelected = true;
    this.addAsset();
    this.getAssets();
  }

  goBack(): void {
    this.location.back();
  }

  onSubmit() {
    const office = this.officeForm.value;
    console.log(office);
    if (office.id != null && office.id.length > 0) {
      this.officeService.updateOffice(office)
        .subscribe(() => this.goBack());
    } else {
      this.officeService.addOffice(office)
        .subscribe(() => this.goBack());
    }
  }

  compareFn(employee1: Employee, employee2: Employee): boolean {
    return employee1.id === employee2.id;
  }

}
