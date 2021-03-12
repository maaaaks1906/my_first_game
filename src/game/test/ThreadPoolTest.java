package game.test;

import game.util.ThreadPool;

public class ThreadPoolTest {
    public static void main(String[] args) {

        // Utworzenie puli w�tk�w:
        ThreadPool threadPool = new ThreadPool(4);
        System.out.println("cyc");

        // Uruchomienie przyk�adowych w�tk�w:
        for (int i=0; i<6; i++) {
            threadPool.runTask(createTask(i));
        }

        // Zamkni�cie puli i oczekiwanie na zako�czenie wszystkich zada�.
        threadPool.join();
    }


    /**
     Tworzy prosty obiekt Runnable drukuj�cy ID i oczekuj�cy 500
     milisekund; nast�pnie ponownie drukowany jest identyfikator zadania.
     */
    private static Runnable createTask(final int taskID) {
        return new Runnable() {
            public void run() {
                System.out.println("Zadanie " + taskID + ": uruchomione");

                // Symulowanie d�ugo dzia�aj�cego zadania:
                try {
                    Thread.sleep(2000);
                }
                catch (InterruptedException ex) { }

                System.out.println("Zadanie " + taskID + ": koniec");
            }
        };
    }
}
