package AWT;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static AWT.ThreadBall.MONITOR;

public abstract class ScheduledBall  extends Ball {
    ScheduledExecutorService scheduled =
            Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> scheduledFuture;

    public ScheduledBall(int x, int y) {
        super(x, y);
        scheduledFuture = scheduled.scheduleAtFixedRate(
                this,
                0,
                speed,
                TimeUnit.MILLISECONDS
        );
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        super.draw(g);
    }

    @Override
    public void run() {
        x += vx;
        y += vy;

        if (collidingX()) {
            vx *= -1;
            x += vx;
            scheduledFuture.cancel(true);
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
            scheduledFuture.cancel(true);
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


    @Override
    public void wakeUp() {
        if (scheduledFuture == null)
            scheduledFuture = scheduled.scheduleAtFixedRate(
                    this,
                    0,
                    speed,
                    TimeUnit.MILLISECONDS
            );
    }
}
