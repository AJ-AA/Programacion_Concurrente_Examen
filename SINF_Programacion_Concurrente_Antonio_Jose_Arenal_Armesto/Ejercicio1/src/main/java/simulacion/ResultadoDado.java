package main.java.simulacion;

//NP: 141350 ANTONIO JOSE ARENAL ARMESTO
//PROGRAMACION CONCURRENTE CONVOCATORIA ORDINARIA


//Clase ResutadoDado
public class ResultadoDado {

    //Declaracion de variables y atributos
    private final int resultado;
    private final Condicion.TipoCondicion tipoCondicion;
    private final boolean afectado;


    //Metodo constructor
    public ResultadoDado(int resultado, Condicion.TipoCondicion tipoCondicion, boolean afectado) {
        this.resultado = resultado;
        this.tipoCondicion = tipoCondicion;
        this.afectado = afectado;
    }

    //Metodo toString
    @Override
    public String toString() {
        String afectadoStr = afectado ? "Afectado por " + tipoCondicion : "No afectado";
        return resultado + " (" + afectadoStr + ")";
    }

    //Metodos de acceso
    public Condicion.TipoCondicion getTipoCondicion() {
        return tipoCondicion;
    }

    public boolean isAfectado() {
        return afectado;
    }

    public int getResultado() {
        return resultado;
    }

}
