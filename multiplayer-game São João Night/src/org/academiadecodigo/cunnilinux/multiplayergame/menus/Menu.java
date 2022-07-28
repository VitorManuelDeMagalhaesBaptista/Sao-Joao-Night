package org.academiadecodigo.cunnilinux.multiplayergame.menus;

import org.academiadecodigo.bootcamp.InputScanner;
import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.integer.IntegerRangeInputScanner;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.InputStream;
import java.io.PrintStream;

import static org.academiadecodigo.cunnilinux.multiplayergame.menus.MenuTypes.*;

public class Menu {
    private MenuTypes menuType;
    //private MenuInputScanner scanner;
    private InputScanner scanner;
    //private MenuInputScanner menuScanner;
    //private IntegerInputScanner integerScanner;
    //private StringInputScanner stringScanner;
    private Prompt prompt;
    private String[] options;
    public Menu(MenuTypes menuType,InputStream in ,PrintStream out) {
        this.menuType = menuType;
        build(in,out);
    }

/*    public void setMessage(){
        ((MenuInputScanner) scanner).setMessage(putColor(menuType.getHeaderColor(),menuType.getHeader()));
    }*/

    public void build(InputStream in , PrintStream out) {
        options = menuType.getOptions();
        prompt = new Prompt(in, out);
        // create a menu with those options and set the message
        if(menuType == SETGROUPSIZE) scanner = new IntegerRangeInputScanner(2, 100);
        else if(menuType == PLAYERNAME) scanner = new StringInputScanner();
        else scanner = new MenuInputScanner(options);
    }

    public String getStringChoice(){
        if(scanner instanceof StringInputScanner){
            ((StringInputScanner) scanner).setMessage(putColor(menuType.getHeaderColor(), menuType.getHeader()));
        }
        // show the menu to the user and get the selected answer
        //System.out.println("User wants to " + menuType.getOptions()[answerIndex - 1]);
        return (String) prompt.getUserInput(scanner);
    }

    public int getIntChoice(){
        if(scanner instanceof MenuInputScanner){
            ((MenuInputScanner) scanner).setMessage(putColor(menuType.getHeaderColor(),menuType.getHeader()));
        }
        // show the menu to the user and get the selected answer
        //System.out.println("User wants to " + menuType.getOptions()[answerIndex - 1]);
        return (int) prompt.getUserInput(scanner);
    }

    public String putColor(String color, String text){
        return color + text + ConsoleColors.RESET;
    }

}
