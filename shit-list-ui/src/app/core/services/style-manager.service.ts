import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class StyleManagerService {
  constructor() {}

  setStyle(key: string, href: string) {
    this.getLinkElementForKey(key).setAttribute('href', href);
  }

  getLinkElementForKey(key: string) {
    return this.getExistingLinkElementByKey(key) || this.createLinkElementWithKey(key);
  }

  getExistingLinkElementByKey(key: string) {
    return document.head.querySelector(`link[rel="stylesheet"].${this.getClassNameForKey(key)}`);
  }

  createLinkElementWithKey(key: string) {
    const linkElement = document.createElement('link');

    linkElement.setAttribute('rel', 'stylesheet');
    linkElement.classList.add(this.getClassNameForKey(key));
    document.head.appendChild(linkElement);

    return linkElement;
  }

  getClassNameForKey(key: string) {
    return `app-${key}`;
  }
}
