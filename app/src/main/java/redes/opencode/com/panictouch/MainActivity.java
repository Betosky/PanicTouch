package redes.opencode.com.panictouch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    private TextView lblEtiqueta;
    private ListView lstOpciones;

    private Titular[] datos =
            new Titular[]{
                    new Titular("Mensaje de Ayuda", "Configura el mensaje de ayuda"),
                    new Titular("Conecta con Facebook", "Publica en tu muro"),
                    new Titular("Alerta por WhatsApp", "Envia el Mensaje a quienes quieras"),
                    new Titular("Conecta con Tweeter", "Postea un tweet"),
                    new Titular("Gesto", "Prueba un gesto"),
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ejemplo básico
        //final String[] datos =
        //        new String[]{"Elem1","Elem2","Elem3","Elem4","Elem5"};
        //
        //ArrayAdapter<String> adaptador =
        //        new ArrayAdapter<String>(this,
        //                android.R.layout.simple_list_item_1, datos);
        //
        //lstOpciones = (ListView)findViewById(R.id.LstOpciones);
        //
        //lstOpciones.setAdapter(adaptador);

        lblEtiqueta = (TextView)findViewById(R.id.LblEtiqueta);
        lstOpciones = (ListView)findViewById(R.id.LstOpciones);

        //Cabecera
        View header = getLayoutInflater().inflate(R.layout.list_header, null);
        lstOpciones.addHeaderView(header);

        //Adaptador
        AdaptadorTitulares adaptador =
                new AdaptadorTitulares(this, datos);

        lstOpciones.setAdapter(adaptador);

        //Eventos
        lstOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                //Alternativa 1:
                String opcionSeleccionada =
                        ((Titular)a.getItemAtPosition(position)).getTitulo();

                //Alternativa 2:
                //String opcionSeleccionada =
                //		((TextView)v.findViewById(R.id.LblTitulo))
                //			.getText().toString();

                switch(position){
                    case 1:
                        pasarAMensaje(v);
                        break;
                    case 2:
                        pasarAFacebook(v);
                        break;
                    case 3:
                        //pasarAWhatsApp(v);
                        break;
                    case 4:
                        //pasarATwitter(v);
                        break;
                    case 5:
                        pasarAGesture(v);
                        break;
                }
                //lblEtiqueta.setText("Opción seleccionada: " + opcionSeleccionada + position);
            }
        });
    }
public void pasarAFacebook(View v){
    Intent act = new Intent(this, Facebook.class);
    startActivity(act);
}
    //public void pasarATwitter(View v){
        //Intent act = new Intent(this, Twitter2.class);
        //startActivity(act);
    //}
    public void pasarAMensaje(View v){
        Intent act = new Intent(this, Mensaje.class);
        startActivity(act);
    }
    public void pasarAGesture(View v){
        Intent act = new Intent(this, Gesture.class);
        startActivity(act);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class AdaptadorTitulares extends ArrayAdapter<Titular> {

        public AdaptadorTitulares(Context context, Titular[] datos) {
            super(context, R.layout.activity_titular, datos);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.activity_titular, null);

            TextView lblTitulo = (TextView)item.findViewById(R.id.LblTitulo);
            lblTitulo.setText(datos[position].getTitulo());

            TextView lblSubtitulo = (TextView)item.findViewById(R.id.LblSubTitulo);
            lblSubtitulo.setText(datos[position].getSubtitulo());

            return(item);
        }
    }
}