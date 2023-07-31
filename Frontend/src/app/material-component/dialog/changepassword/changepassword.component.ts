import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { UserService } from 'src/app/services/user.service';
import { GlobalConstants } from 'src/app/shared/global-constants';

@Component({
  selector: 'app-changepassword',
  templateUrl: './changepassword.component.html',
  styleUrls: ['./changepassword.component.scss']
})
export class ChangepasswordComponent implements OnInit {

  oldPassword=true;
  newPassword=true;
  confirmPassword=true;
  changePasswordForm:any=FormGroup;
  responseMessage:any;

  constructor(
    private formBuilder:FormBuilder,
    private userService:UserService,
    public dialogRef:MatDialogRef<ChangepasswordComponent>,
    private ngxService:NgxUiLoaderService,
    private snackbarService:SnackbarService
  ) { }

  ngOnInit(): void {
    this.changePasswordForm = this.formBuilder.group({
      oldPassword: [null, Validators.required],
      newPassword: [null, [Validators.required, Validators.minLength(6)]],
      confirmPassword: [null, [Validators.required, Validators.minLength(6)]]
    });
  }
validateSubmit(){
  if(this.changePasswordForm.controls['newPassword'].value != this.changePasswordForm.controls['confirmPassword'].value){
    return true;
  }else{
    return false;
  }
}
handlepasswordChangeSubmit() {
  
    this.ngxService.start();
    var formData = this.changePasswordForm.value;
    var data = {
      oldPassword: formData.oldPassword,
      newPassword: formData.newPassword,
      confirmPassword:formData.confirmPassword
    };
    this.userService.changePassword(data).subscribe(
      (response: any) => {
        this.ngxService.stop();
        this.responseMessage=response?.message;
        this.dialogRef.close();
        this.snackbarService.openSnackBar(response.message, 'success');
      },
      (error) => {
        console.log(error);
        this.ngxService.stop();
        if (error.error?.message) {
          this.responseMessage = error.error?.message;
        } else {
          this.responseMessage = GlobalConstants.genaricError;
        }
        this.snackbarService.openSnackBar(this.responseMessage, 'error');
      }
    );
    }
    togglePasswordVisibility(field: string) {
      switch (field) {
        case 'oldPassword':
          this.oldPassword = !this.oldPassword;
          break;
        case 'newPassword':
          this.newPassword = !this.newPassword;
          break;
        case 'confirmPassword':
          this.confirmPassword = !this.confirmPassword;
          break;
      }
    }
  }
  








