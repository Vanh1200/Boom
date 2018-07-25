package com.vanh1200.models;

import com.vanh1200.GUI.GUIManager;
import sounds.GameSound;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class GameManager {
    private Bomber bomber;
    private ArrayList<Enemy> enemies;
    protected ArrayList<Bomb> bombs;
    private ArrayList<Box> boxes;
    private ArrayList<Box> shadowBoxes;
    private ArrayList<BombBang> bombBangs;
    private ArrayList<Item> items;
    private ArrayList<HighScore> highScores;
    private String backGround = "/Images/background_Play.png";
    public static int x;
    public static int y;
    private long changeEnemyOrient = 100000;
    public static final int LOSE = 0;
    public static final int NEXT_ROUND = 2;
    public static final int WIN = 1;
    public static final int RUNNING = 3;
    private int gameStatus;
    private int index_arr_animation = 0;
    private int count_arr_animation = 0;
    private int round = 1;
    public void initGame() {
        initHighScore("src/highscores/highscore.txt");
        gameStatus = RUNNING;
        switch(round){
            case 1:
                initPlayer(315, 540, 6); // speed from 6 -> 9
                initItem("src/maps/Map1/ITEM.txt");
                initBomb();
                initBombBang();
                initBox("src/maps/Map1/BOX.txt");
                initEnemy("src/maps/Map1/ENEMY.txt");
                break;
            case 2:
                GameManager.x = 315;
                GameManager.y = 270;
                bomber.setNewLife(315, 270);
                //initPlayer(315, 270, 7); // speed from 1 -> 10
                initItem("src/maps/Map2/ITEM.txt");
                initBomb();
                initBombBang();
                initBox("src/maps/Map2/BOX.txt");
                initEnemy("src/maps/Map2/ENEMY.txt");
                break;
            case 3:
                GameManager.x = 315;
                GameManager.y = 495;
                bomber.setNewLife(315, 495);
                //initPlayer(315, 495, 7); // speed from 1 -> 10
                initItem("src/maps/Map3/ITEM.txt");
                initBomb();
                initBombBang();
                initBox("src/maps/Map3/BOX.txt");
                initEnemy("src/maps/Map3/ENEMY.txt");
                break;
        }
    }

    private void drawBackGround(Graphics2D g2d){
        Image image = new ImageIcon(getClass().getResource(backGround)).getImage();
        g2d.drawImage(image, 0, 0, null);
    }

    private void drawAnimation(Graphics2D g2d){
        if(bomber.getSpeed() == Bomber.MAX_SPEED && bomber.getStatus() == Bomber.ALIVE){
            count_arr_animation++;
            if(count_arr_animation >= 50){
                count_arr_animation = 0;
                index_arr_animation++;
            }
            if(index_arr_animation > 2)
                index_arr_animation = 0;
            Image[] images = new Image[3];
            images[0] = new ImageIcon(getClass().getResource("/Images/smoke_cloud_04.PNG")).getImage();
            images[1] = new ImageIcon(getClass().getResource("/Images/smoke_cloud_03.PNG")).getImage();
            images[2] = new ImageIcon(getClass().getResource("/Images/smoke_cloud_02.PNG")).getImage();
            g2d.drawImage(images[index_arr_animation], bomber.getX() + 8, bomber.getY() + 20, 30, 30, null);
        }
    }

    private void drawInfo(Graphics2D g2d){
        Image imageTick = new ImageIcon(getClass().getResource("/Images/tick.png")).getImage();
        Image imageInfo = new ImageIcon(getClass().getResource("/Images/background_Info.png")).getImage();
        g2d.drawImage(imageInfo, 678 ,0, 280, 610, null);

        // Draw heart
        Image imageHeart = new ImageIcon(getClass().getResource("/Images/heart_1.png")).getImage();
        int xHeart, yHeart;
        xHeart = 700;
        yHeart = 200;
        for(int i = 0; i < bomber.getHeart(); i++){
            g2d.drawImage(imageHeart, xHeart, yHeart, null);
            xHeart += 36;
        }

        // Draw shoes
        Image imageShose = new ImageIcon(getClass().getResource("/Images/item_shoe.png")).getImage();
        int xShose = 700;
        int yShose = 360;
        int temp = 1;
        if(bomber.getSpeed() == 7)
            temp = 2;
        else if(bomber.getSpeed() == 8)
            temp = 3;
        else if(bomber.getSpeed() == 9){
            temp = 4;
            g2d.drawImage(imageTick, 850, 360, 30, 30, null);
        }
        for(int i = 0; i < temp; i++){
            g2d.drawImage(imageShose, xShose, yShose, 24, 24, null);
            xShose += 36;
        }

        //Draw Bomb
        Image imageBoom = new ImageIcon(getClass().getResource("/Images/bomb.png")).getImage();
        int xSizeBoom = 700;
        int ySizeBoom = 280;
        if(bomber.getNumberOfBomb() == 4)
            g2d.drawImage(imageTick, 850, 280, 30, 30, null);
        for(int i = 0; i < bomber.getNumberOfBomb(); i++){
            g2d.drawImage(imageBoom, xSizeBoom, ySizeBoom, 24, 24, null);
            xSizeBoom += 36;
        }


        //Draw Power
        Image imagePower1 = new ImageIcon(getClass().getResource("/Images/bombbang_right_1.png")).getImage();
        Image imagePower2 = new ImageIcon(getClass().getResource("/Images/bombbang_right_2.png")).getImage();
        if(bomber.getSizeOfBomb() == 1){
            g2d.drawImage(imagePower1, 700, 440, 75, 33, null);
        }else if(bomber.getSizeOfBomb() >= 2){
            g2d.drawImage(imagePower1, 700, 440, 108, 33, null);
            g2d.drawImage(imageTick, 850, 440, 30, 30,null);
        }

        //Draw String
        g2d.setColor(Color.PINK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 25));
        g2d.drawString("Your Heart: ", 720, 170);
        g2d.drawString("Your Score: ", 720, 90);
        g2d.drawString("Number: ", 720, 250);
        g2d.drawString("Speed: ", 720, 330);
        g2d.drawString("Power:", 720, 410);
        g2d.drawString(String.valueOf(bomber.getScore()), 760, 120);
    }

    public Bomber getBomber() {
        return bomber;
    }

    private void initBomb(){
        bombs = new ArrayList<>();
    }

    private void initBombBang(){
        bombBangs = new ArrayList<>();
    }

    private void initPlayer(int x, int y, int speed){
        GameManager.x = x;
        GameManager.y = y;
        bomber = new Bomber(x, y, speed);
    }


    private void initEnemy(String enemyPath){
        enemies = new ArrayList<>();
        try{
            FileReader file = new FileReader(enemyPath);
            BufferedReader reader = new BufferedReader(file);
            String line = "";
            int row = 0;
            while((line = reader.readLine()) != null){
                for(int i = 0; i < line.length(); i++){
                    char c = line.charAt(i);
                    int code = Integer.parseInt(c + "");
                    if(code == 1){
                        int xEnemy = i * 45;
                        int yEnemy = row * 45;
                        Enemy enemy = new Enemy(xEnemy, yEnemy, 3, 1,2);
                        enemies.add(enemy);
                    }
                    else if(code == 2){
                        int xEnemy = i * 45;
                        int yEnemy = row * 45;
                        Enemy enemy = new Enemy(xEnemy, yEnemy, 3, 2,2);
                        enemies.add(enemy);
                    }
                }
                row++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void initBox(String boxPath){
        boxes = new ArrayList<>();
        shadowBoxes = new ArrayList<>();
        try{
            FileReader file = new FileReader(boxPath);
            BufferedReader reader = new BufferedReader(file);
            String line = "";
            int row = 0;
            while((line = reader.readLine()) != null){
                for(int i = 0; i < line.length(); i++){
                    char c = line.charAt(i);
                    int code = Integer.parseInt(c + "");
                    if(code == 1){
                        int xBox1 = i * 45;
                        int yBox1 = row * 45;
                        Box box = new Box(xBox1, yBox1, 1, "/Images/box1.png");
                        boxes.add(box);
                        Box shadowBox = new Box(xBox1, yBox1 - 22, code, "/Images/shawdow1.png");
                        shadowBoxes.add(shadowBox);
                    }
                    else if(code == 2){
                        int xBox = i * 45;
                        int yBox = row * 45;
                        Box box = new Box(xBox, yBox, 2, "/Images/box2.png");
                        boxes.add(box);
                        Box shadowBox = new Box(xBox, yBox - 7, code, "/Images/shawdow2.png");
                        shadowBoxes.add(shadowBox);
                    }
                }
                row++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private void initItem(String itemPath){
        items = new ArrayList<>();
        try{
            FileReader file = new FileReader(itemPath);
            BufferedReader reader = new BufferedReader(file);
            String line = "";
            int row = 0;
            while((line = reader.readLine()) != null){
                for(int i = 0; i < line.length(); i++){
                    char c = line.charAt(i);
                    int code = Integer.parseInt(c + "");
                    if(code == 1){
                        int xItem = i * 45;
                        int yItem = row * 45;
                        Item item = new Item(xItem, yItem, code);
                        items.add(item);
                    }else if(code == 2){
                        int xItem = i * 45;
                        int yItem = row * 45;
                        Item item = new Item(xItem, yItem, code);
                        items.add(item);
                    }
                    else  if(code == 3){
                        int xItem = i * 45;
                        int yItem = row * 45;
                        Item item = new Item(xItem, yItem, code);
                        items.add(item);
                    }
                }
                row++;
            }
        }catch(Exception e){
            e.printStackTrace();
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

    public synchronized void draw(Graphics2D g2d){
        drawBackGround(g2d);
        for(Bomb bomb: bombs){
            bomb.draw(g2d);
        }
        for(BombBang bombBang: bombBangs){
            bombBang.draw(g2d);
        }
        for(Item item: items){
            item.draw(g2d);
        }
        for(Box box: boxes){
            box.draw(g2d);
        }
        bomber.draw(g2d);
        for(Enemy enemy: enemies){
            enemy.draw(g2d);
        }
        for(Box box: shadowBoxes){
            box.draw(g2d);
        }
        drawText(g2d);
        drawInfo(g2d);
        drawAnimation(g2d);
    }


    public void bomberMove(int orient){
        if(bomber.getStatus() == Bomber.DEAD)
            return;
        bomber.changeOrient(orient);
        bomber.move(boxes);
    }

    public void enemyMove(){
        for(int i = 0; i < enemies.size(); i++){
            if(enemies.get(i).move(boxes, bombs) == false)
                enemies.get(i).generateOrient2();
        }
        changeAllEnemyMove();
    }

    public void changeAllEnemyMove(){
        if(changeEnemyOrient == 0) changeEnemyOrient = 100000;
        changeEnemyOrient--;
        if(changeEnemyOrient % (4500) == 0)
            for(int i = 0; i < enemies.size(); i++){
                enemies.get(i).generateOrient();
            }
    }

    public void bombing(){
        if(!bomber.canBomb(bombs, enemies))
            return;
        bomber.bombing(bombs);
    }

    public synchronized void checkDeadTimeBomb(){
        long currentTime = System.currentTimeMillis();
        for(int i = 0; i < bombs.size(); i++){
            if(currentTime - bombs.get(i).getTimeStart() >= bombs.get(i).getTimeAlive()){
                BombBang bombBang = new BombBang(bombs.get(i).getX(), bombs.get(i).getY(), bombs.get(i).getSize(), 250, boxes);
                bombs.remove(i);
                bombBangs.add(bombBang);
                GameSound.getIstance().getAudio(GameSound.BONG_BANG).play();
                return;
            }
        }
    }

    public synchronized void checkDeadTimeBombBang(){
        long currentTime = System.currentTimeMillis();
        for(int i = 0; i < bombBangs.size(); i++){
            if(currentTime - bombBangs.get(i).getTimeStart() >= bombBangs.get(i).getTimeAlive()){
                bombBangs.remove(i);
                return;
            }
        }
    }

    public synchronized void checkDeadTimeItem(){
        long currentTime = System.currentTimeMillis();
        for(int i = 0; i < items.size(); i++){
            if(currentTime - items.get(i).getTimeStart() >= items.get(i).getTimeAlive() && items.get(i).getTimeStart() != 0){
                items.remove(i);
                return;
            }
        }
    }

    public synchronized void checkBombBangImpactVsBomb(){
        for(int i = 0; i < bombs.size(); i++){
            for(int j = 0; j < bombBangs.size(); j++){
                if(bombBangs.get(j).isImpactVsBomb(bombs.get(i))){
                    BombBang bombBang = new BombBang(bombs.get(i).getX(), bombs.get(i).getY(), bombs.get(i).getSize(),  250, boxes);
                    bombs.remove(i);
                    bombBangs.add(bombBang);
                    return;
                }
            }
        }
    }

    public synchronized void checkBombBangImpactVsEnemy(){
        for(int i = 0; i < enemies.size(); i++){
            for(int j = 0; j < bombBangs.size(); j++){
                if(bombBangs.get(j).isImpactVsEnemy(enemies.get(i))) {
                    if (enemies.get(i).getType() == Enemy.MONSTER) {
                        enemies.remove(i);
                        GameSound.getIstance().getAudio(GameSound.MONSTER_DIE).play();
                        if (bomber.getStatus() == Bomber.ALIVE)
                            bomber.setScore(bomber.getScore() + 10);
                        return;
                    }
                    if (enemies.get(i).getType() == Enemy.BOSS){
                        if(bombBangs.get(j).getTimeStart() + 50 == System.currentTimeMillis())
                            enemies.get(i).setHealthBoss(enemies.get(i).getHealthBoss() - 2);
                        if(enemies.get(i).getHealthBoss() == 0){
                            enemies.remove(i);
                            if (bomber.getStatus() == Bomber.ALIVE)
                                bomber.setScore(bomber.getScore() + 30);
                        }
                        GameSound.getIstance().getAudio(GameSound.MONSTER_DIE).play();
                    }

                }
            }
        }
    }

    public boolean isBomberImpactVsEnemy(){
        for(int i = 0; i < enemies.size(); i++){
            if(enemies.get(i).getRec().intersects(bomber.getRec())){
                return true;
            }
        }
        return false;
    }

    public boolean isBomberImpactVsBombBang(){
        for(int i = 0; i < bombBangs.size(); i++){
            if(bombBangs.get(i).isImpactVsBomber(bomber)){
                return true;
            }
        }
        return false;
    }

    public void checkDead(){
        if(isBomberImpactVsBombBang() && bomber.getStatus() == Bomber.ALIVE){
            bomber.setHeart(bomber.getHeart() - 1);
            bomber.setImageAtLeft(new ImageIcon(getClass().getResource("/Images/bomber_dead.png")).getImage());
            bomber.setOrient(Bomber.LEFT);
            bomber.setStatus(Bomber.DEAD);
            GameSound.getIstance().getAudio(GameSound.BOMBER_DIE).play();
        }
        else if(isBomberImpactVsEnemy() &&  bomber.getStatus() == Bomber.ALIVE){
            bomber.setHeart(bomber.getHeart() - 1);
            bomber.setImageAtLeft(new ImageIcon(getClass().getResource("/Images/ghost.png")).getImage());
            bomber.setOrient(Bomber.LEFT);
            bomber.setStatus(Bomber.DEAD);
            GameSound.getIstance().getAudio(GameSound.BOMBER_DIE).play();
        }
    }

    public synchronized void checkBombBangImpactVsBox(){
        for(int i = 0; i < bombBangs.size(); i++){
            for(int j = 0; j < boxes.size(); j ++){
                if(bombBangs.get(i).isImpactVsBox(boxes.get(j)) && boxes.get(j).getType() == Box.canBeBombed){
                    boxes.remove(j);
                    shadowBoxes.remove(shadowBoxes.get(j));
                    return;
                }
            }
        }
    }

    public synchronized void checkBombBangImpactVsItem(){
        for(int i = 0; i < bombBangs.size(); i++){
            for(int j = 0; j < items.size(); j++){
                if(bombBangs.get(i).isImpactVsItem(items.get(j))){
                    if(items.get(j).getTimeStart() == 0){
                        items.get(j).setTimeStart(System.currentTimeMillis());
                    }else if(bombBangs.get(i).getTimeStart() > items.get(j).getTimeStart()){
                        items.remove(j);
                        return;
                    }
                }
            }
        }
    }

//    public synchronized boolean areItemBehindBox(Item item, ArrayList<Box> arrBox){
//        for(int i = 0; i < arrBox.size(); i++){
//            if(item.getRec().intersects(arrBox.get(i).getRec()))
//                return true;
//        }
//        return false;
//    }

    public synchronized void checkBomberImpactItem(){
        for(int i = 0; i < items.size(); i++){
            if(bomber.getRec().intersects(items.get(i).getRec())){
                int type = items.get(i).getType();
                items.remove(i);
                GameSound.getIstance().getAudio(GameSound.ITEM).play();
                switch (type){
                    case Item.BOMB:
                        bomber.setNumberOfBomb(bomber.getNumberOfBomb() + 1);
                        break;
                    case Item.BOMB_SIZE:
                        bomber.setSizeOfBomb(bomber.getSizeOfBomb() + 1);
                        break;
                    case Item.SHOES:
                        bomber.setSpeed(bomber.getSpeed() + 1);
                        break;
                }
            }
        }
    }

    public void setBomberNewLife(){
        bomber.setNewLife(GameManager.x, GameManager.y);
    }

    public void drawText(Graphics2D g2d){
        switch (gameStatus){
            case GameManager.LOSE:
                g2d.setColor(Color.BLUE);
                g2d.setFont(new Font("TimesRoman", Font.PLAIN, 60));
                g2d.drawString("YOU LOSE", GUIManager.PLAY_W / 2 - 150, GUIManager.PLAY_H / 2);
                break;
            case GameManager.WIN:
                g2d.setColor(Color.BLUE);
                g2d.setFont(new Font("TimesRoman", Font.PLAIN, 60));
                g2d.drawString("YOU WIN", GUIManager.PLAY_W / 2 - 150, GUIManager.PLAY_H / 2);
                break;
            case GameManager.NEXT_ROUND:
                g2d.setColor(Color.BLUE);
                g2d.setFont(new Font("TimesRoman", Font.PLAIN, 60));
                g2d.drawString("NEXT ROUND", GUIManager.PLAY_W / 2 - 150, GUIManager.PLAY_H / 2);
                break;
            default:
                break;
        }
    }

    public void checkRound(){
        if(bomber.getHeart() == 0 && gameStatus == GameManager.RUNNING){
            gameStatus = GameManager.LOSE;
            GameSound.getIstance().stop();
            GameSound.getIstance().getAudio(GameSound.LOSE).play();
            saveHighScore();
            return;
        }
        if(enemies.size() == 0 && round < 3 && gameStatus == GameManager.RUNNING){
            round++;
            gameStatus = GameManager.NEXT_ROUND;
            GameSound.getIstance().stop();
            GameSound.getIstance().getAudio(GameSound.WIN).play();
            return;
        }
        if(enemies.size() == 0 && round == 3 && gameStatus == GameManager.RUNNING){
            gameStatus = GameManager.WIN;
            GameSound.getIstance().stop();
            GameSound.getIstance().getAudio(GameSound.WIN).play();
            saveHighScore();
            return;
        }
    }

    public void saveHighScore(){
            int min = 0;
            if(highScores.size() > 0)
                min = highScores.get(highScores.size() - 1).getScore();
            if (bomber.getScore() > min || highScores.size() < 10 && bomber.getScore() != 0){
                String name = JOptionPane.showInputDialog("Input your name: ");
                if(name.equals(null) || name.equals(""))
                    name = "anonymous";
                highScores.add(new HighScore(name, bomber.getScore()));
                Collections.sort(highScores);
            }
            if(highScores.size() > 10){
                highScores.remove(highScores.size() - 1);
            }
            try{
                FileWriter fileWriter = new FileWriter("src/highscores/highscore.txt");
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                for(int i = 0; i < highScores.size(); i++) {
                    String str = highScores.get(i).getName() + ":" + highScores.get(i).getScore();
                    bufferedWriter.write(str);
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            }
            catch (Exception e){
            }
    }

    public int getGameStatus(){
        return gameStatus;
    }

    public void setGameStatus(int gameStatus){
        this.gameStatus = gameStatus;
    }
}
