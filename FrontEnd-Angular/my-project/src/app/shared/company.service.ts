import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpErrorResponse } from '@angular/common/http'
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

import { Company } from '../company/company'
import { Employee } from '../employee/employee';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  private companiesUrl = 'http://localhost:8080/companies';
  
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  }

  options = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    responseType: 'text' as const
  }

  constructor(
    private http: HttpClient
  ) { }

  getCompanies(index?: number, size?: number, orderBy?: string): Observable<Company[]> {
    let parameters: HttpParams = new HttpParams();
    if (index != null) {
      let pindex: string;
      pindex = index.toString();
      parameters = parameters.set('pageNo', pindex);
    }
    if (size != null) {
      let psize: string;
      psize = size.toString();
      parameters = parameters.set('pageSize', psize);
    }
    if (orderBy != null) { parameters = parameters.set('sortBy', orderBy); }

    return this.http.get<Company[]>(`${this.companiesUrl}`, {
      params: parameters
    });
  }

  getCompanyForOffice(officeId: string): Observable<Company> {
    const url = `${this.companiesUrl}` + '/office';
    return this.http.get<Company>(url, {
      params: new HttpParams().set('officeId', officeId)
    });
  }

  getCompanyForEmployee(employeeId: string): Observable<Company> {
    const url = `${this.companiesUrl}` + '/employee';
    return this.http.get<Company>(url, {
      params: new HttpParams().set('employeeId', employeeId)
    });
  }

  getTotalCount(): Observable<number> {
    const url = `${this.companiesUrl}` + '/count';
    return this.http.get<number>(url);
  }

  getCompany(id: string): Observable<Company> {
    const url = `${this.companiesUrl}/${id}`;
    return this.http.get<Company>(url).pipe(
      catchError(this.handleError));
  }

  deleteCompany(company: Company | string): Observable<any> {
    const id = typeof company === 'string' ? company : company.id;
    const url = `${this.companiesUrl}/${id}`;

    return this.http.delete(url, this.options);
  }

  updateCompany(company: Company): Observable<any> {
    return this.http.put(this.companiesUrl, company, this.httpOptions);
  }

  addCompany(company: Company): Observable<Company> {
    return this.http.post<Company>(this.companiesUrl, company, this.httpOptions);
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error.message}`);
    }
    return throwError(
      'Something bad happened; please try again later.');
  };

}


