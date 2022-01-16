package net.com.gopal.vyapar.invoice.pdf;

import android.content.Context;
import android.os.Environment;

import net.com.gopal.vyapar.R;

import java.io.File;

public class Common {
    public static String getAppPath(Context context) {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + File.separator
                + "app_pdf"
                + File.separator
        );
        if (!dir.exists())
            dir.mkdir();
        return dir.getPath() + File.separator;
    }
}
