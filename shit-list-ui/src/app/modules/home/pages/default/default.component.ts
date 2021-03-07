import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AddRepositoryDialogComponent } from '../../../../shared/components/add-repository-dialog/add-repository-dialog.component';
import { RepositoryService } from '../../../../core/services/repository.service';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Repository } from '../../../../core/domain/repository.module';
import { ConfigurationService } from '../../../../core/services/configuration.service';
import { TaskService } from '../../../../core/services/task.service';
import { WebsocketService } from '../../../../core/services/websocket.service';
import { IMessage } from '@stomp/stompjs';
import { Total } from '../../../../core/domain/total.module';

@Component({
  selector: 'app-default',
  templateUrl: './default.component.html',
  styleUrls: ['./default.component.scss']
})
export class DefaultComponent implements OnInit, OnDestroy {
  total: Total = {
    totalTests: 0,
    totalIgnored: 0
  };

  @ViewChild(MatSort, { static: true }) sort!: MatSort;

  displayedColumns: string[] = ['cloneUrl', 'state', 'actions'];

  dataSource = new MatTableDataSource();

  constructor(
    private websockets: WebsocketService,
    private repositoryService: RepositoryService,
    private configurationService: ConfigurationService,
    private taskService: TaskService,
    private dialog: MatDialog
  ) {
    websockets.listen('repository').subscribe((message) => {
      this.updateTableRow(message);
    });

    websockets.listen('total').subscribe((message) => {
      const total: Total = JSON.parse(message.body);

      this.total.totalTests = total.totalTests;
      this.total.totalIgnored = total.totalIgnored;
    });
  }

  updateTableRow(message: IMessage) {
    const repository: Repository = JSON.parse(message.body);

    const index = this.dataSource.data.findIndex((value: any) => value.id === repository.id);
    const data = this.dataSource.data;
    data[index] = repository;

    this.dataSource.data = data;
  }

  ngOnDestroy(): void {}

  ngOnInit() {
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
