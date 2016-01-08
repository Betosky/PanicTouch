package redes.opencode.com.panictouch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;

public class Mensaje extends AppCompatActivity {
Button btnMsje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);

        btnMsje = (Button)findViewById(R.id.btnMsje);
        btnMsje.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendSMS("+56988678358", "Ayuda, estoy en peligro.");
            }
        });
    }

private void sendSMS (String phoneNumber, String message) {
    SmsManager sms = SmsManager.getDefault();
    sms.sendTextMessage(phoneNumber,null,message,null,null);
}
}
