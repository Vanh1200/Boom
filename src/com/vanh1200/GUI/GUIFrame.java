package com.vanh1200.GUI;

import javax.swing.*;

public class GUIFrame extends JFrame {
    private static int GUI_W = 882;
    private static int GUI_H = 610;
    public GUIFrame(){
        setSize(GUI_W, GUI_H);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new GUIManager());
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
