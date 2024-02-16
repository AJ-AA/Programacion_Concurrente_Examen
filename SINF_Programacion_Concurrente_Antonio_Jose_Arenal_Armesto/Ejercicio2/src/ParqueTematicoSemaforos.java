import java.util.concurrent.Semaphore;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;

//NP: 141350 ANTONIO JOSE ARENAL ARMESTO
//PROGRAMACION CONCURRENTE CONVOCATORIA ORDINARIA

class ParqueTematicoSemaforos {

    //Declaracion de puertas, aforo maximo del parque y de cada atraccion
    private static final int NUMERO_PUERTAS = 3;
    private static final int AFORO_MAXIMO = 100;
    private static final int AFORO_ATRACCION = 20;


    //Semaforos y colas para controlar el acceso a las diferentes atracciones.
    private static final Semaphore semaforoPuertas = new Semaphore(NUMERO_PUERTAS, true);
    private static final Semaphore semaforoAforoParque = new Semaphore(AFORO_MAXIMO, true);

    private static final Semaphore semaforoMontanaRusa = new Semaphore(AFORO_ATRACCION, true);
    private static final Queue<Integer> colaMontanaRusa = new LinkedList<>();

    private static final Semaphore semaforoCasaEncantada = new Semaphore(AFORO_ATRACCION, true);
    private static final Queue<Integer> colaCasaEncantada = new LinkedList<>();

    private static final Semaphore semaforoCochesChoque = new Semaphore(AFORO_ATRACCION, true);
    private static final Queue<Integer> colaCochesChoque = new LinkedList<>();


    //Clase visitante que extiende de Thread
    static class Visitante extends Thread {
        private final int codigoAcceso;

        public Visitante(int codigoAcceso) {
            this.codigoAcceso = codigoAcceso;
        }

        //Metodo Run
        public void run() {
            try {
                semaforoPuertas.acquire();
                System.out.println("El visitante con código de acceso " + codigoAcceso + " ha entrado al parque.");

                semaforoAforoParque.acquire();

                Atraccion[] atracciones = {Atraccion.Montaña_Rusa, Atraccion.Casa_Encantada, Atraccion.Coches_de_Choque};
                shuffleArray(atracciones);

                // Intentar ingresar a cada atracción en orden aleatorio
                for (Atraccion atraccion : atracciones) {
                    if (atraccion.intentarEntrar(codigoAcceso)) {
                        Thread.sleep(2000);
                        atraccion.salir(codigoAcceso);
                        break;
                    }
                }

                // Salida del parque
                semaforoPuertas.release();
                semaforoAforoParque.release();
                System.out.println("El visitante con código de acceso " + codigoAcceso + " ha salido del parque.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Método para mezclar aleatoriamente un array
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

    enum Atraccion {
        Montaña_Rusa(semaforoMontanaRusa, colaMontanaRusa),
        Casa_Encantada(semaforoCasaEncantada, colaCasaEncantada),
        Coches_de_Choque(semaforoCochesChoque, colaCochesChoque);

        private final Semaphore semaforo;
        private final Queue<Integer> cola;

        Atraccion(Semaphore semaforo, Queue<Integer> cola) {
            this.semaforo = semaforo;
            this.cola = cola;
        }

        //Metodo para que el visitante se intente entrar con su codigo de acceso, puede dar una excepcion de no ser posible
        public boolean intentarEntrar(int codigoAcceso) throws InterruptedException {
            semaforo.acquire();
            synchronized (cola) {
                if (cola.size() < AFORO_ATRACCION) {
                    cola.add(codigoAcceso);
                    System.out.println("El visitante con código de acceso " + codigoAcceso + " se ha agregado a la cola de " + this.name() + ".");
                    return true;
                } else {
                    semaforo.release();
                    return false;
                }
            }
        }

        //Metodo salir con el codigo de Acceso unico
        public void salir(int codigoAcceso) {
            synchronized (cola) {
                cola.remove(codigoAcceso);
                System.out.println("El visitante con código de acceso " + codigoAcceso + " ha salido de " + this.name() + ".");
                semaforo.release();
            }
        }
    }

    //Metodo Main

    public static void main(String[] args) {
        for (int i = 1; i <= 30; i++) {
            Visitante visitante = new Visitante(i);
            visitante.start();
        }
    }
}
