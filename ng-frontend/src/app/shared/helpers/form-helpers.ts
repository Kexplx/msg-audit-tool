import { FormGroup } from '@angular/forms';

/**
 * Validator for two dates: A start date has to be before the end date.
 *
 * @param startDate string of form group attribute for start date
 * @param endDate string of form group attribute for end date
 */
export function dateRangeValidator(startDate: string, endDate: string) {
  return (group: FormGroup): { [key: string]: any } => {
    const start = group.get(startDate).value;
    const end = group.get(endDate).value;
    if (!start || !end) {
      return null;
    }
    if (start > end) {
      return {
        dateRangeValidator: true,
      };
    }
  };
}
