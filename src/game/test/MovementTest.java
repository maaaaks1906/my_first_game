package game.test;

import java.awt.*;
import java.awt.event.AWTEventListener;

public class MovementTest {
    public static void main(String[] args) {
        Toolkit.getDefaultToolkit().addAWTEventListener(
                new AWTEventListener() {
                    @Override
                    public void eventDispatched(AWTEvent event) {
                        System.out.println(event+"lol");
                    }
                }, -1
        );
//        ScreenManager screenManager = new ScreenManager();
//
//        float fifa = (float) Math.random();
//        System.out.println(fifa);
//
//        System.out.println((float) Math.random() -0.5f);
//        Scanner scanner = new Scanner(System.in);
//        int lifes = 5;
//
//        System.out.println("Podaj liczbe");
//        int number = scanner.nextInt();
//
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//
//        while (lifes > 0) {
//
//            System.out.println("Zgadnij liczbę");
//            int guessedNumber = scanner.nextInt();
//            if (guessedNumber < number) {
//                System.out.println("Podana liczba jest za mala");
//                lifes--;
//                System.out.println("Pozostało żyć: " + lifes);
//                System.out.println();
//            } else if (guessedNumber > number) {
//                System.out.println("Podana liczba jest za duza");
//                lifes--;
//                System.out.println("Pozostało żyć: " + lifes);
//                System.out.println();
//            } else {
//                System.out.println("Gratulacje wygrałeś!");
//                lifes = 0;
//            }
//        }
//
//        if (lifes == 0) {
//            System.out.println("Koniec Gry");
//            System.out.println("Liczba to: " + number);
//        }


    }

}
