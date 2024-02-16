package main.java.simulacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//NP: 141350 ANTONIO JOSE ARENAL ARMESTO
//PROGRAMACION CONCURRENTE CONVOCATORIA ORDINARIA


//Clase simulador de dados
public class SimuladorDeDados {


    //Metodo tiradaDados que devuelve una lista de los resultados
    public List<ResultadoDado> tiradaDados(int numDados) {
        Thread[] hilos = new Thread[numDados];
        Dado[] dados = new Dado[numDados];
        List<ResultadoDado> resultados = new ArrayList<>();

        for (int i = 0; i < numDados; i++) {
            Condicion condicion = new Condicion();
            dados[i] = new Dado(condicion);
            hilos[i] = new Thread(dados[i]);
            hilos[i].start();
        }

        for (int i = 0; i < numDados; i++) {
            try {
                hilos[i].join();
                resultados.add(new ResultadoDado(dados[i].getResultado(), dados[i].getTipoCondicion(), true));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Hilo interrumpido.");
            }
        }

        return resultados;
    }


    //Metodo estadistico para calcular el promedio
    public double calcularPromedio(List<ResultadoDado> resultados) {
        if (resultados.isEmpty()) return 0;

        double suma = 0;
        for (ResultadoDado resultado : resultados) {
            suma += resultado.getResultado();
        }
        return suma / resultados.size();
    }

    //Metodo estadistico para calcular la moda
    public int calcularModa(List<ResultadoDado> resultados) {
        Map<Integer, Integer> frecuencia = new HashMap<>();
        for (ResultadoDado resultado : resultados) {
            int valor = resultado.getResultado();
            frecuencia.put(valor, frecuencia.getOrDefault(valor, 0) + 1);
        }

        int moda = -1;
        int maxFrecuencia = 0;
        for (Map.Entry<Integer, Integer> entrada : frecuencia.entrySet()) {
            if (entrada.getValue() > maxFrecuencia) {
                moda = entrada.getKey();
                maxFrecuencia = entrada.getValue();
            }
        }
        return moda;
    }

    //Metodo estadistico para calcular la calcularDesviacionEstandar
    public double calcularDesviacionEstandar(List<ResultadoDado> resultados) {
        if (resultados.isEmpty()) return 0;

        double promedio = calcularPromedio(resultados);
        double sumaCuadrados = 0;
        for (ResultadoDado resultado : resultados) {
            sumaCuadrados += Math.pow(resultado.getResultado() - promedio, 2);
        }
        return Math.sqrt(sumaCuadrados / resultados.size());
    }

}
