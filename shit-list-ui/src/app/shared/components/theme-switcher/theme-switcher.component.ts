import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-theme-switcher',
  templateUrl: './theme-switcher.component.html',
  styleUrls: ['./theme-switcher.component.scss'],
})
export class ThemeSwitcherComponent {
  @Input()
  options: any[] | null = [];

  @Output() themeChange: EventEmitter<string> = new EventEmitter<string>();

  constructor() {}

  changeTheme(themeToSet: string | undefined) {
    this.themeChange.emit(themeToSet);
  }
}
