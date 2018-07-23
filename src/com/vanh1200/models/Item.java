package com.vanh1200.models;

import javax.swing.*;
import java.awt.*;

public class Item {
    public final static int BOMB = 1;
    public final static int BOMB_SIZE = 2;
    public final static int SHOES = 3;
    private int x;
    private int y;
    private Image[] images;
    private int type;
    private long timeStart;
    private int timeAlive;
    private int count = 0;
    private int i = 0;

    public Item(int x, int y, int type){
        this.x = x;
        this.y = y;
        this.type = type;
        this.timeStart = 0; // khi item chua appear(Behind box) thi timeStart = 0;
        this.timeAlive = 5000;
        images = new Image[4];
        switch (type){
            case BOMB:
                images[0] = new ImageIcon(getClass().getResource("/Images/item_boom.png")).getImage();
                images[1] = new ImageIcon(getClass().getResource("/Images/item_boom_01.png")).getImage();
                images[2] = new ImageIcon(getClass().getResource("/Images/item_boom_02.png")).getImage();
                images[3] = new ImageIcon(getClass().getResource("/Images/item_boom_03.png")).getImage();
                break;
            case BOMB_SIZE:
                images[0] = new ImageIcon(getClass().getResource("/Images/item_exp.png")).getImage();
                images[1] = new ImageIcon(getClass().getResource("/Images/item_exp_01.png")).getImage();
                images[2] = new ImageIcon(getClass().getResource("/Images/item_exp_02.png")).getImage();
                images[3] = new ImageIcon(getClass().getResource("/Images/item_exp_03.png")).getImage();
                break;
            case SHOES:
                images[0] = new ImageIcon(getClass().getResource("/Images/item_speed1.png")).getImage();
                images[1] = new ImageIcon(getClass().getResource("/Images/item_speed_01.png")).getImage();
                images[2] = new ImageIcon(getClass().getResource("/Images/item_speed_02.png")).getImage();
                images[3] = new ImageIcon(getClass().getResource("/Images/item_speed_03.png")).getImage();
                break;

        }
    }


    public void draw(Graphics2D g2d){
        if (count == 70){
            count = 0;
            i++;
            if (i > 3)
                i = 0;
        }
        count++;
        g2d.drawImage(images[i], x, y, 45, 45, null);
    }

    public Rectangle getRec(){
        return new Rectangle(x, y, 45, 45);
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
