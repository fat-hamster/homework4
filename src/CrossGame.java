import java.util.Random;
import java.util.Scanner;

public class CrossGame {
    private int size;
    private int dots;
    private final Random rnd;
    private final Scanner sc;
    private boolean exit;
    private char[][] field;

    public CrossGame() {
        sc = new Scanner(System.in);
        rnd = new Random();
        exit = false;
    }

    public void startGame() {
        startGame(3);
    }

    public void startGame(int size) {
        if(size < 3) {
            System.out.println("Размер поля должен быть не меньше 3!");
            size = 3;
        }
        if(size > 20) {
            System.out.println("И кто в это будет играть?!");
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
        field = new char[size][size];

        initField();
        mainLoop();
    }

    private void initField() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j] = '.';
            }
        }
    }

    private void mainLoop() {
//        do {
            printField();
//        }while (!exit);
    }

    private void printField() {
        System.out.print(" *");
        for (int i = 0; i < size; i++) {
            System.out.printf("%3d", (i+1));
        }
        System.out.println();
        for (int i = 0; i < size; i++) {
            System.out.printf("%3d", i+1);
            for (int j = 0; j < size; j++) {
                System.out.print(" " + field[i][j] + " ");
            }
            System.out.println();
        }
    }
}
