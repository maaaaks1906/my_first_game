package game.test;

public class Main1 {
    public static void main(String[] args) {
        short shot = 16;
        System.out.printf(String.valueOf(shot & 0xff));
        int result = shot & 0xff;
        System.out.println(result);
    }
}
