package AWT;

import java.awt.*;

public abstract class Ball
        implements Runnable {
    protected int x, y;
    protected int vx, vy;
    protected int speed;
    protected Thread thread;

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
        vx = -3;
        vy = 3;
        speed = 25;
        thread = new Thread(this);
        thread.start();
    }

    public void draw(Graphics g) {
        g.fillOval(x-5, y-5, 10, 10);
    }

    @Override
    public void run() {
        while (true) {
            x += vx;
            y += vy;
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
