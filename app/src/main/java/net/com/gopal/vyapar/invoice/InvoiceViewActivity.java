package net.com.gopal.vyapar.invoice;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;


import net.com.gopal.vyapar.R;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class InvoiceViewActivity extends AppCompatActivity {
    // variables for our buttons.
    Button generatePDFbtn;

    // declaring width and height
    // for our PDF file.
    int pageHeight = 842;
    int pagewidth = 595;

    // creating a bitmap variable
    // for storing our images
    Bitmap bmp, scaledbmp;
    WebView webview;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_view);

        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);


        generatePDFbtn = findViewById(R.id.idBtnGeneratePDF);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 80, 60, false);

        // below code is used for
        // checking our permissions.
        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }

        generatePDFbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                // calling method to
                // generate our PDF file.
                generatePDF();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void generatePDF() {
        // creating an object variable
        // for our PDF document.
        PdfDocument pdfDocument = new PdfDocument();

        Paint paint = new Paint();
        Paint title = new Paint();
        Paint title1 = new Paint();
        Paint bill = new Paint();
        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();

        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        Canvas canvas = myPage.getCanvas();

        canvas.drawBitmap(scaledbmp, 50, 40, paint);

        title1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        title1.setTextSize(14);

        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        title.setTextSize(11);

        bill.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        bill.setTextSize(10);
        bill.setColor(ContextCompat.getColor(this, R.color.secondary));

        title.setColor(ContextCompat.getColor(this, R.color.black));
        title1.setColor(ContextCompat.getColor(this, R.color.secondary));
        canvas.drawText("Vyaapar Invoice", 250, 100, title1);
//        canvas.drawText("All the invoice are legit/valid.", 140, 90, title);
        canvas.drawText("Bill To:", 60, 130, title);
        canvas.drawText("Gopal Thapa", 60, 145, title);
        canvas.drawText("Invoice Type: Cash", 60, 160, title);


        canvas.drawText("Invoice Code:123", 460, 130, title);
        canvas.drawText("Date:2020/12/12", 460, 145, title);
        canvas.drawText("Tin No. :1200", 460, 160, title);
        Rect r = new Rect(10, 10, 200, 100);
        canvas.drawRect(r,bill);
        canvas.drawText("S.N", 150, 220, title);
        canvas.drawText("Item", 190, 220, title);
        canvas.drawText("Discount", 260, 220, title);
        canvas.drawText("Rate", 320, 220, title);
        canvas.drawText("Tax", 370, 220, title);
        canvas.drawText("Total", 410, 220, title);
        int incresed = 15;
        for (int i = 0; i < 5; i++) {
            canvas.drawText(""+(i+1), 150, 220+ incresed, bill);
            canvas.drawText("Pants", 190, 220 + incresed, bill);
            canvas.drawText("20%", 260, 220 + incresed, bill);
            canvas.drawText("100", 320, 220 + incresed, bill);
            canvas.drawText("300", 370, 220 + incresed, bill);
            canvas.drawText("12000", 410, 220 + incresed, bill);
            if(i==4){
                canvas.drawText("12000", 410, 220 + incresed+15, title);
                canvas.drawText("Authorized Signatory", 360, 270 + incresed+15, title);
            }
            incresed = incresed + 15;
        }

        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        title.setColor(ContextCompat.getColor(this, R.color.purple_200));
        title.setTextSize(15);

        title.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("This is sample document which we have created.", 396, 560, title);
        pdfDocument.finishPage(myPage);


        try {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir("pdfDir", Context.MODE_PRIVATE);
            File file = new File(directory, "UniqueFileName" + "GFG.pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            webview.loadUrl(fOut.toString());
            pdfDocument.writeTo(fOut);

            Toast.makeText(InvoiceViewActivity.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // below line is used
            // to handle error
            e.printStackTrace();
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close();
    }

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    private void viewPdf(String file, String directory) {

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + directory + "/" + file);
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(InvoiceViewActivity.this, "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }
    }
}