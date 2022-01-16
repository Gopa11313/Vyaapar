package net.com.gopal.vyapar.invoice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.connection.tcp.TcpConnection;
import com.dantsu.escposprinter.connection.usb.UsbConnection;
import com.dantsu.escposprinter.connection.usb.UsbPrintersConnections;
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;

import net.com.gopal.vyapar.MainActivity;
import net.com.gopal.vyapar.R;
import net.com.gopal.vyapar.async.AsyncBluetoothEscPosPrint;
import net.com.gopal.vyapar.async.AsyncEscPosPrinter;
import net.com.gopal.vyapar.async.AsyncTcpEscPosPrint;
import net.com.gopal.vyapar.async.AsyncUsbEscPosPrint;
import net.com.gopal.vyapar.invoice.model.Invoice;
import net.com.gopal.vyapar.invoice.model.InvoiceItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReceptActivity extends AppCompatActivity implements View.OnClickListener {
    Invoice invoice;
    ArrayList<InvoiceItem> invoiceItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recept);
        Button button = (Button) this.findViewById(R.id.button_bluetooth_browse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browseBluetoothDevice();
            }
        });
        button = (Button) findViewById(R.id.button_bluetooth);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printBluetooth();
            }
        });
        button = (Button) this.findViewById(R.id.button_usb);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printUsb();
            }
        });
        button = (Button) this.findViewById(R.id.button_tcp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printTcp();
            }
        });
        invoice = new Invoice();
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
                invoiceItem.setRate(jsonObject1.getString("rate"));
                invoiceItem.setTotal(jsonObject1.getString("total"));
                invoiceItems.add(invoiceItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static final int PERMISSION_BLUETOOTH = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case ReceptActivity.PERMISSION_BLUETOOTH:
                    this.printBluetooth();
                    break;
            }
        }
    }

    private BluetoothConnection selectedDevice;

    public void browseBluetoothDevice() {
        final BluetoothConnection[] bluetoothDevicesList = (new BluetoothPrintersConnections()).getList();

        if (bluetoothDevicesList != null) {
            final String[] items = new String[bluetoothDevicesList.length + 1];
            items[0] = "Default printer";
            int i = 0;
            for (BluetoothConnection device : bluetoothDevicesList) {
                items[++i] = device.getDevice().getName();
            }

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ReceptActivity.this);
            alertDialog.setTitle("Bluetooth printer selection");
            alertDialog.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int index = i - 1;
                    if (index == -1) {
                        selectedDevice = null;
                    } else {
                        selectedDevice = bluetoothDevicesList[index];
                    }
                    Button button = (Button) findViewById(R.id.button_bluetooth_browse);
                    button.setText(items[i]);
                }
            });

            AlertDialog alert = alertDialog.create();
            alert.setCanceledOnTouchOutside(false);
            alert.show();

        }
    }

    public void printBluetooth() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, ReceptActivity.PERMISSION_BLUETOOTH);
        } else {
            new AsyncBluetoothEscPosPrint(this).execute(this.getAsyncEscPosPrinter(selectedDevice));
        }
    }

    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private final BroadcastReceiver usbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ReceptActivity.ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
                    UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (usbManager != null && usbDevice != null) {
                            // printIt(new UsbConnection(usbManager, usbDevice));
                            new AsyncUsbEscPosPrint(context)
                                    .execute(getAsyncEscPosPrinter(new UsbConnection(usbManager, usbDevice)));
                        }
                    }
                }
            }
        }
    };

    public void printUsb() {
        UsbConnection usbConnection = UsbPrintersConnections.selectFirstConnected(this);
        UsbManager usbManager = (UsbManager) this.getSystemService(Context.USB_SERVICE);

        if (usbConnection == null || usbManager == null) {
            new AlertDialog.Builder(this)
                    .setTitle("USB Connection")
                    .setMessage("No USB printer found.")
                    .show();
            return;
        }

        PendingIntent permissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ReceptActivity.ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ReceptActivity.ACTION_USB_PERMISSION);
        registerReceiver(this.usbReceiver, filter);
        usbManager.requestPermission(usbConnection.getDevice(), permissionIntent);
    }

    /*==============================================================================================
    =========================================TCP PART===============================================
    ==============================================================================================*/

    public void printTcp() {
        final EditText ipAddress = (EditText) this.findViewById(R.id.edittext_tcp_ip);
        final EditText portAddress = (EditText) this.findViewById(R.id.edittext_tcp_port);

        try {
            // this.printIt(new TcpConnection(ipAddress.getText().toString(), Integer.parseInt(portAddress.getText().toString())));
            new AsyncTcpEscPosPrint(this)
                    .execute(this.getAsyncEscPosPrinter(new TcpConnection(ipAddress.getText().toString(), Integer.parseInt(portAddress.getText().toString()))));
        } catch (NumberFormatException e) {
            new AlertDialog.Builder(this)
                    .setTitle("Invalid TCP port address")
                    .setMessage("Port field must be a number.")
                    .show();
            e.printStackTrace();
        }
    }

    /*==============================================================================================
    ===================================ESC/POS PRINTER PART=========================================
    ==============================================================================================*/


    /**
     * Synchronous printing
     */
    @SuppressLint("SimpleDateFormat")
    public void printIt(DeviceConnection printerConnection) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("'on' yyyy-MM-dd 'at' HH:mm:ss");
            EscPosPrinter printer = new EscPosPrinter(printerConnection, 203, 48f, 32);
            printer
                    .printFormattedText(
                            "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer, this.getApplicationContext().getResources().getDrawableForDensity(R.drawable.logo, DisplayMetrics.DENSITY_MEDIUM)) + "</img>\n" +
                                    "[L]\n" +
                                    "[C]<u><font size='big'>ORDER N°" + invoice.getInvoiceCode() + "</font></u>\n" +
                                    "[L]\n" +
                                    "[L]5 Trade In Pocket\n" +
                                    "[L]\n" +
                                    "[C]<u type='double'>" + invoice.getDate() + "</u>\n" +
                                    "[C]\n" +
                                    "[C]================================\n" +
                                    "[L]\n" +
                                    "[L]<b>" + invoiceItems.get(0).getItemName() + "</b>[R]9.99€\n" +
                                    "[L]  + Discount : " + invoiceItems.get(0).getDiscount() + "\n" +
                                    "[L]  + Tax : " + invoiceItems.get(0).getTax() + "\n" +
                                    "[L]  + Total : " + invoiceItems.get(0).getTotal() + "\n" +
                                    "[L]\n" +
                                    "[L]<b>" + invoiceItems.get(1).getItemName() + "</b>[R]9.99€\n" +
                                    "[L]  + Discount : " + invoiceItems.get(1).getDiscount() + "\n" +
                                    "[L]  + Tax : " + invoiceItems.get(1).getTax() + "\n" +
                                    "[L]  + Total : " + invoiceItems.get(1).getTotal() + "\n" +
                                    "[L]\n" +
                                    "[L]<b>" + invoiceItems.get(2).getItemName() + "</b>[R]9.99€\n" +
                                    "[L]  + Discount : " + invoiceItems.get(2).getDiscount() + "\n" +
                                    "[L]  + Tax : " + invoiceItems.get(2).getTax() + "\n" +
                                    "[L]  + Total : " + invoiceItems.get(2).getTotal() + "\n" +
                                    "[L]\n" +
                                    "[C]--------------------------------\n" +
                                    "[R]TOTAL PRICE :[R]" + invoice.getTotal() + "\n" +
//                                    "[R]TAX :[R]4.23€\n" +
//                                    "[L]\n" +
                                    "[C]================================\n" +
                                    "[L]\n" +
                                    "[L]<u><font color='bg-black' size='tall'>Customer :"+invoice.getCustomerName()+"</font></u>\n" +
                                    "[L] TIN No."+invoice.getTinNo()+"\n" +
                                    "[L] Invoice Type"+invoice.getInvoiceType()+"\n" +
                                    "[L]31547 PERPETES\n" +
                                    "[L]Tel : +33801201456\n" +
                                    "\n" +
                                    "[C]<barcode type='ean13' height='10'>831254784551</barcode>\n"
//                                    "[L]\n" +
//                                    "[C]<qrcode size='20'>http://www.developpeur-web.dantsu.com/</qrcode>\n"
                    );
        } catch (EscPosConnectionException e) {
            e.printStackTrace();
            new AlertDialog.Builder(this)
                    .setTitle("Broken connection")
                    .setMessage(e.getMessage())
                    .show();
        } catch (EscPosParserException e) {
            e.printStackTrace();
            new AlertDialog.Builder(this)
                    .setTitle("Invalid formatted text")
                    .setMessage(e.getMessage())
                    .show();
        } catch (EscPosEncodingException e) {
            e.printStackTrace();
            new AlertDialog.Builder(this)
                    .setTitle("Bad selected encoding")
                    .setMessage(e.getMessage())
                    .show();
        } catch (EscPosBarcodeException e) {
            e.printStackTrace();
            new AlertDialog.Builder(this)
                    .setTitle("Invalid barcode")
                    .setMessage(e.getMessage())
                    .show();
        }
    }

    /**
     * Asynchronous printing
     */
    @SuppressLint("SimpleDateFormat")
    public AsyncEscPosPrinter getAsyncEscPosPrinter(DeviceConnection printerConnection) {
        SimpleDateFormat format = new SimpleDateFormat("'on' yyyy-MM-dd 'at' HH:mm:ss");
        AsyncEscPosPrinter printer = new AsyncEscPosPrinter(printerConnection, 203, 48f, 32);
        return printer.setTextToPrint(
                "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer, this.getApplicationContext().getResources().getDrawableForDensity(R.drawable.logo, DisplayMetrics.DENSITY_MEDIUM)) + "</img>\n" +
                        "[L]\n" +
                        "[C]<u><font size='big'>ORDER N°" + invoice.getInvoiceCode() + "</font></u>\n" +
                        "[L]\n" +
                        "[L]5 Trade In Pocket\n" +
                        "[L]\n" +
                        "[C]<u type='double'>" + invoice.getDate() + "</u>\n" +
                        "[C]\n" +
                        "[C]================================\n" +
                        "[L]\n" +
                        "[L]<b>" + invoiceItems.get(0).getItemName() + "</b>[R]9.99€\n" +
                        "[L]  + Discount : " + invoiceItems.get(0).getDiscount() + "\n" +
                        "[L]  + Tax : " + invoiceItems.get(0).getTax() + "\n" +
                        "[L]  + Total : " + invoiceItems.get(0).getTotal() + "\n" +
                        "[L]\n" +
                        "[L]<b>" + invoiceItems.get(1).getItemName() + "</b>[R]9.99€\n" +
                        "[L]  + Discount : " + invoiceItems.get(1).getDiscount() + "\n" +
                        "[L]  + Tax : " + invoiceItems.get(1).getTax() + "\n" +
                        "[L]  + Total : " + invoiceItems.get(1).getTotal() + "\n" +
                        "[L]\n" +
                        "[L]<b>" + invoiceItems.get(2).getItemName() + "</b>[R]9.99€\n" +
                        "[L]  + Discount : " + invoiceItems.get(2).getDiscount() + "\n" +
                        "[L]  + Tax : " + invoiceItems.get(2).getTax() + "\n" +
                        "[L]  + Total : " + invoiceItems.get(2).getTotal() + "\n" +
                        "[L]\n" +
                        "[C]--------------------------------\n" +
                        "[R]TOTAL PRICE :[R]" + invoice.getTotal() + "\n" +
//                                    "[R]TAX :[R]4.23€\n" +
//                                    "[L]\n" +
                        "[C]================================\n" +
                        "[L]\n" +
                        "[L]<u><font color='bg-black' size='tall'>Customer :"+invoice.getCustomerName()+"</font></u>\n" +
                        "[L] TIN No."+invoice.getTinNo()+"\n" +
                        "[L] Invoice Type"+invoice.getInvoiceType()+"\n" +
                        "[L]31547 PERPETES\n" +
                        "[L]Tel : +33801201456\n" +
                        "\n" +
                        "[C]<barcode type='ean13' height='10'>831254784551</barcode>\n"
        );
    }

    @Override
    public void onClick(View v) {

    }
}