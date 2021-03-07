import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { MaterialModule } from './material.module';

import { ActionButtonComponent } from './components/action-button/action-button.component';
import { FileSizePipe } from './pipes/file-size.pipe';
import { ThemeSwitcherComponent } from './components/theme-switcher/theme-switcher.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { ConfigurationDialogComponent } from './components/configuration-dialog/configuration-dialog.component';
import { AddRepositoryDialogComponent } from './components/add-repository-dialog/add-repository-dialog.component';
import { ConfirmDialogComponent } from './components/confirm-dialog/confirm-dialog.component';

@NgModule({
  imports: [CommonModule, FormsModule, MatProgressSpinnerModule, MaterialModule, ReactiveFormsModule, RouterModule],
  declarations: [
    ActionButtonComponent,
    FileSizePipe,
    ThemeSwitcherComponent,
    ConfigurationDialogComponent,
    AddRepositoryDialogComponent,
    ConfirmDialogComponent
  ],
  exports: [
    ActionButtonComponent,
    CommonModule,
    FileSizePipe,
    FormsModule,
    HttpClientModule,
    MaterialModule,
    ReactiveFormsModule,
    RouterModule,
    ThemeSwitcherComponent
  ],
  entryComponents: [ConfigurationDialogComponent, AddRepositoryDialogComponent, ConfirmDialogComponent],
  providers: []
})
export class SharedModule {}
