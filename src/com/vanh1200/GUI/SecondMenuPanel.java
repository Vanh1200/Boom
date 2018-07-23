package com.vanh1200.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SecondMenuPanel extends JPanel implements MouseListener{
    private GUIManager guiManager;
    private JLabel lbPlay;
    private JLabel lbNewGame;
    private JLabel lbOption;
    private JLabel lbHighScore;
    private JLabel lbExit;
    public SecondMenuPanel(GUIManager guiManager) {
        this.guiManager = guiManager;
        setLayout(null);
        addComp();
    }

    private void addComp() {
        int x = GUIManager.W_FRAME / 2 - 182 / 2;
        int y = 200;
        lbPlay = setLabel(x, y, "/Images/button_resume.png");
        add(lbPlay);
        lbPlay.addMouseListener(this);
        y += 70;

        lbNewGame = setLabel(x, y, "/Images/button_new-game.png");
        add(lbNewGame);
        lbNewGame.addMouseListener(this);
        y += 70;

        lbOption = setLabel(x, y, "/Images/button_option.png");
        add(lbOption);
        lbOption.addMouseListener(this);
        y += 70;

        lbHighScore = setLabel(x, y, "/Images/button_high-score.png");
        add(lbHighScore);
        lbHighScore.addMouseListener(this);
        y += 70;

        lbExit = setLabel(x, y, "/Images/button_exit.png");
        add(lbExit);
        lbExit.addMouseListener(this);
    }

    private JLabel setLabel(int x, int y, String url){
        JLabel jLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(url));
        jLabel.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
        jLabel.setLocation(x, y);
        jLabel.setIcon(imageIcon);
        return jLabel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Image image = new ImageIcon(getClass().getResource("/Images/back_ground_menu.png")).getImage();
        g2d.drawImage(image, - 24, 0, GUIManager.W_FRAME + 24, GUIManager.H_FRAME, null);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource().equals(lbPlay)){
            guiManager.getBoomPanel().setIS_PAUSE(false);
            guiManager.showPlayGame();
        }
        else if(e.getSource().equals(lbNewGame)){
            guiManager.getBoomPanel().setIS_PAUSE(false);
            guiManager.showNewGame();
        }
        else if(e.getSource().equals(lbOption)){

        }
        else if(e.getSource().equals(lbHighScore)){
            guiManager.showHighScore();
        }
        else if(e.getSource().equals(lbExit)){
            System.exit(0);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource().equals(lbPlay)){
            lbPlay.setIcon(new ImageIcon(getClass().getResource("/Images/button_resume (1).png")));
        }
        else if(e.getSource().equals(lbNewGame)){
            lbNewGame.setIcon(new ImageIcon(getClass().getResource("/Images/button_new-game (1).png")));
        }
        else if(e.getSource().equals(lbOption)){
            lbOption.setIcon(new ImageIcon(getClass().getResource("/Images/button_option (1).png")));
        }
        else if(e.getSource().equals(lbHighScore)){
            lbHighScore.setIcon(new ImageIcon(getClass().getResource("/Images/button_high-score (1).png")));
        }
        else if(e.getSource().equals(lbExit)){
            lbExit.setIcon(new ImageIcon(getClass().getResource("/Images/button_exit (1).png")));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource().equals(lbPlay)){
            lbPlay.setIcon(new ImageIcon(getClass().getResource("/Images/button_resume.png")));
        }
        else if(e.getSource().equals(lbNewGame)){
            lbNewGame.setIcon(new ImageIcon(getClass().getResource("/Images/button_new-game.png")));
        }
        else if(e.getSource().equals(lbOption)){
            lbOption.setIcon(new ImageIcon(getClass().getResource("/Images/button_option.png")));
        }
        else if(e.getSource().equals(lbHighScore)){
            lbHighScore.setIcon(new ImageIcon(getClass().getResource("/Images/button_high-score.png")));
        }
        else if(e.getSource().equals(lbExit)){
            lbExit.setIcon(new ImageIcon(getClass().getResource("/Images/button_exit.png")));
        }
    }
}
