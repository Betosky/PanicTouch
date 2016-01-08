package redes.opencode.com.panictouch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Mensaje extends AppCompatActivity {
    private EditText nFono;
    private EditText msje;
    private Button btnMsje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);

        nFono = (EditText)findViewById(R.id.editText);
        msje = (EditText)findViewById(R.id.editText2);

        btnMsje = (Button)findViewById(R.id.btnMsje);
        btnMsje.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String numero = nFono.getText().toString();
                String sms = msje.getText().toString();

                try{
                    sendSMS(numero,sms);
                    Toast.makeText(getApplicationContext(), "SMS enviado",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(),"SMS fallido. El mensaje no ha sido enviado",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }

private void sendSMS (String phoneNumber, String message) {
    SmsManager sms = SmsManager.getDefault();
    sms.sendTextMessage(phoneNumber,null,message,null,null);
}
}
