package net.com.gopal.vyapar.invoice;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.tejpratapsingh.pdfcreator.activity.PDFCreatorActivity;
import com.tejpratapsingh.pdfcreator.views.PDFBody;
import com.tejpratapsingh.pdfcreator.views.PDFFooterView;
import com.tejpratapsingh.pdfcreator.views.PDFHeaderView;

import net.com.gopal.vyapar.R;

import java.io.File;

public class PdfViewerExampleActivity extends PDFCreatorActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Pdf Viewer");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                    .getColor(R.color.colorTransparentBlack)));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pdf_viewer, menu);
        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.menuPrintPdf) {
            File fileToPrint = getPdfFile();
            if (fileToPrint == null || !fileToPrint.exists()) {
                Toast.makeText(this, R.string.text_generated_file_error, Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            }

            PrintAttributes.Builder printAttributeBuilder = new PrintAttributes.Builder();
            printAttributeBuilder.setMediaSize(PrintAttributes.MediaSize.ISO_A4);
            printAttributeBuilder.setMinMargins(PrintAttributes.Margins.NO_MARGINS);

            PDFUtil.printPdf(PdfViewerExampleActivity.this, fileToPrint, printAttributeBuilder.build());
        } else if (item.getItemId() == R.id.menuSharePdf) {
            File fileToShare = getPdfFile();
            if (fileToShare == null || !fileToShare.exists()) {
                Toast.makeText(this, R.string.text_generated_file_error, Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            }

            Intent intentShareFile = new Intent(Intent.ACTION_SEND);

            Uri apkURI = FileProvider.getUriForFile(
                    getApplicationContext(),
                    getApplicationContext()
                            .getPackageName() + ".provider", fileToShare);
            intentShareFile.setDataAndType(apkURI, URLConnection.guessContentTypeFromName(fileToShare.getName()));
            intentShareFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            intentShareFile.putExtra(Intent.EXTRA_STREAM,
                    apkURI);

            startActivity(Intent.createChooser(intentShareFile, "Share File"));
        }
        return super.onOptionsItemSelected(item);
    }
}