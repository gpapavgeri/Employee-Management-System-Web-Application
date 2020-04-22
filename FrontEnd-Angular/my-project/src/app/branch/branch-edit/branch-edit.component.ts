import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { Branch } from '../branch';
import { BranchService } from '../../shared/branch.service';
import { Company } from '../../company/company';
import { CompanyService } from '../../shared/company.service';

@Component({
  selector: 'app-branch-edit',
  templateUrl: './branch-edit.component.html',
  styleUrls: ['./branch-edit.component.css']
})
export class BranchEditComponent implements OnInit {

  branchForm: FormGroup;
  id: string;
  existed = false;

  companies: Company[];  
  company: Company;

  constructor(
    private route: ActivatedRoute,
    private branchService: BranchService,
    private location: Location,
    private fb: FormBuilder,
    private companyService: CompanyService
  ) { }

  ngOnInit(): void {
    this.getCompanies();
    this.id = this.route.snapshot.paramMap.get('id');
    if (this.id != null) {
      this.existed = true;
      this.getBranch();
    } else {
      this.branchForm = this.buildForm();
    }

  }

  getCompanies() {
    this.companyService.getTotalCount().subscribe(size => {
      this.companyService.getCompanies(1, size, 'name')
        .subscribe(data => this.companies = data);
    });
  }

  buildForm(branch?: Branch): FormGroup {
    return this.fb.group({
      id: [branch != null ? branch.id : null],
      name: [branch != null ? branch.name : null, Validators.required],
      company: [branch != null ? branch.company : null, Validators.required]
    });
  }

  getBranch(): void {
    this.branchService.getBranch(this.id)
      .subscribe(branch => {
        this.branchForm = this.buildForm(branch);
        this.company = branch.company;
      });
  }

  goBack(): void {
    this.location.back();
  }

  onSubmit() {
    const branch = this.branchForm.value;
    if (branch.id != null && branch.id.length > 0) {
      // UPDATE COMPANY
      this.branchService.updateBranch(branch)
        .subscribe(() => this.goBack());
    } else {
      // ADD COMPANY
      this.branchService.addBranch(branch)
        .subscribe(() => this.goBack());
    }
  }

}
