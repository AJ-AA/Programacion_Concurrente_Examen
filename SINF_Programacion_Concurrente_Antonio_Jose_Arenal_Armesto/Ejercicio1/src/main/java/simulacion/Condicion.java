package main.java.simulacion;

import java.util.Random;

//NP: 141350 ANTONIO JOSE ARENAL ARMESTO
//PROGRAMACION CONCURRENTE CONVOCATORIA ORDINARIA


//Clase condicion
public class Condicion {

    //Declaracion de variables y atributos
    private final TipoCondicion tipo;
    private final int modificador;
    private static final Random random = new Random();

    //Enumeracion de condiciones posibles para el programa
    public enum TipoCondicion {
        NINGUNA, VIENTO_FUERTE, SUPERFICIE_IRREGULAR, MAGO_DEMONIACO
    }

    //Metodo condicion
    public Condicion() {
        this.tipo = TipoCondicion.values()[random.nextInt(TipoCondicion.values().length)];
        this.modificador = calcularModificador();
    }

    //Metodo para calcular el modificar segun lo que salga en el switch de forma aleatoria
    private int calcularModificador() {
        int modificador;
        switch (tipo) {
            case VIENTO_FUERTE:
                modificador = -1 + random.nextInt(3); // -1, 0, o 1
                break;
            case SUPERFICIE_IRREGULAR:
                modificador = -2 + random.nextInt(5); // -2, -1, 0, 1, o 2
                break;
            case MAGO_DEMONIACO:
                modificador = 2 + random.nextInt(4); // 2, 3, 4, o 5
                break;
            case NINGUNA:
            default:
                modificador = 0;
                break;
        }
        return modificador;
    }

    //Metodos de acceso
    public int obtenerModificador() {
        return modificador;
    }

    public TipoCondicion obtenerTipo() {
        return tipo;
    }

    public TipoCondicion getTipo() {
        return tipo;
    }

    public int getModificador() {
        return modificador;
    }
}
