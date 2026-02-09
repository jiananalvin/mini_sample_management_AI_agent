import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Sample, SampleCreateRequest, SampleUpdateRequest, SampleStatus } from './sample.model';
import { environment } from '../../environments/environment';

interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}

@Injectable({ providedIn: 'root' })
export class SampleService {
  private baseUrl = `${environment.apiUrl}/samples`;

  constructor(private http: HttpClient) {}

  list(type?: string, status?: SampleStatus, page = 0, size = 20): Observable<Page<Sample>> {
    let params = new HttpParams().set('page', page).set('size', size);
    if (type) params = params.set('type', type);
    if (status) params = params.set('status', status);
    return this.http.get<Page<Sample>>(this.baseUrl, { params });
  }

  create(req: SampleCreateRequest): Observable<Sample> {
    return this.http.post<Sample>(this.baseUrl, req);
  }

  get(id: number): Observable<Sample> {
    return this.http.get<Sample>(`${this.baseUrl}/${id}`);
  }

  update(id: number, req: SampleUpdateRequest): Observable<Sample> {
    return this.http.put<Sample>(`${this.baseUrl}/${id}`, req);
  }

  createFromNaturalLanguage(text: string): Observable<Sample> {
    return this.http.post<Sample>(`${this.baseUrl}/create-from-natural-language`, { text });
  }
}
