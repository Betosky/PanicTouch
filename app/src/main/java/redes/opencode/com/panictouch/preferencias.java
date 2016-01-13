package redes.opencode.com.panictouch;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Vector;

/**
 * Created by Cobert021 on 13-01-2016.
 */
public class preferencias  {
    private static String PREFERENCIAS = "puntuaciones";
    private Context context;

    public preferencias(Context context) {
        this.context = context;
    }

    public void guardarDatos(int puntos, String mensaje) {
        SharedPreferences preferencias =context.getSharedPreferences(
                PREFERENCIAS, Context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("Contacto", puntos + " " + mensaje);
        editor.commit();
    }

    public Vector<String> listaDatos (int cantidad) {
        Vector<String> result = new Vector<String>();
        SharedPreferences preferencias =context.getSharedPreferences(
                PREFERENCIAS, Context.MODE_WORLD_WRITEABLE);
        String s = preferencias.getString("puntuacion", "");
        if (s != "") {
            result.add(s);
        }
        return result;
    }
}