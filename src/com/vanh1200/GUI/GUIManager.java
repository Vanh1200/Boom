package com.vanh1200.GUI;

import com.vanh1200.models.HighScore;
import sounds.GameSound;


import javax.swing.*;
import java.awt.*;

public class GUIManager extends JPanel{
    public static final int PLAY_W = 882 - 200;
    public static final int PLAY_H = 610;
    public static final int W_FRAME = 882;
    public static final int H_FRAME = 610;

    private BoomPanel boomPanel;
    private HighScorePanel highScorePanel;
    private OptionPanel optionPanel;
    private MenuPanel menuPanel;
    private CardLayout cardLayout; // Card layout se giup luu nhieu panel tai cung 1 vi tri va hien thi cai can hien thi khi can
    private SecondMenuPanel secondMenuPanel;

    private static String MENU_TAG = "menu";
    private static String SUB_MENU_TAG = "submenu";
    private static String OPTION_TAG = "option";
    private static String HIGHSCORE_TAG = "highscore";
    private static String PLAYGAME_TAG = "playgame";


    public GUIManager(){
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        menuPanel = new MenuPanel(this);
        add(menuPanel, MENU_TAG);
        secondMenuPanel = new SecondMenuPanel(this);
        add(secondMenuPanel, SUB_MENU_TAG);
        boomPanel = new BoomPanel(this);
        add(boomPanel, PLAYGAME_TAG);
        highScorePanel = new HighScorePanel(this);
        add(highScorePanel, HIGHSCORE_TAG);
        optionPanel = new OptionPanel(this);
        add(optionPanel, OPTION_TAG);

        showMenu();
    }

    public void showMenu(){
        cardLayout.show(this, MENU_TAG);
        menuPanel.requestFocus();
        GameSound.getIstance().stop();
        GameSound.getIstance().getAudio(GameSound.MENU).loop();
    }

    public void showSubMenu(){
        cardLayout.show(this, SUB_MENU_TAG);
        secondMenuPanel.requestFocus();
        GameSound.getIstance().stop();
        GameSound.getIstance().getAudio(GameSound.MENU).loop();
    }

    public void showOption(){
        cardLayout.show(this, OPTION_TAG);
        optionPanel.requestFocus();
    }

    public void showHighScore(){
        cardLayout.show(this, HIGHSCORE_TAG);
        highScorePanel.requestFocus();;
    }

    public void showPlayGame(){
        cardLayout.show(this, PLAYGAME_TAG);
        boomPanel.requestFocus();
        GameSound.getIstance().stop();
        GameSound.getIstance().getAudio(GameSound.PLAYGAME).loop();
    }

    public void showNewGame(){
        boomPanel.newGame();
        cardLayout.show(this, PLAYGAME_TAG);
        boomPanel.requestFocus();
        GameSound.getIstance().stop();
        GameSound.getIstance().getAudio(GameSound.PLAYGAME).loop();
    }

    public void exit() {
        boomPanel.setIS_RUNNING(false);
        System.exit(0);
    }

    public BoomPanel getBoomPanel() {
        return boomPanel;
    }

    public void setBoomPanel(BoomPanel boomPanel) {
        this.boomPanel = boomPanel;
    }
}
