package redes.opencode.com.panictouch;

import android.app.Activity;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class Gesture extends Activity implements
        GestureOverlayView.OnGesturePerformedListener {
    private GestureLibrary libreria;
    private TextView salida;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        libreria = GestureLibraries.fromRawResource(this,
                R.raw.gestures);
        if (!libreria.load()) {
            finish();
        }
        GestureOverlayView gesturesView = (GestureOverlayView)
                findViewById(R.id.gestures);
        gesturesView.addOnGesturePerformedListener(this);
        salida = (TextView) findViewById(R.id.salida);
    }

    public void onGesturePerformed(GestureOverlayView ov,
                                   android.gesture.Gesture gesture) {
        ArrayList<Prediction> predictions =
                libreria.recognize(gesture);
        salida.setText("");
        for (Prediction prediction : predictions){
            salida.append(prediction.name+" " +
                    prediction.score+"\n");
        }
    }
}