// Использованы материалы с сайта:
// https://www.dokwork.ru/2012/11/tictactoe.html

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
    private int last_x;
    private int last_y;
    private int all_turns;
    private int win_x;
    private int win_y;

    public CrossGame() {
        sc = new Scanner(System.in);
        rnd = new Random();
        exit = false;
        last_y = 0;
        last_x = 0;
        all_turns = 0;
        win_x = -1;
        win_y = -1;
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
        all_turns = size * size;
    }

    private void initField() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j] = DOT_EMPTY;
            }
        }
    }

    private void mainLoop() {
        do {
            printGreetings();
            printField();
            if(all_turns <= 0) {
                System.out.println("Ничья. Ходов не осталось.");
                exit = true;
                continue;
            }
            turnPlayer();
            if(isWin(DOT_X)) {
                exit = true;
                System.out.println("Вы выиграли!!!!");
                continue;
            }
            if(all_turns <= 0) {
                System.out.println("Ничья. Ходов не осталось.");
                exit = true;
                continue;
            }
            turnComputer();
            if(isWin(DOT_O)) {
                exit = true;
            }
        }while (!exit);
    }

    private void printGreetings() {
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
        System.out.println("Ход игрока");
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
            }

            last_x = x;
            last_y = y;
            break;
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
        all_turns--;
        return checkHorizontal(ch) || checkVertical(ch) || checkDiagonal(ch);
    }

    private void turnComputer() {
        System.out.println("Ход компьютера");
        // есть выигрышный ход?
        if(lookWinTurn(DOT_O)) {
            return;
        }
        // нужно блокировать выигрыш человека?
        if(lookWinTurn(DOT_X)) {
            return;
        }
        // ход
        rndTurn();
    }

    private boolean lookWinTurn(char symbol) {
        if(lookHorizontalWin(symbol)) {
            field[win_y][win_x] = DOT_O;
            return true;
        }
        if(lookVerticalWin(symbol)) {
            field[win_y][win_x] = DOT_O;
            return true;
        }
        if(lookDiagonalWin(symbol)) {
            field[win_y][win_x] = DOT_O;
            return true;
        }
        return false;
    }

    boolean checkHorizontal(char symbol) {
        int count = 0;

        int i = last_x;
        while (i < size && field[last_y][i] == symbol) {
            count++;
            i++;
        }
        i = last_x - 1;
        while (i >= 0 && field[last_y][i] == symbol) {
            count++;
            i--;
        }
        if (count >= dots) {
            return true;
        }

        return false;
    }

    private boolean checkVertical(char symbol) {
        int count = 0;

        int i = last_y;
        while (i < size && field[i][last_x] == symbol) {
            count++;
            i++;
        }
        i = last_y - 1;
        while (i >= 0 && field[i][last_x] == symbol) {
            count++;
            i--;
        }
        if(count >= dots) {
            return true;
        }

        return false;
    }

    boolean checkDiagonal(char symbol) {
        int count = 0;
        int i = last_y;
        int j = last_x;
        // диагональ \ вниз
        while (i < size && j < size && field[i][j] == symbol) {
            count++;
            i++;
            j++;
        }
        i = last_y - 1;
        j = last_x - 1;
        // диагональ \ вверх
        while (i >= 0 && j >= 0 && field[i][j] == symbol) {
            count++;
            i--;
            j--;
        }
        if(count >= dots) {
            return true;
        }
        // водораздел :)
        count = 0;
        i = last_y;
        j = last_x;
        // диагональ / вниз
        while(i < size && j >= 0 && field[i][j] == symbol) {
            count++;
            i++;
            j--;
        }
        i = last_y - 1;
        j = last_x + 1;
        // диагональ / вверх
        while (i >= 0 && j < size && field[i][j] == symbol) {
            count++;
            i--;
            j++;
        }
        if(count >= dots) {
            return true;
        }
        return false;
    }

    private boolean lookHorizontalWin(char symbol) {
        int count = 0;
        int pos = 0;
        boolean possible = false;

        for (int i = 0; i < size; i++) {
            if(horizontalFoul(i)) {
                continue;
            }
            for (int j = pos; j < size; j++) {
                if(field[i][j] == symbol) {
                    count++;
                } else {
                    if(field[i][j] == DOT_EMPTY && !possible) {
                        possible = true;
                        win_x = j;
                        count++;
                    } else {
                        count = 0;
                        possible = false;
                        win_x = -1;
                        win_y = -1;
                    }
                }
                if(count == dots) {
                    win_y = i;
                    return true;
                }
            }
            pos++;
            possible = false;
        }
        return false;
    }

    private boolean horizontalFoul(int y) {
        for (int i = 0; i < size; i++) {
            if(field[y][i] == DOT_EMPTY) {
                return false;
            }
        }
        return true;
    }

    private boolean lookVerticalWin(char symbol) {
        int count = 0;
        int pos = 0;
        boolean possible = false;

        for (int i = 0; i < size; i++) {
            if(verticalFoul(i)) {
                continue;
            }
            for (int j = pos; j < size; j++) {
                if(field[j][i] == symbol) {
                    count++;
                } else {
                    if(field[j][i] == DOT_EMPTY && !possible) {
                        possible = true;
                        win_x = i;
                        count++;
                    } else {
                        count = 0;
                        possible = false;
                        win_x = -1;
                        win_y = -1;
                    }
                }
                if(count == dots) {
                    win_y = j;
                    return true;
                }
            }
            pos++;
            possible = false;
        }
        return false;
    }

    private boolean verticalFoul(int x) {
        for (int i = 0; i < size; i++) {
            if(field[i][x] == DOT_EMPTY) {
                return false;
            }
        }
        return true;
    }

    private boolean lookDiagonalWin(char symbol) {
        int pos = 0;
        int count = 0;
        boolean possible = false;

        
        return false;
    }

    private boolean diagonalFoul(int x, int y) {

        return true;
    }

    private void rndTurn() {
        int x = 0;
        int y = 0;
        boolean exit = false;

        do {
            x = rnd.nextInt(size);
            y = rnd.nextInt(size);
            if(field[y][x] == DOT_EMPTY) {
                field[y][x] = DOT_O;
                exit = true;
            }
        }while (!exit);
    }
}
