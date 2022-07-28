package org.academiadecodigo.cunnilinux.multiplayergame;

import org.academiadecodigo.cunnilinux.multiplayergame.menus.MenuTypes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.academiadecodigo.cunnilinux.multiplayergame.menus.Options.DEFAULT_PORT;

public class MainServer {
    private LinkedList<GameClient> allClientsList = new LinkedList<>();
    private int clientCount;
    private int groupSize = 3;
    private static final Logger logger = Logger.getLogger(MainServer.class.getName());
    private ServerSocket bindSocket;
    private ExecutorService fixedPool;

    public static void main(String[] args) {

        try {

            int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;

            MainServer chatServer = new MainServer();
            chatServer.listen(port);

        } catch (NumberFormatException e) {

            System.err.println("Usage: WebServer [PORT]");
            System.exit(1);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public int getClientCount() {return clientCount;}

    public void newClient(GameClient chatClient) {
        allClientsList.add(chatClient);
        clientCount++;
    }

    public void broadCast(String message){
        for (GameClient c : allClientsList) {
            c.print(message);
        }
    }

    public void setWastedAll() throws IOException, InterruptedException {
        for (GameClient c : allClientsList) {
            c.getPlayer().setWasted();
        }
    }
    public synchronized void gameOverAll() throws IOException, InterruptedException {
        for (GameClient c : allClientsList) {
            c.gameOver();

        }
    }

    public void broadCastChat(MenuTypes menuType, String message){
        for (GameClient c : allClientsList) {
            if( c.isInChat(menuType) ) c.print(message);
        }
    }


    public boolean allGood(){
        int countGood = 0;
        for (GameClient c : allClientsList) {
            if( c.isGoodToGo() ) countGood++;
            else break;
        }
        if(countGood == groupSize) return true;
        else return false;
    }


    private void listen(int port) throws IOException {

        bindSocket = new ServerSocket(port);
        fixedPool = Executors.newFixedThreadPool(10);
        clientCount = 0;

        while (true) {
            try {
                // LISTEN TO NEW CLIENTS AND ACCEPT WHEN ONE TRIES TO ACCESS
                Socket clientSocket = bindSocket.accept();
                //System.out.println(clientCount);
                GameClient gameClient = new GameClient("" + clientCount , clientSocket, logger, this);
                newClient(gameClient);
                fixedPool.submit(gameClient);
                System.out.println(clientCount);
                logger.log(Level.INFO, "server bind to " + getAddress());
/*                // SERVER QUIT COMMAND
                String message = "";
                while (!message.equals("\"quit\"")) {

                    try {

                        message = in.readLine();
                        System.out.println(message);

                    } catch (IOException ex) {
                        System.out.println("Receiving error: " + ex.getMessage());
                    }
                }*/

            } catch (IOException e) {
                logger.log(Level.SEVERE, "could not bind to port " + port);
                logger.log(Level.SEVERE, e.getMessage());
                System.exit(1);

            } finally {
                //clientSocket.close();
                //bindSocket.close();

            }
        }
    }

    //###################finalScore#############################

    public String getFinalSheet() {
        return finalSheet;
    }

    public void setFinalSheet(String finalSheet) {
        this.finalSheet = finalSheet;
    }

    private  String finalSheet;
    public  String finalSheet(){
        for (GameClient c : allClientsList) {
            c.getPlayer().finalResult(c.getPlayer());
            if(c.getPlayer().isWasted()==true){
                finalSheet = finalSheet + " \n " + c.getPlayer().getName() + ": " + c.getPlayer().getFinalScore();
            }
        }
        return finalSheet;
    }

    public String getAddress() {

        if (bindSocket == null) {
            return null;
        }

        return bindSocket.getInetAddress().getHostAddress() + ":" + bindSocket.getLocalPort();
    }
}
