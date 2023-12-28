import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export class EmailValidator {
  static validateByRegex(regexExp?: RegExp): ValidatorFn {
    return function (control: AbstractControl): ValidationErrors | null {
      let emailMatch = control.value.match(regexExp);

      if (emailMatch) {
        return null;
      }

      return {
        validEmail: true,
      };
    };
  }
}
