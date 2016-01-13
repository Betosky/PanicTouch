package redes.opencode.com.panictouch;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Application;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.Toast;

import static redes.opencode.com.panictouch.Mensaje.getState;
import static redes.opencode.com.panictouch.Mensaje.getNumTel;
import static redes.opencode.com.panictouch.Mensaje.sendSMS;

public class MiWidget extends AppWidgetProvider {
    @Override
        public void onUpdate(Context context,
                             AppWidgetManager appWidgetManager,
                             int[] appWidgetIds) {
            //Actualizar el widget
            //...
        for (int i = 0; i < appWidgetIds.length; i++)
        {
            //ID del widget actual
            int widgetId = appWidgetIds[i];

            //Actualizamos el widget actual
            actualizarWidget(context, appWidgetManager, widgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("redes.opencode.com.panictouch.ACTUALIZAR_WIDGET")) {

            //Obtenemos el ID del widget a actualizar
            int widgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

            //Obtenemos el widget manager de nuestro contexto
            AppWidgetManager widgetManager =
                    AppWidgetManager.getInstance(context);

            //Actualizamos el widget
            if (widgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                actualizarWidget(context, widgetManager, widgetId);
            }
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds)
    {
        //Accedemos a las preferencias de la aplicaci�n
        SharedPreferences prefs =
                context.getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        //Eliminamos las preferencias de los widgets borrados
        for(int i=0; i<appWidgetIds.length; i++)
        {
            //ID del widget actual
            int widgetId = appWidgetIds[i];

            editor.remove("msg_" + widgetId);
        }

        //Aceptamos los cambios
        editor.commit();

        super.onDeleted(context, appWidgetIds);
    }

    public static void actualizarWidget(Context context,
                                        AppWidgetManager appWidgetManager, int widgetId)
    {
        //Recuperamos el mensaje personalizado para el widget actual

        String sms = Mensaje.getState();
        String num = Mensaje.getNumTel();

        if(num.equals("")) {
            Toast.makeText(context, "Sleccione contacto para enviar alertas", Toast.LENGTH_LONG).show();
        }
        SharedPreferences prefs =
                context.getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
        String mensaje = prefs.getString("msg_" + widgetId, "Hora actual:");

        //Obtenemos la lista de controles del widget actual
        RemoteViews controles =
                new RemoteViews(context.getPackageName(), R.layout.miwidget);

        //Asociamos los 'eventos' al widget
        Intent intent = new Intent("redes.opencode.com.panictouch.ACTUALIZAR_WIDGET");
        intent.putExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context, widgetId,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);

        controles.setOnClickPendingIntent(R.id.BtnActualizar, pendingIntent);

        Intent intent2 = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent2 =
                PendingIntent.getActivity(context, widgetId,
                        intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        controles.setOnClickPendingIntent(R.id.FrmWidget, pendingIntent2);

        //Actualizamos el mensaje en el control del widget
        controles.setTextViewText(R.id.LblMensaje, mensaje + ". Sms: " + sms);

        try{
            if(num!=null){
                Mensaje.sendSMS(num, sms);
                Toast.makeText(context, "SMS enviado",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e){
            Toast.makeText(context,"SMS fallido. El mensaje no ha sido enviado",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        //Obtenemos la hora actual
        Calendar calendario = new GregorianCalendar();
        String hora = calendario.getTime().toLocaleString();

        //Actualizamos la hora en el control del widget
        controles.setTextViewText(R.id.LblHora, hora);

        //Notificamos al manager de la actualizaci�n del widget actual
        appWidgetManager.updateAppWidget(widgetId, controles);

    }

}
