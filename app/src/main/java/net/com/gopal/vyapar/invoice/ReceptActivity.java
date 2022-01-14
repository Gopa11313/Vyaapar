package net.com.gopal.vyapar.invoice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import net.com.gopal.vyapar.R;

public class ReceptActivity extends Activity implements View.OnClickListener {
    private String openDeviceName = "192.168.192.168";
    private int connectionType = Print.DEVTYPE_TCP;
    private int printerModel = Builder.LANG_EN;
    private String printerName = null;

    private EditText editWarnings = null;
    private Spinner spnNames = null;
    private Spinner spnModels = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recept);
    }

    @Override
    public void onClick(View v) {

    }
}