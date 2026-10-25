package AWT;

import java.awt.*;
import java.util.concurrent.Semaphore;

public abstract class VsThreadBall extends VBall {
    private final Semaphore semaphore = new Semaphore(1);

    public VsThreadBall(int x, int y) {
        super(x, y);
        vExecutor.submit(this);
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        super.draw(g);
    }

    @Override
    public void wakeUp() {
        semaphore.release();
    }

    @Override
    public void run() {
        while (true) {
            try {
                semaphore.release();

                if (collidingX()) {
                    vx *= -1;
                    semaphore.acquire();
                }

                if (collidingY()) {
                    vy *= -1;
                    semaphore.acquire();
                }

                x += vx;
                y += vy;

                Thread.sleep(speed);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
