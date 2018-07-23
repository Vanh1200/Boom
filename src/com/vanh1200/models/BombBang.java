package com.vanh1200.models;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BombBang {
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;
    private int x;
    private int y;
    private Image[] images = new Image[4];
    private long timeStart;
    private int timeAlive;
    private int size;

    public BombBang(int x, int y, int size, int timeAlive, ArrayList<Box> arrBox){ // add arrBox to init bigger size BombBang;
        this.x = x;
        this.y = y;
        this.timeStart = System.currentTimeMillis();
        this.timeAlive = timeAlive;
        this.size = size;
        images[0] = new ImageIcon(getClass().getResource("/Images/bombbang_left_1.png")).getImage();
        images[1] = new ImageIcon(getClass().getResource("/Images/bombbang_right_1.png")).getImage();
        images[2] = new ImageIcon(getClass().getResource("/Images/bombbang_up_1.png")).getImage();
        images[3] = new ImageIcon(getClass().getResource("/Images/bombbang_down_1.png")).getImage();
        boolean mark[] = new boolean[] {false, false, false, false};
        if(size > 1){
            for(int i = 0; i < 4; i++){
                if(isAPieceImpactVsBox(i, arrBox))
                    mark[i] = true;
            }
            if(mark[0] == false)
                images[0] = new ImageIcon(getClass().getResource("/Images/bombbang_left_2.png")).getImage();
            if(mark[1] == false)
                images[1] = new ImageIcon(getClass().getResource("/Images/bombbang_right_2.png")).getImage();
            if(mark[2] == false)
                images[2] = new ImageIcon(getClass().getResource("/Images/bombbang_up_2.png")).getImage();
            if(mark[3] == false)
                images[3] = new ImageIcon(getClass().getResource("/Images/bombbang_down_2.png")).getImage();
        }
    }

    public boolean isAPieceImpactVsBox(int orient, ArrayList<Box> arrBox){
        Rectangle rec  = new Rectangle();
        switch (orient){
            case LEFT:
                rec = new Rectangle(x + 45 - images[0].getWidth(null), y, images[0].getWidth(null), images[0].getHeight(null));
                break;
            case RIGHT:
                rec = new Rectangle(x, y, images[1].getWidth(null), images[1].getHeight(null));
                break;
            case UP:
                rec = new Rectangle(x, y + 45  -images[2].getHeight(null), images[2].getWidth(null), images[2].getHeight(null));
                break;
            case DOWN:
                rec = new Rectangle(x, y, images[3].getWidth(null), images[3].getHeight(null));
                break;
        }
        for(int i = 0; i < arrBox.size(); i++){
            if(rec.intersects(arrBox.get(i).getRec()))
                return true;
        }
        return false;
    }

    public boolean isImpactVsBomber(Bomber bomber){
        Rectangle rec1 = new Rectangle(x + 45 - images[0].getWidth(null), y, images[0].getWidth(null), images[0].getHeight(null));
        Rectangle rec2 = new Rectangle(x, y, images[1].getWidth(null), images[1].getHeight(null));
        Rectangle rec3 = new Rectangle(x, y + 45  -images[2].getHeight(null), images[2].getWidth(null), images[2].getHeight(null));
        Rectangle rec4 = new Rectangle(x, y, images[3].getWidth(null), images[3].getHeight(null));
        Rectangle recBomber = bomber.getRec();
        return (rec1.intersects(recBomber) ||
        rec2.intersects(recBomber) ||
        rec3.intersects(recBomber) ||
        rec4.intersects(recBomber));
    }

    public boolean isImpactVsBomb(Bomb bomb){
        Rectangle rec1 = new Rectangle(x + 45 - images[0].getWidth(null), y, images[0].getWidth(null), images[0].getHeight(null));
        Rectangle rec2 = new Rectangle(x, y, images[1].getWidth(null), images[1].getHeight(null));
        Rectangle rec3 = new Rectangle(x, y + 45  -images[2].getHeight(null), images[2].getWidth(null), images[2].getHeight(null));
        Rectangle rec4 = new Rectangle(x, y, images[3].getWidth(null), images[3].getHeight(null));
        Rectangle recBomb = bomb.getRec();
        return (rec1.intersects(recBomb) ||
                rec2.intersects(recBomb) ||
                rec3.intersects(recBomb) ||
                rec4.intersects(recBomb));
    }

    public boolean isImpactVsEnemy(Enemy enemy){
        Rectangle rec1 = new Rectangle(x + 45 - images[0].getWidth(null), y, images[0].getWidth(null), images[0].getHeight(null));
        Rectangle rec2 = new Rectangle(x, y, images[1].getWidth(null), images[1].getHeight(null));
        Rectangle rec3 = new Rectangle(x, y + 45  -images[2].getHeight(null), images[2].getWidth(null), images[2].getHeight(null));
        Rectangle rec4 = new Rectangle(x, y, images[3].getWidth(null), images[3].getHeight(null));
        Rectangle recEnemy = enemy.getRec();
        return (rec1.intersects(recEnemy) ||
                rec2.intersects(recEnemy) ||
                rec3.intersects(recEnemy) ||
                rec4.intersects(recEnemy));
    }

    public boolean isImpactVsBox(Box box){
        Rectangle rec1 = new Rectangle(x + 45 - images[0].getWidth(null), y, images[0].getWidth(null), images[0].getHeight(null));
        Rectangle rec2 = new Rectangle(x, y, images[1].getWidth(null), images[1].getHeight(null));
        Rectangle rec3 = new Rectangle(x, y + 45  -images[2].getHeight(null), images[2].getWidth(null), images[2].getHeight(null));
        Rectangle rec4 = new Rectangle(x, y, images[3].getWidth(null), images[3].getHeight(null));
        Rectangle recBox = box.getRec();
        return (rec1.intersects(recBox) ||
                rec2.intersects(recBox) ||
                rec3.intersects(recBox) ||
                rec4.intersects(recBox));
    }

    public boolean isImpactVsItem(Item item){
        Rectangle rec1 = new Rectangle(x + 45 - images[0].getWidth(null), y, images[0].getWidth(null), images[0].getHeight(null));
        Rectangle rec2 = new Rectangle(x, y, images[1].getWidth(null), images[1].getHeight(null));
        Rectangle rec3 = new Rectangle(x, y + 45  -images[2].getHeight(null), images[2].getWidth(null), images[2].getHeight(null));
        Rectangle rec4 = new Rectangle(x, y, images[3].getWidth(null), images[3].getHeight(null));
        Rectangle recItem = item.getRec();
        return (rec1.intersects(recItem) ||
                rec2.intersects(recItem) ||
                rec3.intersects(recItem) ||
                rec4.intersects(recItem));
    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(images[0], x + 45 - images[0].getWidth(null), y, null);
        g2d.drawImage(images[1], x, y, null);
        g2d.drawImage(images[2], x, y + 45  -images[2].getHeight(null), null);
        g2d.drawImage(images[3], x, y, null);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    public int getTimeAlive() {
        return timeAlive;
    }

    public void setTimeAlive(int timeAlive) {
        this.timeAlive = timeAlive;
    }
}
