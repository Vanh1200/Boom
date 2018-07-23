package com.vanh1200.models;

import javax.swing.*;
import java.awt.*;

public class Bomb {
    private int x;
    private int y;
    private Image image;
    private long timeStart;
    private int timeAlive;
    private int size;
    public Bomb(int x, int y, int size, int timeAlive){
        this.x = x;
        this.y = y;
        this.size = size;
        this.timeStart = System.currentTimeMillis();
        this.timeAlive = timeAlive;
        this.image = new ImageIcon(getClass()
                .getResource("/Images/bomb.png"))
                .getImage();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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

    public synchronized void draw(Graphics2D g2d){
        g2d.drawImage(image, x, y, null);
    }

    public Rectangle getRec(){
        return new Rectangle(x, y, 45, 45);
    }
}
