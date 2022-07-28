package org.academiadecodigo.cunnilinux.multiplayergame;

public enum Accidents {
    HITANDRUN(0,-50,0),
    ANGRYDRUNK(0,-15,-20),
    ROBBED(50,0,0);


    private int moneyLoss;
    private int healthLoss;
    private int popularityLoss;

    Accidents(int moneyLoss, int healthLoss, int popularityLoss){
        this.moneyLoss = moneyLoss;
        this.healthLoss = healthLoss;
        this.popularityLoss = popularityLoss;
    }

}
