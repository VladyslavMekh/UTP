package AWT;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyFrame extends Frame {
    private BallPanel ballPanel;
    private Panel buttonPanel;
    private Button addBall;
    private Button shakePanel;

    public MyFrame() {
        ballPanel = new BallPanel();
        addBall = new Button("Add Ball");
        shakePanel = new Button("Shake Balls");
        buttonPanel = new Panel();

        addBall.addActionListener(ballPanel);
        shakePanel.addActionListener(e -> ballPanel.shake());

        FlowLayout flowLayout = new FlowLayout();
        buttonPanel.setLayout(flowLayout);

        buttonPanel.add(addBall);
        buttonPanel.add(shakePanel);

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);
        add(ballPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(500, 500);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        ballPanel.setBackground(Color.YELLOW);
        buttonPanel.setBackground(Color.GREEN);

        setVisible(true);
    }

    static void main(String[] args) {
        new MyFrame();
    }
}
