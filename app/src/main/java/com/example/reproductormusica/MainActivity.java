package com.example.reproductormusica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLOutput;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    //Declaro la funcion convertir que se ejecutara cuando se pulsen ambos botones
    public void convertir(int valorInicial, int valorFinal, double num){
        //Declaramos una array donde guardaremos los valores de conversion para mostrarlo en pantall
        String[] siglasJSG = {"bit","b","Kb","MB","GB","TB","PB","EB","ZB","YB","BB","GB"};


        //Declaro dos Toast para no permitir al usuario que me declare dos conversiones iguales o lo deje vacio
        if(valorInicial== -1||valorFinal== -1) {
            Toast.makeText(MainActivity.this, "Debes de seleccionar los dos valores de conversion", Toast.LENGTH_SHORT).show();
        }else if(valorInicial==valorFinal){
            Toast.makeText(MainActivity.this, "No se pueden convertir dos valores iguales", Toast.LENGTH_SHORT).show();
        }else{
            DecimalFormat formatoCientifico = new DecimalFormat("00000.00E0");
            String fc;
            int elevado=0;
            double resultado=0;
            String respuestaFinal;
            //Creo tres condicionales uno para el conversor de bits y los otros dos para diferenciar si valorInicial es mas grande o mas pequeño que valorFinal
           if(valorInicial ==0 || valorFinal==0){
               if(valorInicial>valorFinal){
                   elevado = valorInicial -1;
                   elevado = elevado * -1;
                   resultado = Math.pow(1024, elevado);
                   resultado = resultado / 8;
                   respuestaFinal = formatNumerosCientificos(resultado) + " " + siglasJSG[valorFinal];
                   respuestaJSG.setText(respuestaFinal);
               } else if (valorInicial<valorFinal) {
                   resultado = Math.pow(1024, valorFinal-1) * 8;
                   respuestaFinal = formatNumeros(resultado) + " " + siglasJSG[valorFinal];
                   respuestaJSG.setText(respuestaFinal);
               }
           } else if (valorInicial<valorFinal) {
               elevado = valorFinal - valorInicial;
               resultado = Math.pow(1024, elevado);
               respuestaFinal=formatNumeros(resultado) + " "+siglasJSG[valorFinal];
               respuestaJSG.setText(respuestaFinal);
           }else if(valorInicial>valorFinal) {
                elevado = valorInicial - valorFinal;
                elevado = elevado * -1;
                resultado = Math.pow(1024, elevado);
                respuestaFinal = formatNumerosCientificos(resultado) + " " + siglasJSG[valorFinal];
                respuestaJSG.setText(respuestaFinal);
           }
        }
    }

    public String formatNumeros(double numero){
        String resultadoFinal="";
        DecimalFormat df = new DecimalFormat("0.00");
        DecimalFormat dfCientifica = new DecimalFormat("0.00E0");

        if(numero < 9999999){
            resultadoFinal = df.format(numero);
        }else{
            resultadoFinal=dfCientifica.format(numero);
        }
        return resultadoFinal;
    }

    public String formatNumerosCientificos(double numero){
        String resultadoFinal="";
        DecimalFormat dfCientifico = new DecimalFormat("0.00E0");
        resultadoFinal = dfCientifico.format(numero);
        return resultadoFinal;
    }

    //Declaro todas las variables del layout
    private Spinner listaValores1JSG;
    private Spinner listaValores2JSG;
    private Button boton1JSG;
    private Button boton2JSG;
    private EditText inicioJSG;
    private EditText respuestaJSG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Parte del codigo donde se muestra la pantalla principal main
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Vinculamos los objetos declarados al id del main
        listaValores1JSG = findViewById(R.id.valor1);
        listaValores2JSG = findViewById(R.id.valor2);
        boton1JSG = findViewById(R.id.convertir1);
        boton2JSG = findViewById(R.id.convertir2);
        inicioJSG = findViewById(R.id.numeroInicial);
        respuestaJSG = findViewById(R.id.numeroFinal);
        respuestaJSG.setEnabled(false);

        //Creamos una array de valores y los insertamos en los spinners correspondientes
        String [] valores = {"","Bits","Byte","Kilobyte","Megabyte","Gigabyte","Terabyte","Petabyte","Exabyte","Zetabyte","Yottabyte","Brontobyte","Geopbyte"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item, valores);
        ArrayAdapter<String> adaptador2= new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, valores);
        listaValores1JSG.setAdapter(adaptador);
        listaValores2JSG.setAdapter(adaptador2);


        //Ejecutamos un listener onclick de cada uno de los botones
        boton1JSG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rs = listaValores1JSG.getSelectedItemPosition() -1;
                int in = listaValores2JSG.getSelectedItemPosition() -1;
                String valorInicio = "";
                valorInicio = inicioJSG.getText().toString();
                //Generamos un if donde en caso de que valorInicio este vacio me ponga un setError en el EditText
                if(valorInicio.isEmpty()){
                    inicioJSG.setError("Debes de introducir un valor valido");
                }else {
                    //Parseamos el double e iniciamos la funcion donde me dara el resultado en el EditText respuestaJSG
                    double numero;
                    numero=Double.parseDouble(valorInicio);
                    convertir(in, rs, numero);
                }
            }
        });

        boton2JSG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //En esta funcion hacemos lo mismo que la anterior pero modificando el orden de las variables en la funcion
                    int in = listaValores1JSG.getSelectedItemPosition() -1;
                    int rs = listaValores2JSG.getSelectedItemPosition() -1;
                    String valorInicio = "";
                    valorInicio = inicioJSG.getText().toString();
                    if(valorInicio.isEmpty()){
                        inicioJSG.setError("Debes de introducir un valor valido");
                    }else{
                        int numero;
                        numero=Integer.parseInt(valorInicio);
                        convertir(in, rs, numero);
                    }



            }
        });

    }
}