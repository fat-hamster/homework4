import java.util.Random;
import java.util.Scanner;

public class CrossGame {
    private int size;
    private int dots;
    private final Random rnd;
    private final Scanner sc;
    private boolean exit;
    private char[][] field;
    private final char DOT_EMPTY = '•';
    private final char DOT_X = 'X';
    private final char DOT_O = 'O';

    public CrossGame() {
        sc = new Scanner(System.in);
        rnd = new Random();
        exit = false;
    }

    public void startGame() {
        startGame(3);
    }

    public void startGame(int size) {
        initGame(size);
        initField();
        mainLoop();
    }

    private void initGame(int size) {
        if(size < 3) {
            System.out.println("Размер поля должен быть не меньше 3!");
            size = 3;
        }
        if(size > 20) {
            System.out.println("И кто в это будет играть?!");
            size = 20;
        }
        this.size = size;
        if(size <= 5) {
            dots = 3;
        }
        else if (size <= 9) {
            dots = 4;
        }
        else {
            dots = 5;
        }
        field = new char[size][size];
    }

    private void initField() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j] = DOT_EMPTY;
            }
        }
    }

    private void mainLoop() {
//        do {
        printGreetings();
        printField();
        turnPlayer();
        isWin(char ch);
        turnComputer();
//        }while (!exit);
    }

    private void printGreetings() {
        System.out.println("Крестики-Нолики v.0.0.1");
        System.out.println("Для хода введите координаты по горизонтали (Х) и вертикали (Y)");
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

    private void turnPlayer() {
        int x, y;
        do {
            x = tryInput("X") - 1;
            y = tryInput("Y") - 1;

            if(field[y][x] != DOT_EMPTY) {
                System.out.println("Клетка занята! Попробуйте еще раз");
                continue;
            } else {
                field[y][x] = DOT_X;
                printField();
                break;
            }
        } while (true);
    }

    private int tryInput(String s) {
        int input;
        do {
            System.out.print(s + " = ");
            if (sc.hasNextInt()) {
                input = sc.nextInt();
                if (input < 1 || input > size) {
                    System.out.println("Координата должна быть в преде от 1 до " + size);
                    continue;
                }
            } else {
                System.out.println("Необходимо ввести число!");
                sc.next();
                continue;
            }
            return input;
        } while (true);
    }

    private boolean isWin(char ch) {
        return false;
    }

    private void turnComputer() {
        // мдя......
    }
}
