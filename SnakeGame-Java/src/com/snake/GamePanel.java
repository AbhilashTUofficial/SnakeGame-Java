package com.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    int pixelSize = 25;
    int totalPixels = (600 * 600) / pixelSize;
    int delay = 80;
    int snakeMoveUnit = 25;
    int snakeHeadX = 25;
    int snakeHeadY = 25;
    int[] snakeX = new int[totalPixels];
    int[] snakeY = new int[totalPixels];
    int snakeLength = 4;
    int score = 0;
    int fruitX;
    int fruitY;
    String dir = "R";
    boolean running = false;
    boolean gameOver = false;
    Timer time;
    Random rand;

    GamePanel() {
        rand = new Random();
        snakeX[0] = snakeHeadX;
        snakeY[0] = snakeHeadY;
        setBounds(new Rectangle(600, 600));
        setBackground(Color.BLACK);
        setFocusTraversalKeysEnabled(false);
        setFocusable(true);
        addKeyListener(this);
        startGame();
        setVisible(true);
        repaint();

    }

    public void startGame() {
        running = true;
        generateFruit();
        time = new Timer(delay, this);
        time.start();

    }

    public void generateFruit() {
        while (true) {
            fruitY = rand.nextInt(600);
            fruitX = rand.nextInt(600);
            if ((fruitY % 25 == 0 && fruitY > 25 && fruitY < 575) && (fruitX % 25 == 0 && fruitX > 25 && fruitX < 575)) {
                return;
            }

        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameOver == true) {
            running = false;
            fruitX = fruitY = 700;
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("Game Over", 180, 260);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString("Score: " + score, 270, 300);
        }
        drawSnake(g);
        drawBoard(g);
        drawFruit(g);
        g.dispose();
        repaint();

    }

    public void drawSnake(Graphics g) {
        for (int i = 0; i < snakeLength; i++) {
            if (i == 0) {
                g.setColor(Color.white);
                g.fillRect(snakeX[i], snakeY[i], 25, 25);
                g.setColor(Color.red);
                g.drawRect(snakeX[i], snakeY[i], 25, 25);
            } else {
                g.setColor(new Color(250, 230, 230));
                g.fillRect(snakeX[i], snakeY[i], 25, 25);
            }
        }


    }

    public void drawBoard(Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, 10, 600);
        g.fillRect(590, 0, 10, 600);
        g.fillRect(0, 590, 600, 10);
        g.fillRect(0, 0, 600, 10);

        g.setColor(Color.lightGray);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString(String.format("score: %03d", score), 480, 25);

        g.setColor(Color.BLACK);
        g.drawRect(0, 0, 600, 600);
        g.setColor(Color.darkGray);
        for (int i = 25; i <= 600; i += 25) {
            for (int j = 25; j <= 600; j += 25) {
                // g.drawLine(i, 0, i, 600);
                // g.drawLine(0, j, 600, j);
            }
        }


        repaint();
    }

    public void drawFruit(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(fruitX, fruitY, 25, 25);
        repaint();
    }


    public void move() {
        for (int i = snakeLength; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }
        if (dir.equals("R")) {
            if (running) {
                snakeX[0] = snakeX[0] + snakeMoveUnit;


            }
        }
        if (dir.equals("L")) {
            if (running) {
                snakeX[0] = snakeX[0] - snakeMoveUnit;


            }
        }
        if (dir.equals("U")) {
            if (running) {
                snakeY[0] = snakeY[0] - snakeMoveUnit;


            }
        }
        if (dir.equals("D")) {
            if (running) {
                snakeY[0] = snakeY[0] + snakeMoveUnit;


            }
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        //Collision with fruit
        if (new Rectangle(snakeX[0], snakeY[0], 25, 25).intersects(new Rectangle(fruitX, fruitY, 25, 25))) {
            score += 10;
            generateFruit();
            snakeLength += 1;
        }

        //Collision with the boarder
        if (new Rectangle(snakeX[0], snakeY[0], 25, 25).intersects(new Rectangle(0, 0, 10, 600))) {
            gameOver = true;
        }
        if (new Rectangle(snakeX[0], snakeY[0], 25, 25).intersects(new Rectangle(590, 0, 10, 600))) {
            gameOver = true;
        }
        if (new Rectangle(snakeX[0], snakeY[0], 25, 25).intersects(new Rectangle(0, 590, 600, 10))) {
            gameOver = true;
        }
        if (new Rectangle(snakeX[0], snakeY[0], 25, 25).intersects(new Rectangle(0, 0, 600, 10))) {
            gameOver = true;
        }
        for (int i = snakeLength; i > 0; i--) {
            if ((snakeX[0] == snakeX[i]) && (snakeY[0] == snakeY[i])) {
                gameOver = true;
            }
        }
        move();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT && !dir.equals("L")) {
            running = true;

            dir = "R";
            move();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && !dir.equals("R")) {
            running = true;
            dir = "L";

        }
        if (e.getKeyCode() == KeyEvent.VK_UP && !dir.equals("D")) {
            running = true;
            dir = "U";

        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && !dir.equals("U")) {
            running = true;
            dir = "D";

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
