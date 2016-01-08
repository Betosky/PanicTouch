package redes.opencode.com.panictouch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage [] msjes = null;
        String str = "";
        if (bundle != null){
            Object[] pdus = (Object[]) bundle.get("pdus");
            msjes = new SmsMessage[pdus.length];
            for(int i = 0; i<msjes.length ; i++){
                msjes[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                str += "SMS de " + msjes[i].getOriginatingAddress();
                str += " : ";
                str += msjes[i].getMessageBody().toString();
                str += "\n";
            }
            Toast.makeText(context, str, Toast.LENGTH_LONG).show();
        }
    }
}
