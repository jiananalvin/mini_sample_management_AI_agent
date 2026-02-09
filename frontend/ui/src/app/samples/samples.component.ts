import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators, FormGroup } from '@angular/forms';

import { Sample, SampleStatus } from './sample.model';
import { SampleService } from './sample.service';

@Component({
  selector: 'app-samples',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './samples.component.html',
})
export class SamplesComponent implements OnInit {
  samples: Sample[] = [];
  loading = false;
  error: string | null = null;

  statuses: SampleStatus[] = ['REGISTERED', 'RECEIVED', 'IN_ANALYSIS', 'COMPLETED', 'REJECTED'];

  filterForm!: FormGroup;
  createForm!: FormGroup;
  editForm!: FormGroup;
  naturalLanguageForm!: FormGroup;

  editId: number | null = null;
  processingNL = false;

  constructor(private fb: FormBuilder, private api: SampleService) {}

  ngOnInit(): void {
    this.filterForm = this.fb.group({
      type: [''],
      status: ['' as any],
    });

    this.createForm = this.fb.group({
      sampleCode: ['', [Validators.required, Validators.maxLength(64)]],
      type: ['', [Validators.required, Validators.maxLength(64)]],
      collectedAt: ['', [Validators.required]],
      comment: [''],
    });

    this.editForm = this.fb.group({
      type: ['', [Validators.required, Validators.maxLength(64)]],
      status: ['REGISTERED' as SampleStatus, [Validators.required]],
      collectedAt: ['', [Validators.required]],
      comment: [''],
    });

    this.naturalLanguageForm = this.fb.group({
      text: ['', [Validators.required]],
    });

    this.refresh();
  }

  refresh(): void {
    this.loading = true;
    this.error = null;

    const type = (this.filterForm.get('type')?.value as string) || undefined;
    const status = (this.filterForm.get('status')?.value as SampleStatus) || undefined;

    this.api.list(type, status).subscribe({
      next: (page) => {
        this.samples = page.content;
        this.loading = false;
      },
      error: (err) => {
        this.error = err?.error?.message || 'Failed to load samples';
        this.loading = false;
      },
    });
  }

  create(): void {
    if (this.createForm.invalid) return;

    this.api.create(this.createForm.value as any).subscribe({
      next: () => {
        this.createForm.reset();
        this.refresh();
      },
      error: (err) => {
        this.error = err?.error?.message || 'Create failed';
      },
    });
  }

  startEdit(s: Sample): void {
    this.editId = s.id;

    this.editForm.setValue({
      type: s.type,
      status: s.status,
      collectedAt: s.collectedAt,
      comment: s.comment ?? '',
    });
  }

  cancelEdit(): void {
    this.editId = null;
  }

  saveEdit(): void {
    if (this.editId == null || this.editForm.invalid) return;

    this.api.update(this.editId, this.editForm.value as any).subscribe({
      next: () => {
        this.editId = null;
        this.refresh();
      },
      error: (err) => {
        this.error = err?.error?.message || 'Update failed';
      },
    });
  }

  createFromNaturalLanguage(): void {
    if (this.naturalLanguageForm.invalid) return;

    this.processingNL = true;
    this.error = null;

    const text = this.naturalLanguageForm.get('text')?.value;

    this.api.createFromNaturalLanguage(text).subscribe({
      next: () => {
        this.naturalLanguageForm.reset();
        this.processingNL = false;
        this.refresh();
      },
      error: (err) => {
        // Try to extract error message from different possible response formats
        let errorMessage = 'Failed to create sample from natural language';
        
        if (err?.error) {
          if (err.error.message) {
            errorMessage = err.error.message;
          } else if (typeof err.error === 'string') {
            errorMessage = err.error;
          } else if (err.error.error) {
            errorMessage = err.error.error;
          }
        } else if (err?.message) {
          errorMessage = err.message;
        }
        
        // Log full error for debugging
        console.error('Natural language creation error:', err);
        
        this.error = errorMessage;
        this.processingNL = false;
      },
    });
  }
}
