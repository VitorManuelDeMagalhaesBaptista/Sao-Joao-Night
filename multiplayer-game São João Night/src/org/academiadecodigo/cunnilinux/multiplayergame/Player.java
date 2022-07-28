package org.academiadecodigo.cunnilinux.multiplayergame;

import java.io.PrintWriter;

public class Player {

    private String name;
    private PrintWriter outChat;
    private int money = 0;
    private int health = 100;
    private int foodCount = 0;

    private int drunkenness = 0;
    private int love = 0;

    public boolean isWasted() {
        return isWasted;
    }

    private boolean isWasted = false;

    public boolean isCatchGaroupa() {
        return catchGaroupa;
    }

    private boolean catchGaroupa = false;

    public int getMoney() {
        return money;
    }

    public int getHealth() {
        return health;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public int getDrunkenness() {
        return drunkenness;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public int getLove() {
        return love;
    }
    public void setWasted(){
        isWasted=true;
    }

    public Player(String name) {
        this.name = name;
    }


    private int finalScore;

    public Player(String name, PrintWriter outChat) {
        this.name = name;
        this.outChat = outChat;
    }

    public void finalResult(Player player) {
        finalScore = (int) ((player.getHealth() * 0.1) - (player.getDrunkenness() * 0.2) - (player.getMoney() * 0.3) + (player.getLove() * 0.4));
    }

    public String getName() {
        return name;
    }

    public void drink(Drinks drink, int place) {

        if (100 < drunkenness + drink.getAlcohol()) {
            drunkenness = 100;
            isWasted = true;
        } else {
            drunkenness = drunkenness + drink.getAlcohol();
        }

        money = money + drink.getPrice(place);

        if (health - (int) Math.round(drink.getAlcohol() / 2) < 0) {
            health = 0;
            isWasted = true;
        } else {
            health = health - (int) Math.round(drink.getAlcohol() / 2);
        }

        int random = (int) Math.floor(Math.random() * 2);
        if (random == 0) {
            outChat.println("Hmm, that tasted like heaven....");
        } else {
            outChat.println("That WAS a very nice " + drink.getName() + "!");
        }
    }


    public void food(Foods foods, int place) {

        if (health > 100 - foods.getHealthGain()) {
            health = 100;
        } else {
            health = health + foods.getHealthGain();
        }

        if (drunkenness < foods.getDrunkennessLoss()) {
            drunkenness = 0;
        } else {
            drunkenness = drunkenness - foods.getDrunkennessLoss();
        }


        if (foodCount > 100 - foods.getFoodCount()) {
            foodCount = 100;
            isWasted = true;
        } else {
            foodCount = foodCount + foods.getFoodCount();
        }

        money = money + foods.getPrice(place);

        int random = (int) Math.floor(Math.random() * 2);
        if (random == 0) {
            outChat.println("BRRRT! I was really hungry, now i'm better!");
        } else {
            outChat.println("That WAS a very nice " + foods.getName() + "!");
        }
    }

    public void payClassic(int price) {
        money = money + price;
    }

    public void love(int numLove) {
        love = love + numLove;
        if (love >= 100) {
            catchGaroupa = true;
        } else {
            catchGaroupa = false;
        }
    }

    public void lovePiropos(int minLove, int maxLove) {

        love = love + ((int) ((Math.random() * (maxLove - minLove)) + minLove));
        if (love >= 100) {
            catchGaroupa = true;
        } else {
            catchGaroupa = false;
        }
    }


}
