package com.vanh1200.GUI;

import com.vanh1200.models.Bomber;
import com.vanh1200.models.GameManager;
import sounds.GameSound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.BitSet;

public class BoomPanel extends JPanel implements KeyListener, Runnable, MouseListener {
    private GameManager gameManager;
    private JLabel lb_back;
    private BitSet bitSet = new BitSet();
    private GUIManager guiManager;
    private int timeDead = 0;
    private int timeNextRound = 0;
    private int timeWin = 0;
    private int timeLose = 0;
    private boolean IS_RUNNING = true;
    private boolean IS_PAUSE = true;
    public static boolean HIT_PAUSE = false;
    Thread thread;
    public BoomPanel(GUIManager guiManager) {
        setLayout(null);
        this.guiManager = guiManager;
        gameManager = new GameManager();
        gameManager.initGame();
        addLabel();
        addKeyListener(this);
        thread = new Thread(this);
        thread.start();
    }

    private void addLabel() {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/Images/button_pause.png"));
        lb_back = new JLabel();
        lb_back.setIcon(imageIcon);
        lb_back.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
        lb_back.setLocation(690, 520);
        lb_back.addMouseListener(this);
        add(lb_back);
    }


    public void newGame(){
        IS_PAUSE = true; // Trong qua trinh goi newGame thi Thread1 (this) van chay, neu khong stop -> nullpointerEx (enemy dang duoc khoi tao lai trong khi thread van chay)
        gameManager = new GameManager();
        gameManager.initGame();
        IS_PAUSE = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        gameManager.draw(g2d);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        bitSet.set(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        bitSet.clear(e.getKeyCode());
    }

    @Override
    public void run() {
        while(IS_RUNNING){
            while(IS_PAUSE){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(bitSet.get(KeyEvent.VK_LEFT)){
                gameManager.bomberMove(Bomber.LEFT);
            }else
            if(bitSet.get(KeyEvent.VK_RIGHT)){
                gameManager.bomberMove(Bomber.RIGHT);
            }else
            if(bitSet.get(KeyEvent.VK_UP)){
                gameManager.bomberMove(Bomber.UP);
            }else
            if(bitSet.get(KeyEvent.VK_DOWN)){
                gameManager.bomberMove(Bomber.DOWN);
            }
            if(bitSet.get(KeyEvent.VK_SPACE)){
                gameManager.bombing();
            }
            gameManager.enemyMove();
            gameManager.checkDeadTimeBombBang();
            gameManager.checkDeadTimeBomb();
            gameManager.checkDeadTimeItem();
            gameManager.checkBombBangImpactVsBomb();
            gameManager.checkBombBangImpactVsEnemy();
            gameManager.checkBombBangImpactVsBox();
            gameManager.checkBombBangImpactVsItem();
            gameManager.checkBomberImpactItem();
            gameManager.checkDead();
            gameManager.checkRound();

            if(gameManager.getBomber().getStatus() == Bomber.DEAD && gameManager.getGameStatus() != GameManager.LOSE){
                timeDead++;
                if(timeDead == 1500){
                    timeDead = 0;
                    gameManager.setBomberNewLife();
                }
            }


            if(gameManager.getGameStatus() == GameManager.LOSE){
                timeLose++;
                System.out.println(timeLose);
                if(timeLose == 3000){
                    timeLose = 0;
                    IS_PAUSE = true;
                    guiManager.showMenu();
                    gameManager.setGameStatus(GameManager.RUNNING);
                }
            }

            if(gameManager.getGameStatus() == GameManager.WIN){
                timeWin++;
                if(timeWin == 3000){
                    timeWin = 0;
                    GameSound.getIstance().getAudio(GameSound.PLAYGAME).loop();
                    IS_PAUSE = true;
                    guiManager.showMenu();
                }
            }

            if(gameManager.getGameStatus() == GameManager.NEXT_ROUND){
                timeNextRound++;
                if(timeNextRound == 3000){
                    timeNextRound = 0;
                    GameSound.getIstance().getAudio(GameSound.PLAYGAME).loop();
                    gameManager.initGame();
                }
            }

            repaint();
            try {
                Thread.sleep(1);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource().equals(lb_back)){
            setIS_PAUSE(true);
            HIT_PAUSE = true;
            guiManager.showSubMenu();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource().equals(lb_back)){
            lb_back.setIcon(new ImageIcon(getClass().getResource("/Images/button_pause (1).png")));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource().equals(lb_back)){
            lb_back.setIcon(new ImageIcon(getClass().getResource("/Images/button_pause.png")));
        }
    }

    public boolean isIS_RUNNING() {
        return IS_RUNNING;
    }

    public void setIS_RUNNING(boolean IS_RUNNING) {
        this.IS_RUNNING = IS_RUNNING;
    }

    public boolean isIS_PAUSE() {
        return IS_PAUSE;
    }

    public void setIS_PAUSE(boolean IS_PAUSE) {
        this.IS_PAUSE = IS_PAUSE;
    }
}
