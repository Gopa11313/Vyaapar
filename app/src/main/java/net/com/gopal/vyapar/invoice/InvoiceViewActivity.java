package net.com.gopal.vyapar.invoice;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

import net.com.gopal.vyapar.R;
import net.com.gopal.vyapar.dashboard.DashBoardActivity;
import net.com.gopal.vyapar.invoice.model.Invoice;
import net.com.gopal.vyapar.invoice.model.InvoiceItem;
import net.com.gopal.vyapar.invoice.pdf.Common;
import net.com.gopal.vyapar.invoice.pdf.pdfDocumentAdapter;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class InvoiceViewActivity extends AppCompatActivity {
    Button generatePDFbtns;
    private static final int PERMISSION_REQUEST_CODE = 200;
    int pageHeight = 842;
    int pagewidth = 595;
    Toolbar toolbaruni;
    public TextView title;
    Bitmap bmp, scaledbmp;
    WebView webview;
    ArrayList<InvoiceItem> invoiceItems = new ArrayList<>();
    Invoice invoice;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_view);
        toolbaruni = findViewById(R.id.toolbar);
        setSupportActionBar(toolbaruni);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setTitleToolbar("Invoice Create");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbaruni.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }
        generatePDFbtns = findViewById(R.id.idBtnGeneratePDF);
        generatePDFbtns.setOnClickListener(v -> {
            createPdf(Common.getAppPath(InvoiceViewActivity.this) + "test_pdf.pdf");
        });

        Intent intentData = getIntent();
        String data = intentData.getStringExtra("data");
        try {
            JSONObject jsonObject = new JSONObject(data);
            invoice = new Invoice();
            String customerName = jsonObject.getString("customerName");
            String date = jsonObject.getString("date");
            String invoiceCode = jsonObject.getString("invoiceCode");
            String invoice_id = jsonObject.getString("invoice_id");
            String tinNo = jsonObject.getString("tinNo");
            String total = jsonObject.getString("total");
            String invoiceType = jsonObject.getString("invoiceType");
//            String total = jsonObject.getString("total");
            String hb = jsonObject.getString("invoiceItem");
            invoice.setInvoiceCode(invoiceCode);
            invoice.setCustomerName(customerName);
            invoice.setDate(date);
            invoice.setTinNo(tinNo);
            invoice.setInvoice_id(Integer.parseInt(invoiceCode));
            invoice.setTotal(total);
            invoice.setInvoiceType(invoiceType);
//            invoice.setInvoiceType();
            JSONArray jsonArray = new JSONArray(hb);
            for (int i = 0; i < jsonArray.length(); i++) {
                InvoiceItem invoiceItem = new InvoiceItem();
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                invoiceItem.setTax(jsonObject1.getString("tax"));
                invoiceItem.setItemName(jsonObject1.getString("itemName"));
                invoiceItem.setDiscount(jsonObject1.getString("discount"));
                invoiceItem.setQuantity(jsonObject1.getString("quantity"));
                invoiceItem.setRate(jsonObject1.getString("rate"));
                invoiceItem.setTotal(jsonObject1.getString("total"));
                invoiceItems.add(invoiceItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPdf(String path) {
        if (new File(path).exists()) {
            new File(path).delete();
        }
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();

            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor("Gopal");
            document.addCreator("Gopal Thapa");
            BaseColor colorAgent = new BaseColor(0, 153, 204, 255);
            float fontsize = 20.0f;
            float valueFontSize = 16.0f;

            BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.WINANSI, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 16.0f, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, "Trade In Pocket", Element.ALIGN_CENTER, titleFont);

            Font orderNumberFont = new Font(baseFont, valueFontSize, Font.NORMAL, colorAgent);
            addNewItem(document, "Invoice No", Element.ALIGN_CENTER, orderNumberFont);

            Font orderNumberValueFont = new Font(baseFont, valueFontSize, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, invoice.getInvoiceCode(), Element.ALIGN_CENTER, orderNumberValueFont);

            addLineSpeactor(document);

            addNewItem(document, "Date", Element.ALIGN_LEFT, orderNumberValueFont);
            addNewItem(document, invoice.getDate(), Element.ALIGN_LEFT, orderNumberValueFont);

            addLineSpeactor(document);

            addNewItem(document, "Account Name", Element.ALIGN_LEFT, orderNumberValueFont);
            addNewItem(document, invoice.getCustomerName(), Element.ALIGN_LEFT, orderNumberValueFont);

            addLineSpeactor(document);

            addNewItem(document, "Item Details", Element.ALIGN_LEFT, orderNumberValueFont);
            addLineSpeactor(document);
            for (int i = 0; i < invoiceItems.size(); i++) {
                addNewItemWithLeftAndRight(document, invoiceItems.get(i).getItemName(), "(" + invoiceItems.get(i).getDiscount() +"%"+ ")", titleFont, orderNumberValueFont);
                addNewItemWithLeftAndRight(document, "" + invoiceItems.get(i).getQuantity() + "*" + invoiceItems.get(i).getRate() + "", "" + invoiceItems.get(i).getTotal() + "", titleFont, orderNumberValueFont);
                addLineSpeactor(document);
            }

//            addNewItemWithLeftAndRight(document, "Pizza 21", "(0.0%)", titleFont, orderNumberValueFont);
//            addNewItemWithLeftAndRight(document, "12.0*1000", "12000.0", titleFont, orderNumberValueFont);
//            addLineSpeactor(document);
//
//            addNewItemWithLeftAndRight(document, "Pizza 22", "(0.0%)", titleFont, orderNumberValueFont);
//            addNewItemWithLeftAndRight(document, "12.0*1000", "12000.0", titleFont, orderNumberValueFont);
//            addLineSpeactor(document);

            addLineSpace(document);
            addLineSpace(document);
            addNewItemWithLeftAndRight(document, "Total", ""+invoice.getTotal()+"", titleFont, orderNumberValueFont);
            addLineSpace(document);
            addLineSpace(document);
            addLineSpace(document);
            addNewItemWithLeftAndRight(document, "", "Signature", titleFont, orderNumberValueFont);

            document.close();
            Toast.makeText(InvoiceViewActivity.this, "success", Toast.LENGTH_LONG).show();

            printPDF();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void printPDF() {
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        try {
            PrintDocumentAdapter printDocumentAdapter = new pdfDocumentAdapter(InvoiceViewActivity.this, Common.getAppPath(InvoiceViewActivity.this) + "test_pdf.pdf");
            printManager.print("Document", printDocumentAdapter, new PrintAttributes.Builder().build());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addNewItemWithLeftAndRight(Document document, String textLeft, String textRight, Font textLeftRight, Font rightFont) throws DocumentException {
        Chunk chunkTextLeft = new Chunk(textLeft, textLeftRight);
        Chunk chunkTextRight = new Chunk(textRight, rightFont);
        Paragraph p = new Paragraph(chunkTextLeft);
        p.add(new Chunk(new VerticalPositionMark()));
        p.add(chunkTextRight);
        document.add(p);


    }

    private void addLineSpeactor(Document document) throws DocumentException {
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));
        addLineSpace(document);
        document.add(new Chunk(lineSeparator));
        addLineSpace(document);
    }

    private void addLineSpace(Document document) throws DocumentException {
        document.add(new Paragraph(""));
    }

    private void addNewItem(Document document, String text, int align, Font font) throws DocumentException {
        Chunk chunk = new Chunk(text, font);
        Paragraph paragraph = new Paragraph(chunk);
        paragraph.setAlignment(align);
        document.add(paragraph);
    }

    private boolean checkPermission() {
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }
    public Toolbar getToolbar() {
        return toolbaruni;
    }

    public void setTitleToolbar(String string) {

        TextView toolbar_text = (TextView) toolbaruni.findViewById(R.id.title);
        toolbar_text.setText(string);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(InvoiceViewActivity.this, InvoiceActivity.class);
        startActivity(intent);
        finish();
    }
}