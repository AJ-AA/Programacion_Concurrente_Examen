import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

//NP: 141350 ANTONIO JOSE ARENAL ARMESTO
//PROGRAMACION CONCURRENTE CONVOCATORIA ORDINARIA

class ParqueTematicoMonitores {

    //Declaracion de variables
    private static final int AFORO_MAXIMO = 100;

    private final Object aforoLock = new Object();
    private int aforoActual = 0;

    //Clase Atraccion
    private class Atraccion {
        private final Queue<Integer> cola = new LinkedList<>();
        private final Object atraccionLock = new Object();
        private final int aforoMaximo;
        private final String nombre;


        //Constructor de la atraccion
        public Atraccion(int aforoMaximo, String nombre) {
            this.aforoMaximo = aforoMaximo;
            this.nombre = nombre;
        }

        //Metodo para entrar con el codigo de acceso unico
        public void entrarCola(int codigoAcceso) throws InterruptedException {
            synchronized (atraccionLock) {
                while (cola.size() >= aforoMaximo) {
                    atraccionLock.wait();
                }
                cola.add(codigoAcceso);
                System.out.println("El visitante con código de acceso " + codigoAcceso + " se ha agregado a la cola de " + nombre + ".");
                atraccionLock.notifyAll();
            }
        }
        //Metodo para salir con el codigo de acceso unico

        public void salirCola(int codigoAcceso) {
            synchronized (atraccionLock) {
                cola.remove(codigoAcceso);
                System.out.println("El visitante con código de acceso " + codigoAcceso + " ha salido de " + nombre + ".");
                atraccionLock.notifyAll();
            }
        }
        //Metodo para comprobar si esta lleno el aforo
        public boolean estaLlena() {
            synchronized (atraccionLock) {
                return cola.size() >= aforoMaximo;
            }
        }
    }

    private final Atraccion MONTANA_RUSA = new Atraccion(20, "Montaña_Rusa");
    private final Atraccion CASA_ENCANTADA = new Atraccion(20, "Casa_Encantada");
    private final Atraccion COCHES_CHOQUE = new Atraccion(20, "Coches_de_Choque");

    //Clase visitante que extiende de Thread para la programacion concurrente

    class Visitante extends Thread {
        private final int codigoAcceso;

        public Visitante(int codigoAcceso) {
            this.codigoAcceso = codigoAcceso;
        }

        //Metodo run

        public void run() {
            try {
                synchronized (aforoLock) {
                    while (aforoActual >= AFORO_MAXIMO) {
                        aforoLock.wait();
                    }
                    aforoActual++;
                    System.out.println("El visitante con código de acceso " + codigoAcceso + " ha entrado al parque.");
                }


                Atraccion[] atracciones = {MONTANA_RUSA, CASA_ENCANTADA, COCHES_CHOQUE};
                shuffleArray(atracciones);


                for (Atraccion atraccion : atracciones) {
                    if (!atraccion.estaLlena()) {
                        atraccion.entrarCola(codigoAcceso);
                        atraccion.salirCola(codigoAcceso);
                        break;
                    }
                }

                synchronized (aforoLock) {
                    aforoActual--;
                    System.out.println("El visitante con código de acceso " + codigoAcceso + " ha salido del parque.");
                    aforoLock.notifyAll();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }


        private void shuffleArray(Atraccion[] array) {
            Random rnd = new Random();
            for (int i = array.length - 1; i > 0; i--) {
                int index = rnd.nextInt(i + 1);
                // Intercambiar
                Atraccion temp = array[index];
                array[index] = array[i];
                array[i] = temp;
            }
        }
    }


    //METODO MAIN

    public static void main(String[] args) {
        ParqueTematicoMonitores parque = new ParqueTematicoMonitores();
        for (int i = 1; i <= 30; i++) {
            if (i % 5 == 0) {
                try {
                    System.out.println("Entrando nuevos visitantes al parque temático...");
                    Thread.sleep(2000); //
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            Visitante visitante = parque.new Visitante(i);
            visitante.start();
        }
    }
}
