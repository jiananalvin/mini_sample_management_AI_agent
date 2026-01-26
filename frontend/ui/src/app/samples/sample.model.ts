export type SampleStatus = 'REGISTERED' | 'RECEIVED' | 'IN_ANALYSIS' | 'COMPLETED' | 'REJECTED';

export interface Sample {
  id: number;
  sampleCode: string;
  type: string;
  status: SampleStatus;
  collectedAt: string; // ISO
  comment?: string | null;
}

export interface SampleCreateRequest {
  sampleCode: string;
  type: string;
  collectedAt: string;
  comment?: string | null;
}

export interface SampleUpdateRequest {
  type: string;
  status: SampleStatus;
  collectedAt: string;
  comment?: string | null;
}
