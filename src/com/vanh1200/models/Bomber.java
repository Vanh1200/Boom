package com.vanh1200.models;



import com.vanh1200.GUI.GUIManager;
import sounds.GameSound;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Bomber {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;
    public final static int ALIVE = 1;
    public final static int DEAD = 0;
    private int x;
    private int y;
    private int status;
    private int heart;
    private int score;
    private Image images[];
    private int orient;
    private int speed; // from 1 -> 10;
    private int sizeOfBomb;
    private int numberOfBomb;
    private int countSpeed = 1000000;

    public Bomber(int x, int y, int speed){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.orient = DOWN;
        this.score = 0;
        images = new Image[4];
        status = Bomber.ALIVE;
        numberOfBomb = 1;
        sizeOfBomb = 1;
        heart = 2;
        images[0] = new ImageIcon(getClass()
                .getResource("/Images/bomber_left.png"))
                .getImage();
        images[1] = new ImageIcon(getClass()
                .getResource("/Images/bomber_right.png"))
                .getImage();
        images[2] = new ImageIcon(getClass()
                .getResource("/Images/bomber_up.png"))
                .getImage();
        images[3] = new ImageIcon(getClass()
                .getResource("/Images/bomber_down.png"))
                .getImage();
    }

    public int getSizeOfBomb() {
        return sizeOfBomb;
    }

    public void setSizeOfBomb(int sizeOfBomb) {
        this.sizeOfBomb = sizeOfBomb;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setNumberOfBomb(int temp){
        if(temp > 4)
            temp = 4;
        numberOfBomb = temp;
    }

    public int getOrient() {
        return orient;
    }

    public void setOrient(int orient) {
        this.orient = orient;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        if(speed > 9) speed = 9;
        this.speed = speed;
    }

    public void setNewLife(int x, int y){

        this.x = x;
        this.y = y;
        this.status = ALIVE;
        images[0] = new ImageIcon(getClass()
                .getResource("/Images/bomber_left.png"))
                .getImage();
    }

    public void setImageAtLeft(Image image){
        this.images[0] = image;
    }

    public int getNumberOfBomb(){
        return numberOfBomb;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getStatus(){
        return status;
    }

    public void draw(Graphics2D g2d){
            g2d.drawImage(images[orient], x, y - 20, null);
    }
    public Rectangle getRec(){
        return new Rectangle(x, y, 45, 45);
    }

    public boolean delayMove(){
        if (countSpeed == 0) countSpeed = 1000000;
        countSpeed--;
        if((countSpeed % (11 - speed)) != 0)
            return true;
        return false;
    }

    public void move(ArrayList<Box> arrBox){
        if(delayMove())
            return;
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
        if(check2 == true){
            x = xR;
            y = yR;
        }
        if(check == true){
            fixMove(orient, arrBox);
        }
    }

    public void fixMove(int orient, ArrayList<Box> arrBox){
        switch (orient){
            case UP:
                int mGap = gapUpDown(arrBox);
                y++;
                if(mGap >= 22)
                    x++;
                else if(mGap <= -22)
                    x--;
                break;
            case DOWN:
                int mGap1 = gapUpDown(arrBox);
                y--;
                if(mGap1 >= 22)
                    x++;
                else if(mGap1 <= -22)
                    x--;
                break;
            case LEFT:
                int mGap2 = gapLeftRight(arrBox);
                x++;
                if(mGap2 >= 22)
                    y++;
                else if(mGap2 <= -22)
                    y--;
                break;
            case RIGHT:
                int mGap3 = gapLeftRight(arrBox);
                x--;
                if(mGap3 >= 22)
                    y++;
                else if(mGap3 <= -22)
                    y--;
                break;
        }
    }

    public int gapUpDown(ArrayList<Box> arrBox){
        int count = 0;
        int mGap = 0;
        for(int i = 0; i < arrBox.size(); i++){
            if(this.getRec().intersects(arrBox.get(i).getRec())){
                count++;
                mGap = this.getX() - arrBox.get(i).getX();
            }
        }
        if(count > 1) return 0;
        return mGap;
    }

    public int gapLeftRight(ArrayList<Box> arrBox){
        int count = 0;
        int mGap = 0;
        for(int i = 0; i < arrBox.size(); i++){
            if(this.getRec().intersects(arrBox.get(i).getRec())){
                count++;
                mGap = this.getY() - arrBox.get(i).getY();
            }
        }
        if(count > 1) return 0;
        return mGap;
    }

    public boolean isImpactVsMap(ArrayList<Box> arrBox){
        for(int i = 0; i < arrBox.size(); i++){
            if(this.getRec().intersects(arrBox.get(i).getRec()))
                return true;
        }
        return false;
    }

    public boolean isOutOfMap(){
        if(x + 47 >= GUIManager.PLAY_W ||
                y + 69 >= GUIManager.PLAY_H ||
                x < 0 ||
                y < 0)
            return true;
        return false;
    }

    public void changeOrient(int orient){
        this.orient = orient;
    }

    public boolean canBomb(ArrayList<Bomb> arrBomb, ArrayList<Enemy> arrEnemy){
        //calculate position where bomb should be put
        int xBomb = 0, yBomb = 0;
        int xBomber = this.x;
        int yBomber = this.y;
        boolean check = true;
        for(int i = 0; i <= 14; i++){
            if(xBomber <= i * 45 + 22){
                xBomb = i * 45;
                check = false;
                break;
            }
        }
        if(check == true) xBomb = 13 * 45;
        check = true;
        for(int i = 0; i <= 12; i++){
            if(yBomber <= i * 45 + 22){
                yBomb = i * 45;
                check = false;
                break;
            }
        }
        if(check == true) yBomb = 13 * 45;

        Rectangle recPos = new Rectangle(xBomb, yBomb, 45, 45);
        for(Bomb bomb: arrBomb){
            if(bomb.getRec().intersects(recPos))
                return false;
        }
        for(Enemy enemy: arrEnemy){
            if(enemy.getRec().intersects(recPos))
                return false;
        }
        if(arrBomb.size() >= this.getNumberOfBomb())
            return false;
        return true;
    }

    public void bombing(ArrayList<Bomb> bombs){
        if(status == DEAD) return;
        //calculate postion
        int xBomb = 0, yBomb = 0;
        int xBomber = this.x;
        int yBomber = this.y;
        boolean check = true;
        for(int i = 0; i <= 14; i++){
            if(xBomber <= i * 45 + 22){
                xBomb = i * 45;
                check = false;
                break;
            }
        }
        if(check == true) xBomb = 13 * 45;
        check = true;
        for(int i = 0; i <= 12; i++){
            if(yBomber <= i * 45 + 22){
                yBomb = i * 45;
                check = false;
                break;
            }
        }
        if(check == true) yBomb = 13 * 45;

        Bomb b1 = new Bomb(xBomb, yBomb, sizeOfBomb, 3000);
        bombs.add(b1);

        GameSound.getIstance().getAudio(GameSound.BOMB).play();
    }
}
