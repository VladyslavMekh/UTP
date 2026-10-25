package AWT;

import java.awt.*;

public abstract class ThreadBall extends Ball {
    static final Object MONITOR = new Object();
    protected Thread thread;

    public ThreadBall(int x, int y) {
        super(x, y);
        thread = new Thread(this);
        thread.start();
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


    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        super.draw(g);
    }

    public void wakeUp() {

    }
}
