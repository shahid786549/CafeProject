import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { UserService } from '../services/user.service';
import { SnackbarService } from '../services/snackbar.service';
import { MatDialogRef } from '@angular/material/dialog';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { GlobalConstants } from '../shared/global-constants';
import { Router } from '@angular/router';

// Custom Validator to check if password and confirm password match
function passwordsMatchValidator(control: AbstractControl): { [key: string]: boolean } | null {
  const password = control.get('password');
  const confirmPassword = control.get('confirmpassword');

  if (password && confirmPassword && password.value !== confirmPassword.value) {
    return { passwordsNotMatched: true };
  }

  return null;
}

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {
  hide = true;
  signupForm: any=FormGroup;
  responseMessage: any;

  constructor(private formBuilder: FormBuilder,
    private router: Router,
    private userService: UserService,
    private snackbarService: SnackbarService,
    public dialogRef: MatDialogRef<SignupComponent>,
    private ngxService: NgxUiLoaderService) { }

  ngOnInit(): void {
    this.signupForm = this.formBuilder.group({
      name: [null, [Validators.required, Validators.pattern(GlobalConstants.nameRegex)]],
      email: [null, [Validators.required, Validators.pattern(GlobalConstants.emailRegex)]],
      contactNumber: [null, [Validators.required, Validators.pattern(GlobalConstants.contactnumberRegex)]],
      password: [null, [Validators.required]],
      confirmpassword: [null, [Validators.required]]
    }, {
      validator: passwordsMatchValidator // Use the custom validator here
    });
  }

  handleSubmit() {
    this.ngxService.start();
    const formData = this.signupForm.value;
    const data = {
      name: formData.name,
      email: formData.email,
      contactNumber: formData.contactNumber,
      password: formData.password,
      confirmpassword: formData.confirmpassword
    };

    this.userService.signup(data).subscribe(
      (response: any) => {
        this.ngxService.stop();
        this.dialogRef.close();
        this.responseMessage = response?.message;
        this.snackbarService.openSnackBar(this.responseMessage, "success");
        this.router.navigate(['/']);
      },
      (error) => {
        this.ngxService.stop();
        if (error?.error?.message) {
          this.responseMessage = error.error.message;
        } else {
          this.responseMessage = "Something went wrong. Please try again.";
        }
        this.snackbarService.openSnackBar(this.responseMessage, "error");
      }
    );
  }
}
