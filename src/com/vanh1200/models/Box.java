package com.vanh1200.models;

import javax.swing.*;
import java.awt.*;

public class Box {
    public final static int canBeBombed = 2;
    public final static int canNotBeBombed = 1;
    private int x;
    private int y;
    private int type;
    private Image image;

    public Box(int x, int y, int type, String urlImage){ //0 la box nau, 1 la box xanh
        this.x = x;
        this.y = y;
        this.type = type;
        image = new ImageIcon(
                getClass()
                .getResource(urlImage))
                .getImage();
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(image, x, y, null);
    }

    public Rectangle getRec(){
        return new Rectangle(x, y, 45, 45);
    }
}
