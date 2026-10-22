package AWT;

import java.awt.*;

public abstract class Ball
        implements Runnable {
    protected int x, y;
    protected int vx, vy;
    protected int speed;

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
        vx = (int) (Math.random() * 7) - 3;
        vy = (int) (Math.random() * 7) - 3;
        speed = 20 + (int) (Math.random() * 11);
    }

    public void draw(Graphics g) {
        g.fillOval(x-5, y-5, 10, 10);
    }

    public abstract boolean collidingX();
    public abstract boolean collidingY();
    public abstract void wakeUp();
}
