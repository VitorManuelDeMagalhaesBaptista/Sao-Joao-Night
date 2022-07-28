package org.academiadecodigo.cunnilinux.multiplayergame;

import org.academiadecodigo.cunnilinux.multiplayergame.menus.MenuTypes;

public enum Foods {
    BIFANA("bifana",5,3,20,5,10),
    KEBAB("kebab",6,4,20,5,15),
    FARTURA("fartura",3,2,10,-5,3),
    AMBERGA("amberga",5,4,20,5,10);

    private String name;
    private int priceTtias;
    private int priceTasca;
    private int foodCount;
    private int healthGain;
    private int drunkennessLoss;

    public int getHealthGain() {
        return healthGain;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public int getDrunkennessLoss() {
        return drunkennessLoss;
    }
    public String getName(){return name;}

    public int getPrice(int menuNum) {
        int price = 0;
        switch (menuNum) {
            case 1: // TASCA DAS TIAS
                price = priceTtias;
                break;
            case 3: // TASCA
                price = priceTasca;
        }
        return price;
    }

    Foods(String name, int priceTtias,int priceTasca, int foodCount,int healthGain,int drunkennessLoss){
        this.name = name;
        this.priceTtias = priceTtias;
        this.priceTasca = priceTasca;
        this.foodCount = foodCount;
        this.healthGain = healthGain;
        this.drunkennessLoss = drunkennessLoss;
    }


}
