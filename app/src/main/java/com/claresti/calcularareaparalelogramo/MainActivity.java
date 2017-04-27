package com.claresti.calcularareaparalelogramo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.math.*;

public class MainActivity extends AppCompatActivity {

    //Elementos de layout
    private EditText x1;
    private EditText y1;
    private EditText z1;
    private EditText x2;
    private EditText y2;
    private EditText z2;
    private EditText x3;
    private EditText y3;
    private EditText z3;
    private Button calcular;
    private TextView calculando;
    private ProgressBar bar;
    private RelativeLayout resultado;
    private TextView pq;
    private TextView pr;
    private TextView areaNoEx;
    private TextView areaEx;
    private TextView diferenciaArea;
    private double[] p;
    private double[] q;
    private double[] r;
    double vectorMagPQ;
    double vectorMagPR;
    double NoExacta;
    double exPQPR;
    double diferencia;
    int flag = 0;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(flag == 10){
                Log.i("bandera", flag + " entrando a la seccion para mostrar todo");
                pq.setText("Magnitud vetorPQ = " + Double.toString(vectorMagPQ));
                pr.setText("Magnitud vetorPR = " + Double.toString(vectorMagPR));
                areaNoEx.setText("Area no exacta = " + Double.toString(NoExacta));
                areaEx.setText("Area exacta = " + Double.toString(exPQPR));
                diferenciaArea.setText("Diferencia de areas = " + Double.toString(diferencia));
                calculando.setText("CALCULO COMPLETO");
                resultado.setVisibility(View.VISIBLE);
                flag = 0;
            }else{
                bar.incrementProgressBy(10);
                flag ++;
                Log.i("bandera", flag + "");
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x1 = (EditText)findViewById(R.id.x1);
        y1 = (EditText)findViewById(R.id.y1);
        z1 = (EditText)findViewById(R.id.z1);
        x2 = (EditText)findViewById(R.id.x2);
        y2 = (EditText)findViewById(R.id.y2);
        z2 = (EditText)findViewById(R.id.z2);
        x3 = (EditText)findViewById(R.id.x3);
        y3 = (EditText)findViewById(R.id.y3);
        z3 = (EditText)findViewById(R.id.z3);
        calcular = (Button)findViewById(R.id.btn_calcular);
        calculando = (TextView)findViewById(R.id.txt_calculando);
        bar = (ProgressBar)findViewById(R.id.bar);
        resultado = (RelativeLayout)findViewById(R.id.l_resultado);
        pq = (TextView)findViewById(R.id.pq);
        pr = (TextView)findViewById(R.id.pr);
        areaNoEx = (TextView)findViewById(R.id.areaNoEx);
        areaEx = (TextView)findViewById(R.id.areaEx);
        diferenciaArea = (TextView)findViewById(R.id.diferenciaArea);
        p = new double[3];
        q = new double[3];
        r = new double[3];
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    p[0] = Double.parseDouble(x1.getText().toString());
                    p[1] = Double.parseDouble(y1.getText().toString());
                    p[2] = Double.parseDouble(z1.getText().toString());
                    q[0] = Double.parseDouble(x2.getText().toString());
                    q[1] = Double.parseDouble(y2.getText().toString());
                    q[2] = Double.parseDouble(z2.getText().toString());
                    r[0] = Double.parseDouble(x3.getText().toString());
                    r[1] = Double.parseDouble(y3.getText().toString());
                    r[2] = Double.parseDouble(z3.getText().toString());
                    calculando.setVisibility(View.VISIBLE);
                    bar.setVisibility(View.VISIBLE);
                    Thread hilo = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            vectorMagPQ = calcularMagVector(p, q);
                            handler.sendMessage(handler.obtainMessage());
                            vectorMagPR = calcularMagVector(p, r);
                            handler.sendMessage(handler.obtainMessage());
                            NoExacta = vectorMagPQ * vectorMagPR;
                            handler.sendMessage(handler.obtainMessage());
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                Toast.makeText(getApplicationContext(), "Ocurrio algun error", Toast.LENGTH_SHORT).show();
                            }
                            double[] vectorPQ = {
                                    (p[0] - q[0]),
                                    (p[1] - q[1]),
                                    (p[2] - q[2])};
                            handler.sendMessage(handler.obtainMessage());
                            double[] vectorPR = {
                                    (p[0] - r[0]),
                                    (p[1] - r[1]),
                                    (p[2] - r[2])};
                            handler.sendMessage(handler.obtainMessage());
                            double[] vectorQR = {
                                    (q[0] - r[0]),
                                    (q[1] - r[1]),
                                    (q[2] - r[2])};
                            handler.sendMessage(handler.obtainMessage());
                            double exPQQR = calcularArea(vectorPQ, vectorQR);
                            handler.sendMessage(handler.obtainMessage());
                            exPQPR = calcularArea(vectorPQ, vectorPR);
                            handler.sendMessage(handler.obtainMessage());
                            diferencia = Math.abs(NoExacta - exPQPR);
                            handler.sendMessage(handler.obtainMessage());
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                Toast.makeText(getApplicationContext(), "Ocurrio algun error", Toast.LENGTH_SHORT).show();
                            }
                            handler.sendMessage(handler.obtainMessage());
                            handler.sendMessage(handler.obtainMessage());
                        }
                    });
                    hilo.start();
                }catch(Exception e) {
                    Toast.makeText(getApplicationContext(), "Error al ingresar algun dato", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private double calcularArea(double[] PQ, double[] QR) {
        double i = (PQ[1] * QR[2] - QR[1] * PQ[2]);
        double j = (PQ[0] * QR[2] - QR[0] * PQ[2]);
        double k = (PQ[0] * QR[1] - QR[0] * PQ[1]);
        double resultado = Math.sqrt(Math.pow(i,2) + Math.pow(j,2) + Math.pow(k,2));
        return resultado;
    }

    public double calcularMagVector(double[] p1, double[] p2){
        double x3 = Math.pow((p1[0] - p2[0]), 2);
        double y3 = Math.pow((p1[1] - p2[1]), 2);
        double z3 = Math.pow((p1[2] - p2[2]), 2);
        double resultado = Math.sqrt(x3 + y3 + z3);
        return resultado;
    }
}
