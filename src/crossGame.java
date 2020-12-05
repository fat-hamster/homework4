public class crossGame {
    protected int size;
    protected int dots;

    public crossGame() {

    }

    public void startGame(int size) {
        this.size = size;
    }

    public void startGame() {
        startGame(3);
    }
}
