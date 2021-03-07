import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Configuration } from '../../../core/domain/configuration.module';

export interface ConfigurationDialogData {
  configuration: Configuration;
}

@Component({
  selector: 'app-configuration-dialog',
  templateUrl: './configuration-dialog.component.html',
  styleUrls: ['./configuration-dialog.component.scss']
})
export class ConfigurationDialogComponent {
  configuration: Configuration;

  constructor(private dialogRef: MatDialogRef<any>, @Inject(MAT_DIALOG_DATA) private data: ConfigurationDialogData) {
    this.configuration = data.configuration;
  }

  onCancel() {
    this.dialogRef.close();
  }
}
