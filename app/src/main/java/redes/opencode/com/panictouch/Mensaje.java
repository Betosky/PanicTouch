package redes.opencode.com.panictouch;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Mensaje extends AppCompatActivity {
    //private EditText nFono;
    private EditText msje;
    private Button btnMsje;
    private Button buttonReadContact;
    //private TextView textPhone;
    private TextView estado;
    private String contacto = "";

    final int RQS_PICKCONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);

        //nFono = (EditText)findViewById(R.id.editText);
        msje = (EditText)findViewById(R.id.editText2);
        buttonReadContact = (Button)findViewById(R.id.readcontact);
        //textPhone = (TextView)findViewById(R.id.phone);
        estado = (TextView)findViewById(R.id.textView3);

        buttonReadContact.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                //Start activity to get contact
                final Uri uriContact = ContactsContract.Contacts.CONTENT_URI;
                Intent intentPickContact = new Intent(Intent.ACTION_PICK, uriContact);
                startActivityForResult(intentPickContact, RQS_PICKCONTACT);
            }});

        btnMsje = (Button)findViewById(R.id.btnMsje);
        btnMsje.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //String numero = nFono.getText().toString();
                String sms = msje.getText().toString();

                try{
                    if(contacto!=null){
                        sendSMS(contacto,sms);
                        Toast.makeText(getApplicationContext(), "SMS enviado",
                                Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(),"SMS fallido. El mensaje no ha sido enviado",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // TODO Auto-generated method stub
    if(resultCode == RESULT_OK){
        if(requestCode == RQS_PICKCONTACT){
            Uri returnUri = data.getData();
            Cursor cursor = getContentResolver().query(returnUri, null, null, null, null);

            if(cursor.moveToNext()){
                int columnIndex_ID = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                String contactID = cursor.getString(columnIndex_ID);

                int columnIndex_HASPHONENUMBER = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
                String stringHasPhoneNumber = cursor.getString(columnIndex_HASPHONENUMBER);

                if(stringHasPhoneNumber.equalsIgnoreCase("1")){
                    Cursor cursorNum = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactID,
                            null,
                            null);

                    //Get the first phone number
                    if(cursorNum.moveToNext()){
                        int columnIndex_number = cursorNum.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        String stringNumber = cursorNum.getString(columnIndex_number);
                        this.contacto = stringNumber;
                        //textPhone.setText(stringNumber);
                        estado.setText("Su mensaje de ayuda será enviado a: " + stringNumber);
                    }

                }else{
                    estado.setText("Numero no seleccionado");
                }


            }else{
                Toast.makeText(getApplicationContext(), "Sin datos!", Toast.LENGTH_LONG).show();
            }
        }
    }
}

private void sendSMS (String phoneNumber, String message) {
    SmsManager sms = SmsManager.getDefault();
    sms.sendTextMessage(phoneNumber,null,message,null,null);
}
}
