import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Employee } from '../employee/employee';
import { Company } from '../company/company';
import { Branch } from '../branch/branch';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private employeesUrl = 'http://localhost:8080/employees';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  }

  constructor(
    private http: HttpClient
  ) { }

  getEmployees(company?: Company, branch?: Branch, officeId?: string, index?: number, size?: number, orderBy?: string): Observable<Employee[]> {
    let parameters: HttpParams = new HttpParams();
    if (company != null) { parameters = parameters.set('companyId', company.id) };
    if (branch != null) { parameters = parameters.set('branchId', branch.id) };
    if (officeId != null) { parameters = parameters.set('officeId', officeId) };
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

    return this.http.get<Employee[]>(`${this.employeesUrl}`, {
      params: parameters
    });
  }

  getTotalCount(company?: Company, branch?: Branch, officeId?: string): Observable<number> {
    const url = `${this.employeesUrl}` + '/count';
    let parameters: HttpParams = new HttpParams();
    if (company != null) { parameters = parameters.set('companyId', company.id) };
    if (branch != null) { parameters = parameters.set('branchId', branch.id) };
    if (officeId != null) { parameters = parameters.set('officeId', officeId) };
    return this.http.get<number>(url, {
      params: parameters
    });
  }

  getEmployee(id: string): Observable<Employee> {
    const url = `${this.employeesUrl}/${id}`;
    return this.http.get<Employee>(url);
  }

  deleteEmployee(employee: Employee | string): Observable<any> {
    const id = typeof employee === 'string' ? employee : employee.id;
    const url = `${this.employeesUrl}/${id}`;

    return this.http.delete<any>(url, this.httpOptions);
  }

  updateEmployee(employee: Employee): Observable<any> {
    return this.http.put(this.employeesUrl, employee, this.httpOptions).pipe(
      catchError(this.handleError));
  }

  addEmployee(employee: Employee): Observable<Employee> {
    return this.http.post<Employee>(this.employeesUrl, employee, this.httpOptions);
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
      alert(`Error message:\n${error.error.message}`));
  };


}


