package main.java.simulacion;

import java.util.Random;

//NP: 141350 ANTONIO JOSE ARENAL ARMESTO
//PROGRAMACION CONCURRENTE CONVOCATORIA ORDINARIA


//Clase Dado que implementa Runnable
public class Dado implements Runnable {

    //Declaracion de variables y atributos
    private int resultado;
    private final Condicion condicion;

    private static final Random random = new Random();

    public Dado(Condicion condicion) {
        this.condicion = new Condicion();
    }


    //Metodo run
    @Override
    public void run() {

        int resultadoBase = lanzarDado();

        int modificador = aplicarCondicion();

        int resultadoFinal = resultadoBase + modificador;

        if (resultadoFinal > 6) {
            resultadoFinal = (resultadoFinal % 6) == 0 ? 6 : resultadoFinal % 6;
        } else if (resultadoFinal < 1) {
            resultadoFinal = resultadoFinal + 6;
        }

        this.resultado = resultadoFinal;
    }


    //Metodo para lanzar un dado
    private int lanzarDado() {

        return 1 + random.nextInt(6);
    }

    //Metodo para aplicar la condicion coon dicho modificador
    private int aplicarCondicion() {

        return condicion.obtenerModificador();
    }

    //Metodo para devolver el tipo de condicion
    public Condicion.TipoCondicion getTipoCondicion() {

        return this.condicion.obtenerTipo();
    }

    //Metodos de acceso
    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

    public Condicion getCondicion() {
        return condicion;
    }

    public int getResultado() {
        return resultado;
    }
}
