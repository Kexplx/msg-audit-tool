import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-add-audit',
  templateUrl: './add-audit.component.html',
  styleUrls: ['./add-audit.component.scss'],
})
export class AddAuditComponent implements OnInit {
  isMobileScreen = false;
  mediaMatcher = matchMedia('(max-width: 600px)');

  ngOnInit(): void {
    this.isMobileScreen = this.mediaMatcher.matches;
    this.mediaMatcher.addEventListener('change', () => {
      this.isMobileScreen = this.mediaMatcher.matches;
    });
  }
}
