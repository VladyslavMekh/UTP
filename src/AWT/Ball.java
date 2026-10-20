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
        vx = (int) (Math.random() * 7) - 3;
        vy = (int) (Math.random() * 7) - 3;
        speed = 20 + (int) (Math.random() * 11);
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

            if (collidingX()) {
                vx *= -1;
                x += vx;

                synchronized (MONITOR) {
                    try {
                        MONITOR.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }

            if (collidingY()) {
                vy *= -1;
                y += vy;

                synchronized (MONITOR) {
                    try {
                        MONITOR.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }

            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void wakeUp() {
        synchronized (MONITOR) {
            thread.notifyAll();
        }
    }

    public abstract boolean collidingX();
    public abstract boolean collidingY();

    static final Object MONITOR = new Object();
}
