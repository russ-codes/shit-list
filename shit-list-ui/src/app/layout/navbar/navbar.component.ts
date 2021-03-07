import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ThemeService } from '../../core/services/theme.service';
import { MatDialog } from '@angular/material/dialog';
import {
  ConfigurationDialogComponent,
  ConfigurationDialogData
} from '../../shared/components/configuration-dialog/configuration-dialog.component';
import { ConfigurationService } from '../../core/services/configuration.service';
import { Option } from '../../core/domain/option.module';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  options$: Observable<Array<Option>> = new Observable<Array<Option>>();

  constructor(private readonly themeService: ThemeService, private dialog: MatDialog, private configurationService: ConfigurationService) {}

  ngOnInit() {
    this.options$ = this.themeService.getThemeOptions();

    const theme = this.themeService.getTheme();

    if (theme === undefined) {
      this.themeService.setTheme('deeppurple-amber');
    } else {
      this.themeService.setTheme(theme);
    }
  }

  themeChangeHandler(themeToSet: string) {
    this.themeService.setTheme(themeToSet);
  }

  goToGitHub() {
    window.open('https://github.com/russ-codes/shit-list', '_blank');
  }

  openConfigDialog() {
    this.configurationService.find().subscribe((configuration) => {
      if (configuration === null) {
        configuration = {
          username: '',
          accessToken: ''
        };
      }

      const data: ConfigurationDialogData = { configuration };

      console.log(configuration);

      this.dialog
        .open(ConfigurationDialogComponent, { data: data })
        .afterClosed()
        .subscribe((configuration) => {
          if (configuration === undefined) return;

          this.configurationService.save(configuration);
        });
    });
  }
}
