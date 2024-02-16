package main.java.ui;

import main.java.simulacion.SimuladorDeDados;
import main.java.simulacion.ResultadoDado;

import javax.swing.*;
import java.util.List;

//NP: 141350 ANTONIO JOSE ARENAL ARMESTO
//PROGRAMACION CONCURRENTE CONVOCATORIA ORDINARIA

public class InterfazUsuario {

    //Metodo Main

    public static void main(String[] args) {

        //Pedimos por pantalla el numero de dados
        String input = JOptionPane.showInputDialog("Ingrese el número de dados a lanzar:");
        int numDados;


        //Comprobamos que se introduce un numero valido
        try {
            numDados = Integer.parseInt(input);
            if (numDados <= 0) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un número mayor que cero.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un número entero válido.");
            return;
        }

        SimuladorDeDados simulador = new SimuladorDeDados();
        List<ResultadoDado> resultados = simulador.tiradaDados(numDados);

        StringBuilder mensaje = new StringBuilder("Resultados de la tirada de dados:\n");
        for (int i = 0; i < resultados.size(); i++) {
            mensaje.append("Dado ").append(i + 1).append(": ")
                    .append(resultados.get(i).toString()).append("\n");
        }

        //Se muestran los resultados
        mensaje.append("\nAnálisis de los resultados:\n");
        mensaje.append("Promedio: ").append(simulador.calcularPromedio(resultados)).append("\n");
        mensaje.append("Moda: ").append(simulador.calcularModa(resultados)).append("\n");
        mensaje.append("Desviación estándar: ").append(simulador.calcularDesviacionEstandar(resultados)).append("\n");

        mensaje.append("\nConteo de resultados:\n");
        for (int i = 1; i <= 6; i++) {
            int count = 0;
            for (ResultadoDado resultado : resultados) {
                if (resultado.getResultado() == i) {
                    count++;
                }
            }
            mensaje.append("Resultado ").append(i).append(": ").append(count).append(" veces\n");
        }

        JOptionPane.showMessageDialog(null, mensaje.toString());
    }
}
