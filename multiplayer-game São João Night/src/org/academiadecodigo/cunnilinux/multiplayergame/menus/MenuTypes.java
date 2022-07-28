package org.academiadecodigo.cunnilinux.multiplayergame.menus;

import static org.academiadecodigo.cunnilinux.multiplayergame.menus.Options.*;

public enum MenuTypes {
    INTRO(INTRO_HEADER, ConsoleColors.CYAN_BRIGHT, INTRO_OPT),
    RULES(RULES_TEXT, ConsoleColors.GREEN_BRIGHT, GOBACK_OPT),
    //-------------------------------------------
    // PLAY
    PLAYERNAME(PLAYERNAME_HEADER, ConsoleColors.BLUE_BOLD, GOBACK_OPT),
    SETGROUPSIZE(SETGROUPSIZE_HEADER, ConsoleColors.BLUE_BOLD_BRIGHT, new String[]{""}),
    WAITINGROOM(WAITINGROOM_HEADER, ConsoleColors.BLACK_UNDERLINED, GOBACK_OPT),
    //-------------------------------------------
    // RUA SAO JOAO
    RUASAOJOAO(SJOAOSTREET_HEADER, ConsoleColors.YELLOW_BOLD_BRIGHT, SJOAOSTREET_OPT),
    JUMPFIRE(JUMPFIRE_HEADER, ConsoleColors.RED_BRIGHT, JUMPFIRE_OPT),
    //-------------------------------------------
    // TASCA DAS TIAS
    TASCATIAS(TTIAS_HEADER, ConsoleColors.GREEN_BOLD_BRIGHT, TTIAS_OPT),
    TASCATIASBAR(TTIAS_BAR_HEADER, ConsoleColors.GREEN_BRIGHT, TTIAS_BAR_OPT),
    TASCATIASDRINKS(TTIAS_DRINKS_HEADER, ConsoleColors.GREEN_UNDERLINED, TTIAS_DRINKS_OPT),
    TASCATIASFOODS(TTIAS_FOODS_HEADER, ConsoleColors.GREEN_UNDERLINED, TTIAS_FOOD_OPT),
    //-------------------------------------------
    // CLASSIC
    CLASSIC(CLASSIC_HEADER, ConsoleColors.PURPLE_BOLD_BRIGHT, CLASSIC_OPT),
    CLASSICBAR(CLASSIC_BAR_HEADER, ConsoleColors.PURPLE_BOLD_BRIGHT, CLASSIC_BAR_OPT),
    CLASSICSHOW(CLASSIC_SHOW_HEADER, ConsoleColors.PURPLE, GOBACK_OPT),
    LAPDANCE(LAPDANCE_HEADER, ConsoleColors.PURPLE_BRIGHT, GOBACK_OPT),
    //-------------------------------------------
    // TASCA
    TASCA(TASCA_HEADER, ConsoleColors.CYAN_BOLD_BRIGHT, TASCA_OPT),
    TASCADRINKS(TASCA_DRINK_HEADER, ConsoleColors.CYAN_BRIGHT, TASCA_DRINK_OPT),
    TASCAFOODS(TASCA_FOOD_HEADER, ConsoleColors.CYAN, TASCA_FOOD_OPT),
    PIROPO_RUA(PIROPO_HEADER, ConsoleColors.CYAN_BOLD_BRIGHT, PIROPO_OPT),
    PIROPO_TASCA(PIROPO_HEADER, ConsoleColors.CYAN_BOLD_BRIGHT, PIROPO_OPT),
    GAME_OVER(GAME_OVER_HEADER,ConsoleColors.RED_BOLD_BRIGHT,GAME_OVER_OPT ),
    FINAL_SCORE_MENU(FINAL_SCORE_MENU_HEADER,ConsoleColors.CYAN_UNDERLINED,FINAL_SCORE_MENU_OPT);

    private String header;
    private String headerColor;
    private String[] options;

    public String getHeader() {
        return header;
    }

    public String[] getOptions() {
        return options;
    }
    public String getHeaderColor(){return headerColor;}

    MenuTypes(String header, String headerColor, String[] options) {
        this.header = header;
        this.headerColor = headerColor;
        this.options = options;
    }


}
