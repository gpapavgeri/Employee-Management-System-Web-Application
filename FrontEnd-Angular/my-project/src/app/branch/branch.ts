import { Office } from '../office/office';
import { Company } from '../company/company';

export interface Branch {
    id: string;
    name: string;
    company: Company;
    offices?: Office[]
}