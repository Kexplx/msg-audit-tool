import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-not-found',
  templateUrl: './not-found.component.html',
  styleUrls: ['./not-found.component.scss'],
})
export class NotFoundComponent implements OnInit {
  url: string;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.url = this.route.snapshot.url
      .map(x => x.path)
      .toString()
      .replace(/,/g, '/');
  }
}
