import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AddRepositoryDialogComponent } from '../../../../shared/components/add-repository-dialog/add-repository-dialog.component';
import { RepositoryService } from '../../../../core/services/repository.service';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Repository } from '../../../../core/domain/repository.module';
import { ConfigurationService } from '../../../../core/services/configuration.service';
import { Observable } from 'rxjs';
import { Configuration } from '../../../../core/domain/configuration.module';
import { TaskService } from '../../../../core/services/task.service';

@Component({
  selector: 'app-default',
  templateUrl: './default.component.html',
  styleUrls: ['./default.component.scss']
})
export class DefaultComponent implements OnInit, OnDestroy {

  @ViewChild(MatSort, { static: true }) sort!: MatSort;

  displayedColumns: string[] = ['cloneUrl', 'actions'];

  dataSource = new MatTableDataSource();

  configuration$: Observable<Configuration> = new Observable<Configuration>();

  constructor(private repositoryService: RepositoryService, private configurationService: ConfigurationService, private taskService: TaskService, private dialog: MatDialog) {

  }

  ngOnDestroy(): void {

  }

  ngOnInit() {
    this.configuration$ = this.configurationService.find();

    this.dataSource = this.repositoryService.tableData();
    this.dataSource.sort = this.sort;
  }

  addRepository() {
    const config = {
      data: {
        repository: {
          cloneUrl: ''
        }
      }
    };

    this.dialog
      .open(AddRepositoryDialogComponent, config)
      .afterClosed()
      .subscribe((repository) => {
        if (repository === undefined) return;

        this.repositoryService.save(repository);
      });
  }

  goTo(repository: Repository) {
    this.editRepository(repository);
  }

  sync() {
    this.taskService.sync();
  }

  editRepository(repository: Repository) {
    const config = {
      data: {
        repository: repository
      }
    };

    this.dialog
      .open(AddRepositoryDialogComponent, config)
      .afterClosed()
      .subscribe((repository) => {
        if (repository === undefined) return;

        this.repositoryService.save(repository);
      });
  }

  deleteRepository(repository: Repository) {
    this.repositoryService.deleteRepository(repository);
  }
}
