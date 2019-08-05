package com.example.groupsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {
    //interface
    private static SmsListener mListener;

    @Override
    public void onReceive(Context context, Intent intent) {

        //Bundle is used to pass data between activities
        //getExtras() is used to retreive data from an Intent
        Bundle data  = intent.getExtras();

        //PDU - 'protocol data unit'
        //industry format for an sms message
        Object[] pdus = (Object[]) data.get("pdus");

        for(int i=0;i<pdus.length;i++){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

            String sender = smsMessage.getDisplayOriginatingAddress();
            //Check the sender to filter messages which we require to read

            Log.d("name of sender",sender);
            Toast.makeText(context.getApplicationContext(),sender,Toast.LENGTH_LONG).show();



                String messageBody = smsMessage.getMessageBody();

                //Pass the message text to interface
                mListener.messageReceived(messageBody);


        }

    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}
