package com.snake;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    final int frameWidth=616;
    final int frameHeight=639;
    Main(){
        GamePanel gamePlay=new GamePanel();
        setBounds(100,200,frameWidth,frameHeight);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        add(gamePlay);
        setBackground(Color.black);
        setVisible(true);

    }
    public static void main(String[] args) {
        new Main();

    }
}
