package com.vanh1200.GUI;

import com.vanh1200.models.HighScore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class HighScorePanel extends JPanel implements MouseListener {
    private ArrayList<HighScore> highScores;
    private GUIManager guiManager;
    private JLabel lb_back;
    public HighScorePanel(GUIManager guiManager) {
        this.guiManager = guiManager;
        setLayout(null);
        addComp();
        initHighScore("src/highScores/highscore.txt");
    }

    private void addComp() {
        int x = GUIManager.W_FRAME / 2 - 182 / 2;
        int y = 520;
        lb_back = setLabel(x, y, "/Images/button_back.png");
        lb_back.addMouseListener(this);
        add(lb_back);
    }

    private JLabel setLabel(int x, int y, String url) {
        JLabel jLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(url));
        jLabel.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
        jLabel.setIcon(imageIcon);
        jLabel.setLocation(x, y);
        return jLabel;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //Draw background;
        Image image = new ImageIcon(getClass().getResource("/Images/background_highscore.png")).getImage();
        g2d.drawImage(image, 0, - 35 , GUIManager.W_FRAME, GUIManager.H_FRAME + 35, null);

        //Draw HighScore
        int y = 60;
        int x = 200;
        for(int i = 0; i < highScores.size(); i++){
            g2d.setColor(Color.PINK);
            g2d.setFont(new Font("Arial", Font.BOLD, 40));
            g2d.drawString(Integer.toString(i + 1), x , y);
            g2d.drawString(highScores.get(i).getName(), x + 100, y);
            g2d.drawString(Integer.toString(highScores.get(i).getScore()), x + 400, y);
            y += 50;
        }
    }

    public void initHighScore(String highScorePath){
        highScores = new ArrayList<>();
        try{
            String line = "";
            FileReader fileReader = new FileReader(highScorePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null){
                String name;
                int score;
                String[] arr = line.split(":");
                name = arr[0];
                score = Integer.parseInt(arr[1]);
                HighScore highScore = new HighScore(name, score);
                highScores.add(highScore);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource().equals(lb_back)){
            if(BoomPanel.HIT_PAUSE == false)
                guiManager.showMenu();
            else
                guiManager.showSubMenu();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource().equals(lb_back)){
            lb_back.setIcon(new ImageIcon(getClass().getResource("/Images/button_back (1).png")));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource().equals(lb_back)){
            lb_back.setIcon(new ImageIcon(getClass().getResource("/Images/button_back.png")));
        }
    }
}
