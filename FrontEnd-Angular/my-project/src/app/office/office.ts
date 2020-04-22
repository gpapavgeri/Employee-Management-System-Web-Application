import { Branch } from '../branch/branch';
import { OfficeEmployee } from './officeEmpoyee';
import { OfficeAsset } from './officeAsset';

export interface Office {
    id: string;
    code: number;
    branch: Branch;
    assets?: OfficeAsset[];
    employees?: OfficeEmployee[];
}