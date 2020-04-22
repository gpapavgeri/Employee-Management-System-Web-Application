import { Branch } from '../branch/branch';
import { Asset } from '../asset/asset';

export interface Company {
    id: string;
    name: string;
    branches?: Branch[];
    assets?: Asset[];
}