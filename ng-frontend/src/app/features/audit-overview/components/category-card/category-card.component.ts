import { Component, OnInit, Input } from '@angular/core';
import { Category } from 'src/app/core/data/models/category.model';

@Component({
  selector: 'app-category-card',
  templateUrl: './category-card.component.html',
  styleUrls: ['./category-card.component.scss'],
})
export class CategoryCardComponent implements OnInit {
  @Input() category: Category;
  constructor() {}

  ngOnInit(): void {}
}
