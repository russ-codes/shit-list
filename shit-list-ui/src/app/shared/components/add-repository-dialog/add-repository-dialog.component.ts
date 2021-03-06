import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {Repository} from "../../../core/domain/repository.module";

export interface RepositoryDialogData {
  repository: Repository;
}

@Component({
  selector: 'app-repository-dialog',
  templateUrl: './add-repository-dialog.component.html',
  styleUrls: ['./add-repository-dialog.component.scss'],
})
export class AddRepositoryDialogComponent {
  repository: Repository;

  constructor(private dialogRef: MatDialogRef<any>, @Inject(MAT_DIALOG_DATA) private data: RepositoryDialogData) {
    this.repository = data.repository;
  }

  onCancel() {
    this.dialogRef.close();
  }
}
