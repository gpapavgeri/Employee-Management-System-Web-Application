import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { Company } from '../company';
import { CompanyService } from '../../shared/company.service';

@Component({
  selector: 'app-company-edit',
  templateUrl: './company-edit.component.html',
  styleUrls: ['./company-edit.component.css']
})
export class CompanyEditComponent implements OnInit {

  companyForm: FormGroup;
  id: string;
  existed = true;

  constructor(
    private route: ActivatedRoute,
    private companyService: CompanyService,
    private location: Location,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    if (this.id != null) {
      this.getCompany();
    } else {
      this.companyForm = this.buildForm();
      this.existed = false;
    }
  }

  getCompany(): void {
    this.companyService.getCompany(this.id)
      .subscribe(company => this.companyForm = this.buildForm(company));
  }

  buildForm(company?: Company): FormGroup {
    return this.fb.group({
      id: [company != null ? company.id : null],
      name: [company != null ? company.name : null, Validators.required]
    });
  }

  goBack(): void {
    this.location.back();
  }

  onSubmit() {
    const company = this.companyForm.value;
    if (company.id != null && company.id.length > 0) {
      // UPDATE COMPANY
      this.companyService.updateCompany(company)
        .subscribe(() => this.goBack());
    } else {
      // ADD COMPANY
      this.companyService.addCompany(company)
        .subscribe(() => this.goBack());
    }
  }


}
