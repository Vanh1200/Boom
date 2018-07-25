package com.vanh1200.models;

import com.vanh1200.GUI.GUIManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Enemy {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;
    public static final int MONSTER = 1;
    public static final int BOSS = 2;
    private int x;
    private int y;
    private Image images[];
    private int orient;
    private int type;
    private int healthBoss;
    private int speed; // from 1 -> 10;
    private int countSpeed = 1000000;
    private Random random = new Random();

    public Enemy (int x, int y, int orient, int type, int speed){
        this.x = x;
        this.y = y;
        this.orient = orient;
        this.speed = speed;
        this.type = type;
        images = new Image[4];
        if(type == 1){
            images[0] = new ImageIcon(getClass()
                    .getResource("/Images/monster_left.png"))
                    .getImage();
            images[1] = new ImageIcon(getClass()
                    .getResource("/Images/monster_right.png"))
                    .getImage();
            images[2] = new ImageIcon(getClass()
                    .getResource("/Images/monster_up.png"))
                    .getImage();
            images[3] = new ImageIcon(getClass()
                    .getResource("/Images/monster_down.png"))
                    .getImage();
        }
        else if(type == 2){
            this.healthBoss = 10;
            images[0] = new ImageIcon(getClass()
                    .getResource("/Images/boss_left.png"))
                    .getImage();
            images[1] = new ImageIcon(getClass()
                    .getResource("/Images/boss_right.png"))
                    .getImage();
            images[2] = new ImageIcon(getClass()
                    .getResource("/Images/boss_up.png"))
                    .getImage();
            images[3] = new ImageIcon(getClass()
                    .getResource("/Images/boss_down.png"))
                    .getImage();
        }

    }

    public void draw(Graphics2D g2d){
        if(type == 1){
            g2d.drawImage(images[orient], x, y - 23, null);
        }
        else if(type == 2){
            g2d.drawImage(images[orient], x, y - 38, null);
            int x_heartBoss = x + 19;
            int y_heartBoss = y - 60;
            for(int i = 0; i < healthBoss; i++)
                g2d.drawImage(new ImageIcon(getClass().getResource("/Images/heart_boss.png")).getImage(), x_heartBoss + i * 9, y_heartBoss, null);
        }
    }

    public int getHealthBoss() {
        return healthBoss;
    }

    public void setHealthBoss(int healthBoss) {
        this.healthBoss = healthBoss;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getOrient() {
        return orient;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setOrient(int orient) {
        this.orient = orient;
    }

    public Rectangle getRec(){
        if(type == 1)
            return new Rectangle(x, y, 45, 45);
        return new Rectangle(x, y, 135, 140);
    }

    public boolean delayMove(){
        if (countSpeed == 0) countSpeed = 1000000;
        countSpeed--;
        if((countSpeed % (11 - speed)) != 0)
            return true;
        return false;
    }

//    public void move(ArrayList<Box> arrBox){
//        if(delayMove())
//            return;
//        int xR = x;
//        int yR = y;
//        switch (orient){
//            case LEFT:
//                x -= 1;
//                break;
//            case RIGHT:
//                x += 1;
//                break;
//            case UP:
//                y -= 1;
//                break;
//            case DOWN:
//                y += 1;
//                break;
//        }
//        boolean check = isImpactVsMap(arrBox);
//        boolean check2 = isOutOfMap();
//        if(check2 == true || isOutOfMap()){
//            x = xR;
//            y = yR;
//        }
//    }
//
//    public boolean canMove(int orient, ArrayList<Box> arrBox, ArrayList<Bomb> arrBomb){
//        Enemy e = new Enemy(x, y, orient, speed);
//        switch (orient){
//            case LEFT:
//                e.x -= 1;
//                break;
//            case RIGHT:
//                e.x += 1;
//                break;
//            case UP:
//                e.y -= 1;
//                break;
//            case DOWN:
//                e.y += 1;
//                break;
//        }
//        boolean check = e.isImpactVsMap(arrBox);
//        boolean check2 = e.isOutOfMap();
//        boolean check3 = isImpactVsBomb(arrBomb);
//        if(check == false && check2 == false && check3 == false)
//            return true;
//        return false;
//    }
//
//
//    public void autoMove(ArrayList<Box> arrBox, ArrayList<Bomb> arrBomb){
//        move(arrBox);
//        while(canMove(orient, arrBox, arrBomb) == false){ // vong lap while nay gay slow khi chay nhieu chuong trinh khac cung luc_ chose Orient @@
//                generateOrient2();
//        }
//    }

    public boolean move(ArrayList<Box> arrBox, ArrayList<Bomb> arrBomb){
        if(delayMove())
            return true;
        int xR = x;
        int yR = y;
        switch (orient){
            case LEFT:
                x -= 1;
                break;
            case RIGHT:
                x += 1;
                break;
            case UP:
                y -= 1;
                break;
            case DOWN:
                y += 1;
                break;
        }
        boolean check = isImpactVsMap(arrBox);
        boolean check2 = isOutOfMap();
        boolean check3 = isImpactVsBomb(arrBomb);
        if(check2 == true || check == true || check3 == true){
            x = xR;
            y = yR;
            return false;
        }
        return true;
    }

    public boolean isImpactVsBomb(ArrayList<Bomb> arrBomb){
        for(int i = 0; i < arrBomb.size(); i++){
            if(this.getRec().intersects(arrBomb.get(i).getRec()))
                return true;
        }
        return false;
    }

    public boolean isImpactVsMap(ArrayList<Box> arrBox){
        for(int i = 0; i < arrBox.size(); i++){
            if(this.getRec().intersects(arrBox.get(i).getRec()))
                return true;
        }
        return false;
    }

    public boolean isOutOfMap(){
        if(type == 1){
            if(this.x + 47 >= GUIManager.PLAY_W ||
                    this.y + 69 >= GUIManager.PLAY_H ||
                    this.x < 0 ||
                    this.y < 0)
            return true;
        }
        else if(type == 2){
            if(this.x + 137 >= GUIManager.PLAY_W ||
                    this.y + 173 >= GUIManager.PLAY_H ||
                    this.x < 0 ||
                    this.y < 0)
                return true;
        }
        return false;
    }

    public void generateOrient(){ // uu tien huong di cu
        int percent = random.nextInt(101);
        if(percent > 60)
            orient = random.nextInt(4);
    }

    public void generateOrient2(){ // thay doi huong khong phai huong cu
        int temp = random.nextInt(4);
        if(orient != temp){
            orient = temp;
            return;
        }
        if(temp + 1 <= 3)
            orient = temp + 1;
        else
            orient = temp - 2;
    }
}
