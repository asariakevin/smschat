
package com.example.groupsms;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final int SMS_READ = 1;
    final int SMS_SEND = 2;

    TextView smsView;
    FloatingActionButton fab;
    //TODO : add a floatingAction Button to add a chat group
    //TODO : 


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : open create group dialog box
                createGroupDialog();
                //Snackbar.make(v,"h",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            }
        });

        //check if SMS sending and viewing permissions
        //is granted
        if (isSmsPermissionGranted() != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            // if permission is not granted
            requestReadAndSendSmsPermission();

        } else {

            SmsReceiver.bindListener(new SmsListener() {
                @Override
                public void messageReceived(String messageText) {

                    //From the received text string you may do string operations to get the required OTP
                    //It depends on your SMS format
                    Log.e("Message", messageText);


                    smsView.setText(messageText);

                    Toast.makeText(MainActivity.this, "Message: " + messageText, Toast.LENGTH_LONG).show();

                    //TODO: send message to appropriate contact list

                }
            });
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case SMS_READ:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {


//                    SmsManager.getDefault()
//                            .sendTextMessage("0738965577",
//                                    null,
//                                    "Hello World",
//                                    null,
//                                    null);
//                }
//
//                Log.d("sms sent:", "tru");


                }
        }

    }

    public int isSmsPermissionGranted() {

        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
    }

    //to create the Create Group Dialog Box
    private void createGroupDialog(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.create_group_dialog_box,null);

        final EditText editText = dialogView.findViewById(R.id.group_name_editText);
        Button cancelButton = dialogView.findViewById(R.id.cancel_group_button);
        Button createButton = dialogView.findViewById(R.id.create_group_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: return to main activity
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: open a new activity with the name of group
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    //request runtime SMS permission
    private void requestReadAndSendSmsPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
            //dialog to show why you need the permission
            //in case you were denied the first time

        } else {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS}
                    , SMS_READ);


        }


    }

}
