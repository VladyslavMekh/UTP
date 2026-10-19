package AWT;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BallPanel extends Panel
    implements Runnable, ActionListener
{
    public List<Ball> ballList;

    public BallPanel() {
        ballList = new ArrayList<>();
        new Thread(this).start();
    }

    public void paint(Graphics graphics) {
        for (Ball element : ballList) {
            element.draw(graphics);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Ball ball = new Ball(getWidth() / 2, getHeight() / 2) {
            @Override
            public boolean collidingX() {
                return x + vx < 5 || x + vx > BallPanel.this.getWidth() - 5;
            }

            @Override
            public boolean collidingY() {
                return y + vy < 5 || y + vy > BallPanel.this.getHeight() - 5;
            }
        };
        ballList.add(ball);
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(33);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void shake() {
        for (Ball element : ballList) {
            element.wakeUp();
        }
    }
}
