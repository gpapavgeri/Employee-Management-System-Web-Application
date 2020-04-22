import { AssetType } from '../assetType/assetType';
import { Company } from '../company/company';
import { AssetOffice } from './assetOffice';

export interface Asset {
    id: string;
    serialNumber: string;
    brand: string;
    company: Company;
    assetType: AssetType;
    offices: AssetOffice[];
}