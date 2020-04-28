import { Component } from '@angular/core';
import { NbMenuService } from '@nebular/theme';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  items = [{ title: 'Löschen' }, { title: 'Infos' }];

  constructor(private nbMenuService: NbMenuService) {}

  ngOnInit() {
    this.nbMenuService
      .onItemClick()
      .pipe(map(x => x.item.title))
      .subscribe(x => console.log(x));
  }
}
