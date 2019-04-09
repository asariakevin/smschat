
package com.example.groupsms;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final int SMS_READ = 1;
    final int SMS_SEND = 2;

    public static final String OTP_REGEX = "[0-9]{1,6}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check if SMS sending and viewing permissions
        //is granted
        if(isSmsPermissionGranted() != PackageManager.PERMISSION_GRANTED){

            //permission is not granted
            requestReadAndSendSmsPermission();

        }




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String [] permissions, int[] grantResults){

        switch( requestCode){

            case SMS_READ :

                if( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    SmsReceiver.bindListener(new SmsListener() {
                        @Override
                        public void messageReceived(String messageText) {

                            //From the received text string you may do string operations to get the required OTP
                            //It depends on your SMS format
                            Log.e("Message",messageText);
                            Toast.makeText(MainActivity.this,"Message: "+messageText,Toast.LENGTH_LONG).show();

                        }
                    });



                    SmsManager.getDefault()
                            .sendTextMessage("0719144643",
                                    null,
                                    "Hello WOrld",
                                    null,
                                    null);
                }

            case SMS_SEND:

                if()
        }
    }


    public int isSmsPermissionGranted(){

        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
    }

    //request runtime SMS permission
    private void requestReadAndSendSmsPermission(){

        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_SMS)){


        }else{
            ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.READ_SMS},SMS_READ);
            ActivityCompat.requestPermissions(this,String][]{ Manifest.permission.SEND_SMS},);


        }


    }
}
