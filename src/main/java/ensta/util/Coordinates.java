package ensta.util;

public class Coordinates {
    private int x;
    private int y;

    public Coordinates(int ax, int ay) {
        this.x = ax;
        this.y = ay;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Coordinates() {
        this.x = 0;
        this.y = 0;
    }
}
