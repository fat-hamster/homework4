import java.util.Random;
import java.util.Scanner;

public class CrossGame {
    private int size;
    private int dots;
    private final Random rnd;
    private final Scanner sc;

    public CrossGame() {
        sc = new Scanner(System.in);
        rnd = new Random();
    }

    public void startGame() {
        startGame(3);
    }

    public void startGame(int size) {
        if(size < 3) {
            System.out.printf("Размер поля должен быть не меньше 3!");
            size = 3;
        }
        if(size > 20) {
            System.out.printf("И кто в это будет играть?!");
            size = 20;
        }
        this.size = size;
        if(size >= 3 && size <= 5) {
            dots = 3;
        }
        else if (size >= 6 && size <= 9) {
            dots = 4;
        }
        else {
            dots = 5;
        }

        mainLoop();
    }

    private void mainLoop() {

    }
}
