package org.academiadecodigo.cunnilinux.multiplayergame;

import org.academiadecodigo.cunnilinux.multiplayergame.menus.MenuTypes;

public enum Drinks {
    CERVEJA("beer", 2, 5, 1, 5),
    SANGRIA("sangria", 3, 6, 2, 8),
    VINHO("wine", 5, 10, 2, 10),
    WHISKY("whisky", 5, 15, 5, 15),
    COCKTAIL("cocktail", 10, 30, 10, 20);

    private String name;
    private int priceTtias;
    private int priceClassic;
    private int priceTasca;
    private int alcohol;

    public int getAlcohol() {
        return alcohol;
    }

        public String getName() {
        return name;
    }

    public int getPrice(int menuNum) {
        int price = 0;
        switch (menuNum) {
            case 1: // TASCA DAS TIAS
                price = priceTtias;
                break;
            case 2: // CLASSIC
                price = priceClassic;
                break;
            case 3: // TASCA
                price = priceTasca;
        }
        return price;
    }



    Drinks(String name, int priceTtias, int priceClassic, int priceTasca, int alcohol) {
        this.name = name;
        this.priceTtias = priceTtias;
        this.priceClassic = priceClassic;
        this.priceTasca = priceTasca;
        this.alcohol = alcohol;

    }

}
