export interface Repository {
  id: number;
  cloneUrl: string;
  totalTests: number;
  totalIgnored: number;
  state: string;
}
