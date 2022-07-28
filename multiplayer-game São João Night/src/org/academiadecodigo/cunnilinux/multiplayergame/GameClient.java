package org.academiadecodigo.cunnilinux.multiplayergame;

import org.academiadecodigo.cunnilinux.multiplayergame.menus.ConsoleColors;
import org.academiadecodigo.cunnilinux.multiplayergame.menus.Menu;
import org.academiadecodigo.cunnilinux.multiplayergame.menus.MenuTypes;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

import static org.academiadecodigo.cunnilinux.multiplayergame.menus.MenuTypes.*;
import static org.academiadecodigo.cunnilinux.multiplayergame.menus.Options.*;

public class GameClient implements Runnable {
    private Player player;
    private String name;
    private boolean goodToGo;
    public boolean isGoodToGo() {return goodToGo;}
    private boolean inTascaChat;
    private boolean inClassicChat;
    private MainServer mainServer;
    private Socket clientSocket;
    private Logger logger;
    private Menu[] menus;
    private PrintWriter outChat;
    private PrintStream outMenu;
    private BufferedReader in;
    long startTime=0;

    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    public GameClient(String name, Socket clientSocket, Logger logger, MainServer chatServer) throws IOException {
        //this.player = new Player(name);
        this.clientSocket = clientSocket;
        this.logger = logger;
        this.mainServer = chatServer;
        goodToGo = false;
        inTascaChat = false;
        inClassicChat = false;
        outChat = new PrintWriter(clientSocket.getOutputStream(), true);
        outMenu = new PrintStream(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        menus = factory();
    }

    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    // RUN
    @Override
    public void run() {
        try {

            show(INTRO);

            goodToGo = true;

            while (!mainServer.allGood()) {
                Thread.sleep(300);
            }

            //startTime = System.currentTimeMillis();
            //System.out.println(startTime);
            show(RUASAOJOAO);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public Player getPlayer() {return player;}

    public void setIsInChat(MenuTypes menuType,boolean bool){
        switch (menuType) {
            case TASCA:
                inTascaChat = bool;
                break;
            case CLASSIC:
                inClassicChat = bool;
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    // METHODS FOR RUN
    public void goToChat(MenuTypes menuType) {
        try {

            setIsInChat(menuType,true);
            Scanner textReader = new Scanner(clientSocket.getInputStream());
            String message = "";

            while (!message.equals("\"quit\"")) {
                message = textReader.nextLine();
                mainServer.broadCastChat(menuType,message);
            }

            setIsInChat(menuType,false);
            show(menuType);

        } catch (IOException e) {

            throw new RuntimeException(e);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isInChat(MenuTypes menuType) {
        boolean bool = false;
        System.out.println(menuType);
        switch (menuType) {
            case TASCA:
                bool = inTascaChat;
                break;
            case CLASSIC:
                bool = inClassicChat;
        }
        System.out.println(bool);
        return bool;
    }

    public void print(String message) {
        // REJECT EMPTY MESSAGES
        boolean hasNonSpace = false;
        for (String s : message.split("")) {
            if (!s.equals(" ")) {
                hasNonSpace = true;
                break;
            }
        }
        if (!message.equals("") && hasNonSpace) outChat.println(Thread.currentThread().getName()+ ": " + message);
    }

    public String getName() {
        return Thread.currentThread().getName();
    }

    public void setName(String name) {
        //this.name = name;
        Thread.currentThread().setName(name);
    }

    public Menu[] factory() throws IOException {
        Menu[] menus = new Menu[MenuTypes.values().length];
        for (int i = 0; i < MenuTypes.values().length; i++) {
            menus[i] = new Menu(MenuTypes.values()[i], clientSocket.getInputStream(), outMenu);
        }
        return menus;
    }


    public void gameOver() throws IOException, InterruptedException {
        player.setWasted();
        mainServer.setFinalSheet("");
        mainServer.finalSheet();
        outChat.println(ConsoleColors.RED_BOLD_BRIGHT + ":::::::::::::::::::::::::::");
        outChat.println(ConsoleColors.RED_BOLD_BRIGHT + "::::::   GAME OVER   ::::::");
        outChat.println(ConsoleColors.RED_BOLD_BRIGHT + ":::::::::::::::::::::::::::");
        outChat.println(ConsoleColors.RED_UNDERLINED + "Players' score:" + ConsoleColors.RESET);
        outChat.println(mainServer.getFinalSheet());
    }

    public void show(MenuTypes menuType) throws IOException, InterruptedException {
        //if(startTime != 0 && System.currentTimeMillis()-startTime >= 20000) mainServer.gameOverAll();
        // WAIT FOR CHOICE/INPUT
        if (menuType == PLAYERNAME) {
            System.out.println(PLAYERNAME_HEADER);
            String name = menus[menuType.ordinal()].getStringChoice();
            setName(name);
            player = new Player(name, outChat);
            //show(SETGROUPSIZE);
        } else {
/*            if (menuType == SETGROUPSIZE) {
                waitingRoom();
            } else {*/
            int answerIndex = menus[menuType.ordinal()].getIntChoice();
            switch (menuType) {
                case INTRO:
                    if (answerIndex == 1) show(PLAYERNAME);
                    if (answerIndex == 2) show(RULES);
                    if (answerIndex == 3) clientSocket.close();//show(FINAL_SCORE_MENU);
                    break;
/*                  case SETGROUPSIZE:
                    //if (answerIndex >= MIN_PLAYERS && answerIndex <= MAX_PLAYERS) {
                    if (answerIndex == 3) {
                        groupSize = answerIndex;
                        show(WAITINGROOM);
                        // START SERVER with playerName
                    }
                    break;*/
/*                        case WAITINGROOM:
                    waitingRoom();
                    break;*/
                case RULES:
                    if (answerIndex == 1) show(INTRO);
                    break;
                case RUASAOJOAO:
                    if (answerIndex == 1) show(JUMPFIRE);
                    if (answerIndex == 2) show(TASCATIAS);
                    if (answerIndex == 3) show(TASCA);
                    if (answerIndex == 4) {
                        player.payClassic(10);
                        show(CLASSIC);
                    }
                    if (answerIndex == 5) show(PIROPO_RUA);
                    if (answerIndex == 6) show(INTRO);
                    //DANÇAR NUMA MARCHA;
                case PIROPO_RUA:
                    if (answerIndex == 1) {
                        player.lovePiropos(Piropos.P1.getMinLove(), Piropos.P1.getMaxLove());
                        show(PIROPO_RUA);
                    }
                    if (answerIndex == 2) {
                        player.lovePiropos(Piropos.P2.getMinLove(), Piropos.P2.getMaxLove());
                        show(PIROPO_RUA);
                    }
                    if (answerIndex == 3) {
                        player.lovePiropos(Piropos.P3.getMinLove(), Piropos.P3.getMaxLove());
                        show(PIROPO_RUA);
                    }
                    if (answerIndex == 4) {
                        player.lovePiropos(Piropos.P4.getMinLove(), Piropos.P4.getMaxLove());
                        show(PIROPO_RUA);
                    }
                    if (answerIndex == 5) {
                        player.lovePiropos(Piropos.P5.getMinLove(), Piropos.P5.getMaxLove());
                        show(PIROPO_RUA);
                    }
                    if (answerIndex == 6) {
                        player.lovePiropos(Piropos.P6.getMinLove(), Piropos.P6.getMaxLove());
                        show(PIROPO_RUA);
                    }
                    if (answerIndex == 7) show(RUASAOJOAO);
                    break;
                case JUMPFIRE:
                    if (answerIndex == 1) {
                        player.love(5);
                        show(JUMPFIRE);
                    }
                    if (answerIndex == 2) show(RUASAOJOAO);
                    break;
                case TASCATIAS:
                    if (answerIndex == 1) show(TASCATIASBAR);
                    if (answerIndex == 2) show(RUASAOJOAO);
                    break;
                case TASCATIASBAR:
                    if (answerIndex == 1) show(TASCATIASFOODS);
                    if (answerIndex == 2) show(TASCATIASDRINKS);
                    if (answerIndex == 3) show(TASCATIAS);
                    break;
                case TASCATIASDRINKS:
                    if (answerIndex == 1) {
                        player.drink(Drinks.CERVEJA, 1);
                        if (player.isWasted() == true) {

                            show(GAME_OVER);
                        } else {
                            show(TASCATIASDRINKS);
                        }
                    }
                    if (answerIndex == 2) {

                        player.drink(Drinks.SANGRIA, 1);
                        if (player.isWasted() == true) {
                            show(GAME_OVER);
                        } else {
                            show(TASCATIASDRINKS);
                        }
                    }
                    if (answerIndex == 3) {
                        player.drink(Drinks.VINHO, 1);
                        if (player.isWasted() == true) {
                            show(GAME_OVER);
                        } else {
                            show(TASCATIASDRINKS);
                        }
                    }
                    if (answerIndex == 4) {
                        player.drink(Drinks.WHISKY, 1);
                        if (player.isWasted() == true) {
                            show(GAME_OVER);
                        } else {
                            show(TASCATIASDRINKS);
                        }
                    }
                    if (answerIndex == 5) {
                        player.drink(Drinks.COCKTAIL, 1);
                        if (player.isWasted() == true) {
                            show(GAME_OVER);
                        } else {
                            show(TASCATIASDRINKS);
                        }
                    }
                    if (answerIndex == 6) show(TASCATIASBAR);
                    break;
                case TASCATIASFOODS:
                    if (answerIndex == 1) {
                        player.food(Foods.BIFANA, 1);
                        if (player.isWasted() == true) {
                            show(GAME_OVER);
                        } else {
                            show(TASCATIASFOODS);
                        }
                    }
                    if (answerIndex == 2) {
                        player.food(Foods.KEBAB, 1);
                        if (player.isWasted() == true) {
                            show(GAME_OVER);
                        } else {
                            show(TASCATIASFOODS);
                        }
                    }
                    if (answerIndex == 3) {
                        player.food(Foods.FARTURA, 1);
                        if (player.isWasted() == true) {
                            show(GAME_OVER);
                        } else {
                            show(TASCATIASFOODS);
                        }
                    }
                    if (answerIndex == 4) {
                        player.food(Foods.AMBERGA, 1);
                        if (player.isWasted() == true) {
                            show(GAME_OVER);
                        } else {
                            show(TASCATIASFOODS);
                        }
                    }
                    if (answerIndex == 5) show(TASCATIASBAR);
                    break;
                case CLASSIC:
                    if (answerIndex == 1) show(CLASSICBAR);
                    if (answerIndex == 2) show(CLASSICSHOW);
                    if (answerIndex == 3) show(LAPDANCE);
                    if (answerIndex == 4) goToChat(CLASSIC);//CHAT FALAR COM A MALTA;
                    if (answerIndex == 5) show(RUASAOJOAO);
                case CLASSICBAR:
                    if (answerIndex == 1) {
                        player.drink(Drinks.CERVEJA, 2);
                        if (player.isWasted() == true) {
                            show(GAME_OVER);
                        } else {
                            show(CLASSICBAR);
                        }
                    }
                    if (answerIndex == 2) {
                        player.drink(Drinks.SANGRIA, 2);
                        if (player.isWasted() == true) {
                            show(GAME_OVER);
                        } else {
                            show(CLASSICBAR);
                        }
                    }
                    if (answerIndex == 3) {
                        player.drink(Drinks.VINHO, 2);
                        if (player.isWasted() == true) {
                            show(GAME_OVER);
                        } else {
                            show(CLASSICBAR);
                        }
                    }
                    if (answerIndex == 4) {
                        player.drink(Drinks.WHISKY, 2);
                        if (player.isWasted() == true) {
                            show(GAME_OVER);
                        } else {
                            show(CLASSICBAR);
                        }
                    }
                    if (answerIndex == 5) {
                        player.drink(Drinks.COCKTAIL, 2);
                        if (player.isWasted() == true) {
                            show(GAME_OVER);
                        } else {
                            show(CLASSICBAR);
                        }
                    }
                    if (answerIndex == 6) {
                        show(CLASSIC);
                    }
                    break;
                case CLASSICSHOW:
                    if (answerIndex == 1) show(CLASSIC);
                    break;
                case LAPDANCE:
                    if (answerIndex == 1) {
                        player.payClassic(40);
                        show(CLASSIC);
                    }
                    break;

                case TASCA:
                    if (answerIndex == 1) show(TASCADRINKS);
                    if (answerIndex == 2) show(TASCAFOODS);
                    if (answerIndex == 3) show(PIROPO_TASCA);
                    if (answerIndex == 4) goToChat(TASCA);
                    if (answerIndex == 5) show(RUASAOJOAO);
                    break;
                case PIROPO_TASCA:
                    if (answerIndex == 1) {
                        player.lovePiropos(Piropos.P1.getMinLove(), Piropos.P1.getMaxLove());
                        show(PIROPO_TASCA);
                    }
                    if (answerIndex == 2) {
                        player.lovePiropos(Piropos.P2.getMinLove(), Piropos.P2.getMaxLove());
                        show(PIROPO_TASCA);
                    }
                    if (answerIndex == 3) {
                        player.lovePiropos(Piropos.P3.getMinLove(), Piropos.P3.getMaxLove());
                        show(PIROPO_TASCA);
                    }
                    if (answerIndex == 4) {
                        player.lovePiropos(Piropos.P4.getMinLove(), Piropos.P4.getMaxLove());
                        show(PIROPO_TASCA);
                    }
                    if (answerIndex == 5) {
                        player.lovePiropos(Piropos.P5.getMinLove(), Piropos.P5.getMaxLove());
                        show(PIROPO_TASCA);
                    }
                    if (answerIndex == 6) {
                        player.lovePiropos(Piropos.P6.getMinLove(), Piropos.P6.getMaxLove());
                        show(PIROPO_TASCA);
                    }
                    if (answerIndex == 7) show(TASCA);

                    break;
                case TASCADRINKS:

                    if (answerIndex == 1) {
                        player.drink(Drinks.CERVEJA, 3);
                        if (player.isWasted() == true) {
                            show(GAME_OVER);
                        } else {
                            show(TASCADRINKS);
                        }
                    }
                    if (answerIndex == 2) {
                        player.drink(Drinks.SANGRIA, 3);
                        if (player.isWasted() == true) {
                            show(GAME_OVER);
                        } else {
                            show(TASCADRINKS);
                        }
                    }
                    if (answerIndex == 3) {
                        player.drink(Drinks.VINHO, 3);
                        if (player.isWasted() == true) {
                            show(GAME_OVER);
                        } else {
                            show(TASCADRINKS);
                        }
                    }
                    if (answerIndex == 4) {
                        player.drink(Drinks.WHISKY, 3);
                        if (player.isWasted() == true) {
                            show(GAME_OVER);
                        } else {
                            show(TASCADRINKS);
                        }
                    }
                    if (answerIndex == 5) {
                        player.drink(Drinks.COCKTAIL, 3);
                        if (player.isWasted() == true) {
                            show(GAME_OVER);
                        } else {
                            show(TASCADRINKS);
                        }
                    }
                    if (answerIndex == 6) show(TASCA);
                    break;
                case TASCAFOODS:

                    if (answerIndex == 1) {
                        player.food(Foods.BIFANA, 3);
                        if (player.isWasted() == true) {
                            show(GAME_OVER);
                        } else {

                            show(TASCAFOODS);
                        }
                    }
                    if (answerIndex == 2) {
                        player.food(Foods.KEBAB, 3);
                        if (player.isWasted() == true) {
                            show(GAME_OVER);
                        } else {
                            show(TASCAFOODS);
                        }
                    }
                    if (answerIndex == 3) {
                        player.food(Foods.FARTURA, 3);
                        if (player.isWasted() == true) {
                            show(GAME_OVER);
                        } else {
                            show(TASCAFOODS);
                        }
                    }
                    if (answerIndex == 4) {
                        player.food(Foods.AMBERGA, 3);
                        if (player.isWasted() == true) {
                            show(GAME_OVER);
                        } else {
                            show(TASCAFOODS);
                        }
                    }
                    if (answerIndex == 5) {
                        show(TASCA);
                    }
                    break;
                case GAME_OVER:
                    mainServer.gameOverAll();
                    if (answerIndex == 1) {


                        mainServer.setFinalSheet("");
                        mainServer.finalSheet();
                        outChat.println(mainServer.getFinalSheet());
                        System.exit(0);

                    }
                    break;
            }
        }
    }

//    }


}
