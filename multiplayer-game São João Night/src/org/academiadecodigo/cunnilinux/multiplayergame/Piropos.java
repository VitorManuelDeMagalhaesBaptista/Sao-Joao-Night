package org.academiadecodigo.cunnilinux.multiplayergame;

import static org.academiadecodigo.cunnilinux.multiplayergame.menus.Options.*;

public enum Piropos {
    P1(PIROPO1,20,10),
    P2(PIROPO2,25,15),
    P3(PIROPO3,10,-5),
    P4(PIROPO4,10,0),
    P5(PIROPO5,20,-20),
    P6(PIROPO6,25,5);
    private String piropo;
    private int maxLove;
    private int minLove;
    public int getMinLove() {
        return minLove;
    }
    public int getMaxLove() {
        return maxLove;
    }

        Piropos(String piropo, int maxLove, int minLove){
        this.piropo = piropo;
        this.maxLove = maxLove;
        this.minLove = minLove;
    }
}
