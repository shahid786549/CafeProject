import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { saveAs } from 'file-saver';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { BillService } from 'src/app/services/bill.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { ViewBillProductsComponent } from '../dialog/view-bill-products/view-bill-products.component';
import { ConfirmationComponent } from '../dialog/confirmation/confirmation.component';

@Component({
  selector: 'app-view-bill',
  templateUrl: './view-bill.component.html',
  styleUrls: ['./view-bill.component.scss']
})
export class ViewBillComponent implements OnInit {
  displayedColumns: string[]= ['name','email', 'contactNumber', 'paymentMethod', 'total','view']

  dataSource:any; // length1:any;
  
  responseMessage:any;
  constructor(private ngxService:NgxUiLoaderService,
    private billService:BillService,
    private dialog:MatDialog,
    private snackbarService: SnackbarService,
    private router:Router) { }

  ngOnInit(): void {
    this.ngxService.start();
    this.tableData();
  }
  tableData(){
    this.billService.getBills().subscribe((response:any)=>{ 
      this.ngxService.stop();
    this.dataSource = new MatTableDataSource(response);
    }, (error:any)=>{
    
    this.ngxService.stop();
    console.log(error);
    if(error.error?.message){
    this.responseMessage = error.error?.message;
    }
    else{
    this.responseMessage.GlobalConstants.genaricError;
    }
    this.snackbarService.openSnackBar(this.responseMessage,"error");
    })
    }
    applyFilter(event:Event){
      const filterValue=(event.target as HTMLInputElement).value;
      this.dataSource.filter =filterValue.trim().toLowerCase();
    }
    handleViewAction(values:any){
      const dialogConfig=new MatDialogConfig();
      dialogConfig.data={
        data:values
      }
      dialogConfig.width="100%";
      const dialogRef=this.dialog.open(ViewBillProductsComponent,dialogConfig);
      this.router.events.subscribe(()=>{
        dialogRef.close();
      })
    }
    
    handleDeleteAction(values: any) {
      const dialogConfig = new MatDialogConfig();
  
      dialogConfig.data = {
        message: 'delete ' + values.name + ' bill',
        confirmation: true
      };
  
      const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);
      const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((response) => {
        
          this.ngxService.start();
          this.deleteBill(values.id);
           dialogRef.close();
      });
    }
    deleteBill(id: any) {
      this.billService.delete(id).subscribe(
        (response: any) => {
          // Handle success, update dataSource if needed
          this.ngxService.stop();
          this.tableData(); // Refresh data after successful deletion
          this.responseMessage=response?.message;
          this.snackbarService.openSnackBar(this.responseMessage,'success');
        },
        (error: any) => {
          this.ngxService.stop();
    console.log(error);
    if(error.error?.message){
    this.responseMessage = error.error?.message;
    }
    else{
    this.responseMessage.GlobalConstants.genaricError;
    }
    this.snackbarService.openSnackBar(this.responseMessage,"error");
    });
    }
  
    downloadReportAction(values: any) {
      this.ngxService.start();
    
      var data = {
        name: values.name,
        email: values.email,
        uuid: values.uuid,
        contactNumber: values.contactNumber,
        paymentMethod: values.paymentMethod,
        totalAmount: values.total.toString(),
        productDetails: values.productDetail
      };
    
      this.downloadFile(values.uuid, data);
    }
    
    downloadFile(fileName: string, data: any) {
      this.billService.getPdf(data).subscribe(
        (response: any) => {
          this.saveAs(response, fileName + ".pdf");
          this.ngxService.stop();
        },
        (error: any) => {
          this.ngxService.stop();
          console.log(error);
          // Handle any error if needed
        }
      );
    }
    
    saveAs(blob: any, fileName: string) {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = fileName;
      a.click();
      window.URL.revokeObjectURL(url);
    }
    
}
