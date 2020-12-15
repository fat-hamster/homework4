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
        if (size < 3) {
            System.out.println("Размер поля должен быть не меньше 3!");
            size = 3;
        }
        if (size > 20) {
            System.out.println("И кто в это будет играть?!");
            size = 20;
        }
        this.size = size;
        if (size <= 5) {
            dots = 3;
        } else if (size <= 9) {
            dots = 4;
        } else {
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
            turnPlayer();
            if (isWin(DOT_X)) {
                exit = true;
                System.out.println("Вы выиграли!!!!");
                continue;
            }
            if (exit = noWin()) {
                continue;
            }
            turnComputer();
            if (isWin(DOT_O)) {
                printField();
                exit = true;
                System.out.println("Выиграл компьютер :(");
                continue;
            }
            exit = noWin();
        } while (!exit);
    }

    private boolean noWin() {
        if (all_turns <= 0) {
            System.out.println("Ничья. Ходов не осталось.");
            return true;
        }
        return false;
    }

    private void printGreetings() {
        System.out.println("Для хода введите координаты по горизонтали (Х) и вертикали (Y)");
    }

    private void printField() {
        System.out.print(" *");
        for (int i = 0; i < size; i++) {
            System.out.printf("%3d", (i + 1));
        }
        System.out.println();
        for (int i = 0; i < size; i++) {
            System.out.printf("%3d", i + 1);
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

            if (field[y][x] != DOT_EMPTY) {
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
            last_y = win_y;
            last_x = win_x;
            field[win_y][win_x] = DOT_O;
            return true;
        }
        if(lookVerticalWin(symbol)) {
            last_y = win_y;
            last_x = win_x;
            field[win_y][win_x] = DOT_O;
            return true;
        }
        if(lookDiagonalWin(symbol)) {
            last_y = win_y;
            last_x = win_x;
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
        boolean start = false;
        boolean end = false;
        boolean middle = false;
        int pos_x = -1;
        char con;
        if(symbol == DOT_O) {
            con = DOT_X;
        } else {
            con = DOT_O;
        }

        for (int y = 0; y < size; y++) {
            if(isHorizontalFull(y) || isHorizontalEmpty(y)) {
                continue;
            }
            for (int x = 0; x < size; x++) {
                if(field[y][x] == symbol) {
                    count++;
                    if(x == 0) {
                        start = true;
                        continue;
                    } else {
                        //start = false;
                    }
                    if(x == size - 1) {
                        end = true;
                    }
                    if(count > 1 && !start) {
                        middle = true;
                    }
                }
                if(field[y][x] == con) {
                    count = 0;
                    start = false;
                    end = false;
                    middle = false;
                    pos_x = -1;
                    continue;
                }
                if(middle && count == dots - 1) {
                    if(field[y][x - 1] == DOT_EMPTY) {
                        win_y = y;
                        win_x = x - 1;
                        return true;
                    }
                }
                if(field[y][x] == DOT_EMPTY && start) {
                    if(count == dots - 1) {
                        win_x = x;
                        win_y = y;
                        return true;
                    }
                }
                if(end && count == dots - 1 && pos_x == x - (dots - 1)) {
                    win_y = y;
                    win_x = pos_x;
                    return true;
                }
                if(count == (dots - 1)) {
                    if(field[y][x - (count - 1)] == DOT_EMPTY) {
                        win_y = y;
                        win_x = x - count;
                        return true;
                    }
                    if(field[y][x + 1] == DOT_EMPTY) {
                        win_x = x + 1;
                        win_y = y;
                        return true;
                    }
                }
                if(field[y][x] == DOT_EMPTY) {
                    pos_x = x;
                }
            count = 0;
            }
        }
        return false;
    }

    private boolean isHorizontalEmpty(int y) {
        for (int i = 0; i < size; i++) {
            if(field[y][i] != DOT_EMPTY) {
                return false;
            }
        }
        return true;
    }

    private boolean isHorizontalFull(int y) {
        for (int i = 0; i < size; i++) {
            if(field[y][i] == DOT_EMPTY) {
                return false;
            }
        }
        return true;
    }

    private boolean lookVerticalWin(char symbol) {
        int count = 0;
        boolean start = false;
        boolean end = false;
        boolean middle = false;
        int pos_y = -1;

        for (int x = 0; x < size; x++) {
            if(isVerticalFull(x) || isVerticalEmpty(x)) {
                continue;
            }
            for (int y = 0; y < size; y++) {
                if(field[y][x] == symbol) {
                    count++;
                    if(y == 0) {
                        start = true;
                        continue;
                    }
                    if(y == size - 1) {
                        end = true;
                    }
                    if(count > 1) {
                        middle = true;
                    }
                }
                if(middle) {
                    if(field[y - 1][x] == DOT_EMPTY) {
                        win_y = y - 1;
                        win_x = x;
                        return true;
                    }
                }
                if(field[y][x] == DOT_EMPTY && start) {
                    if(count == dots - 1) {
                        win_x = x;
                        win_y = y;
                        return true;
                    }
                }
                if(end && count == dots - 1 && pos_y == y - (dots - 1)) {
                    win_y = pos_y;
                    win_x = x;
                    return true;
                }
                if(count == (dots - 1)) {
                    if(field[y - (count - 1)][x] == DOT_EMPTY) {
                        win_y = y - (count - 1);
                        win_x = x;
                        return true;
                    }
                    if(field[y + 1][x] == DOT_EMPTY) {
                        win_x = x;
                        win_y = y + 1;
                        return true;
                    }
                }
                if(field[y][x] == DOT_EMPTY) {
                    pos_y = y;
                }
            }
            count = 0;
        }
        return false;
    }

    private boolean isVerticalEmpty(int x) {
        for (int y = 0; y < size; y++) {
            if(field[y][x] != DOT_EMPTY) {
                return false;
            }
        }
        return true;
    }

    private boolean isVerticalFull(int x) {
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

        // Для диагоналей \
        for (int y = size - dots; y >= 0; y--) {
            if(isDiagonalFull(y, 0) || isDiagonalEmpty(y, 0)) {
                continue;
            }
            for (int i = 0; i+y < size; i++) {
                if(field[y+i][i] == symbol) {
                    count++;
                } else {
                    if(field[y+i][i] == DOT_EMPTY && !possible) {
                        count++;
                        possible = true;
                        win_x = i;
                        win_y = y+i;
                    } else {
                        count = 0;
                        possible = false;
                        win_x = -1;
                        win_y = -1;
                    }
                }
                if(count == dots) {
                    return true;
                }
            }
        }
        // для диагонали /
        pos = 0;
        count = 0;
        possible = false;
        for (int y = size - dots; y >= 0; y--) {
            if(isDiagonalFull(y, 0) || isDiagonalEmpty(y, 0)) {
                continue;
            }
            for (int i = 0; i+y < size; i++) {
                if(field[y+i][i] == symbol) {
                    count++;
                } else {
                    if(field[y+i][i] == DOT_EMPTY && !possible) {
                        count++;
                        possible = true;
                        win_x = i;
                        win_y = y+i;
                    } else {
                        count = 0;
                        possible = false;
                        win_x = -1;
                        win_y = -1;
                    }
                }
                if(count == dots) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isDiagonalFull(int y, int x) {
        for (int i = 0; i < dots; i++) {
            if(field[y + i][x + i] == DOT_EMPTY) {
                return false;
            }
        }
        return true;
    }

    private boolean isDiagonalEmpty(int y, int x) {
        for (int i = 0; i < dots; i++) {
            if(field[y + i][x + i] != DOT_EMPTY) {
                return false;
            }
        }
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
        last_x = x;
        last_y = y;
    }
}
