import { EmployeeOffice } from './employeeOffice';

export interface Employee {
    id: string;
    firstName: string;
    lastName: string;
    offices?: EmployeeOffice[];
}