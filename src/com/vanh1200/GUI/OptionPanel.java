package com.vanh1200.GUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class OptionPanel extends JPanel implements MouseListener {
    private GUIManager guiManager;
    private JLabel lbBack;
    public OptionPanel(GUIManager guiManager) {
        this.guiManager = guiManager;
        setLayout(null);
        addComp();
    }

    private void addComp() {
        int x = GUIManager.W_FRAME / 2 - 182 / 2;
        int y = 520;
        lbBack = setLabel(x, y, "/Images/button_back.png");
        add(lbBack);
        lbBack.addMouseListener(this);
    }

    private JLabel setLabel(int x, int y, String URL) {
        JLabel jLabel = new JLabel();
        ImageIcon icon = new ImageIcon(getClass().getResource(URL));
        jLabel.setIcon(icon);
        jLabel.setSize(icon.getIconWidth(), icon.getIconHeight());
        jLabel.setLocation(x, y);
        return jLabel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Image bgImage = new ImageIcon(getClass().getResource("/Images/background_option.png")).getImage();
        g2d.drawImage(bgImage, 0, 0, GUIManager.W_FRAME, GUIManager.H_FRAME - 80, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource().equals(lbBack)){
            if(BoomPanel.HIT_PAUSE){
                guiManager.showSubMenu();
            }
            else{
                guiManager.showMenu();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        lbBack.setIcon(new ImageIcon(getClass().getResource("/Images/button_back (1).png")));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        lbBack.setIcon(new ImageIcon(getClass().getResource("/Images/button_back.png")));
    }
}
