import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { isoCategories } from 'src/app/data/iso-categories';
import { IsoCategory } from 'src/app/data/models/iso-category.model';

@Component({
  selector: 'app-category-form',
  templateUrl: './category-form.component.html',
  styleUrls: ['./category-form.component.scss'],
})
export class CategoryFormComponent implements OnInit {
  @Output() categoriesSubmitted = new EventEmitter<IsoCategory[]>();
  categories: IsoCategory[];
  selectedCategories: IsoCategory[];

  ngOnInit(): void {
    this.categories = isoCategories;
    this.selectedCategories = [];
  }

  addParentCategory(parentCategory: IsoCategory) {
    this.selectedCategories.push({ ...parentCategory });
  }

  removeParentCategory(parentCategory: IsoCategory) {
    const index = this.selectedCategories.indexOf(parentCategory);
    this.selectedCategories.splice(index, 1);
  }

  addChildCategory(childCategory: IsoCategory, parentCategory: IsoCategory) {
    const parent = this.selectedCategories.find(x => x.title == parentCategory.title);

    if (!parent) {
      this.selectedCategories.push({
        title: parentCategory.title,
        children: [childCategory],
      });
    } else {
      parent.children.push(childCategory);
    }
  }

  removeChildCategory(childCategory: IsoCategory, parentCategory: IsoCategory) {
    parentCategory = this.selectedCategories.find(x => x.title == parentCategory.title);
    parentCategory.children = parentCategory.children.filter(x => x != childCategory);
  }

  isChecked(title: string) {
    return (
      this.selectedCategories.find(
        x => x.title == title || x.children?.map(x => x.title).includes(title),
      ) != undefined
    );
  }

  onSubmit() {
    this.selectedCategories = this.selectedCategories.sort((a: IsoCategory, b: IsoCategory) =>
      a.title > b.title ? 1 : -1,
    );

    for (const category of this.selectedCategories) {
      if (category.children) {
        category.children = category.children.sort((a: IsoCategory, b: IsoCategory) =>
          a.title > b.title ? 1 : -1,
        );
      }
    }

    this.categoriesSubmitted.emit(this.selectedCategories);
  }
}
