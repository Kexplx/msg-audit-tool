import { Component, OnInit, Output, EventEmitter, OnDestroy } from '@angular/core';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { ContactPersonStore } from 'src/app/core/data/stores/contact-person.store';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SubSink } from 'subsink';

@Component({
  selector: 'app-contact-person-search',
  templateUrl: './contact-person-search.component.html',
  styleUrls: ['./contact-person-search.component.scss'],
})
export class ContactPersonSearchComponent implements OnInit, OnDestroy {
  @Output() contactPersonSelected = new EventEmitter<ContactPerson>();

  filteredContactPersons$: Observable<ContactPerson[]>;
  private contactPersons: ContactPerson[];
  private readonly subSink = new SubSink();

  constructor(private contactPersonStore: ContactPersonStore) {}

  ngOnInit(): void {
    const contactPersonsSub = this.contactPersonStore.contactPersons$
      .pipe(filter(contactPersons => contactPersons != null))
      .subscribe(contactPersons => {
        this.contactPersons = contactPersons;

        this.filteredContactPersons$ = of(contactPersons); // no filter applied initially.
      });

    this.subSink.add(contactPersonsSub);
  }

  ngOnDestroy() {
    this.subSink.unsubscribe();
  }

  onContactPersonSelect(contactPerson: ContactPerson) {
    this.contactPersonSelected.emit(contactPerson);
  }

  // Assigns filtered #contactPersons to #filteredContactPersons
  onInput(text: string) {
    this.filteredContactPersons$ = of(this.contactPersons).pipe(
      map(
        contactPersons =>
          contactPersons.filter(cp => cp.forename.toLowerCase().startsWith(text.toLowerCase())), // Filter by forename.
      ),
    );
  }
}
