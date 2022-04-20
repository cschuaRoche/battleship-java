package org.scrum.psd.battleship.ascii;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import org.scrum.psd.battleship.controller.GameController;
import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;
import org.scrum.psd.battleship.controller.dto.Ship;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static List<Ship> myFleet;
    private static List<Ship> enemyFleet;
    private static List<String> inputLocations = new ArrayList<>();
    private static List<String> enemyInputLocations = new ArrayList<>();
    private static ColoredPrinter console;

    // we randomly place the positions  B2  A1  H8
    // then we pop a location

    public static void initEnemyInput() {
        // A
        enemyInputLocations.add("A1");
        enemyInputLocations.add("A2");
        enemyInputLocations.add("A3");
        enemyInputLocations.add("A4");
        enemyInputLocations.add("A5");
        enemyInputLocations.add("A6");
        enemyInputLocations.add("A7");
        enemyInputLocations.add("A8");
        enemyInputLocations.add("A9");
        // B
        enemyInputLocations.add("B1");
        enemyInputLocations.add("B2");
        enemyInputLocations.add("B3");
        enemyInputLocations.add("B4");
        enemyInputLocations.add("B5");
        enemyInputLocations.add("B6");
        enemyInputLocations.add("B7");
        enemyInputLocations.add("B8");
        enemyInputLocations.add("B9");
        // C
        enemyInputLocations.add("C1");
        enemyInputLocations.add("C2");
        enemyInputLocations.add("C3");
        enemyInputLocations.add("C4");
        enemyInputLocations.add("C5");
        enemyInputLocations.add("C6");
        enemyInputLocations.add("C7");
        enemyInputLocations.add("C8");
        enemyInputLocations.add("C9");
        // D
        enemyInputLocations.add("D1");
        enemyInputLocations.add("D2");
        enemyInputLocations.add("D3");
        enemyInputLocations.add("D4");
        enemyInputLocations.add("D5");
        enemyInputLocations.add("D6");
        enemyInputLocations.add("D7");
        enemyInputLocations.add("D8");
        enemyInputLocations.add("D9");
        // E
        enemyInputLocations.add("E1");
        enemyInputLocations.add("E2");
        enemyInputLocations.add("E3");
        enemyInputLocations.add("E4");
        enemyInputLocations.add("E5");
        enemyInputLocations.add("E6");
        enemyInputLocations.add("E7");
        enemyInputLocations.add("E8");
        enemyInputLocations.add("E9");
        // F
        enemyInputLocations.add("F1");
        enemyInputLocations.add("F2");
        enemyInputLocations.add("F3");
        enemyInputLocations.add("F4");
        enemyInputLocations.add("F5");
        enemyInputLocations.add("F6");
        enemyInputLocations.add("F7");
        enemyInputLocations.add("F8");
        enemyInputLocations.add("F9");
        // G
        enemyInputLocations.add("G1");
        enemyInputLocations.add("G2");
        enemyInputLocations.add("G3");
        enemyInputLocations.add("G4");
        enemyInputLocations.add("G5");
        enemyInputLocations.add("G6");
        enemyInputLocations.add("G7");
        enemyInputLocations.add("G8");
        enemyInputLocations.add("G9");
        // H
        enemyInputLocations.add("H1");
        enemyInputLocations.add("H2");
        enemyInputLocations.add("H3");
        enemyInputLocations.add("H4");
        enemyInputLocations.add("H5");
        enemyInputLocations.add("H6");
        enemyInputLocations.add("H7");
        enemyInputLocations.add("H8");
        enemyInputLocations.add("H9");

        Collections.shuffle(enemyInputLocations);
    }

    public static void main(String[] args) {
        console = new ColoredPrinter.Builder(1, false).background(Ansi.BColor.BLACK).foreground(Ansi.FColor.WHITE).attribute(Ansi.Attribute.CLEAR).build();

        console.setForegroundColor(Ansi.FColor.MAGENTA);
        console.println("                                     |__");
        console.println("                                     |\\/");
        console.println("                                     ---");
        console.println("                                     / | [");
        console.println("                              !      | |||");
        console.println("                            _/|     _/|-++'");
        console.println("                        +  +--|    |--|--|_ |-");
        console.println("                     { /|__|  |/\\__|  |--- |||__/");
        console.println("                    +---------------___[}-_===_.'____                 /\\");
        console.println("                ____`-' ||___-{]_| _[}-  |     |_[___\\==--            \\/   _");
        console.println(" __..._____--==/___]_|__|_____________________________[___\\==--____,------' .7");
        console.println("|                        Welcome to Battleship                         BB-61/");
        console.println(" \\_________________________________________________________________________|");
        console.println("");
        console.setForegroundColor(Ansi.FColor.WHITE);

        InitializeGame();

        StartGame();
    }

    private static void StartGame() {
        Scanner scanner = new Scanner(System.in);

        console.print("\033[2J\033[;H");
        console.println("                  __");
        console.println("                 /  \\");
        console.println("           .-.  |    |");
        console.println("   *    _.-'  \\  \\__/");
        console.println("    \\.-'       \\");
        console.println("   /          _/");
        console.println("  |      _  /\" \"");
        console.println("  |     /_\'");
        console.println("   \\    \\_/");
        console.println("    \" \"\" \"\" \"\" \"");

        int round = 1;
        while (true) {
            console.setForegroundColor(Ansi.FColor.WHITE);
            console.println("\n\n\n-------------------- Round "+ (round++) +" --------------------");
            console.println("");
            console.println("Player, it's your turn");

            if (inputLocations.size() > 0) {
                console.setForegroundColor(Ansi.FColor.YELLOW);
                console.println("\nYour previously entered coordinates:");
                console.println(inputLocations.toString());
                console.setForegroundColor(Ansi.FColor.WHITE);
            }

            if (anyEnemyShipDestroyed(enemyFleet)) {
                console.setForegroundColor(Ansi.FColor.RED);
                console.println("\n@@@@@@@@ Destroyed Enemy Ships List @@@@@@@@");
                printEnemyDestroyedList(enemyFleet);
            }

            if (anyEnemyShipAvailable(enemyFleet)) {
                console.setForegroundColor(Ansi.FColor.GREEN);
                console.println("\n@@@@@@@@ Available Enemy Ships List @@@@@@@@");
                printEnemyAvailableList(enemyFleet);
            }
            console.setForegroundColor(Ansi.FColor.WHITE);

            console.println("\nEnter coordinates for your shot:");
            String positionInput = scanner.nextLine();

            // kill switch - Game Over
            if (positionInput.equals("Surrender")) {
                showGameOver();
                break;
            }

            Position position = parsePosition(positionInput);
            inputLocations.add(getPosition(position));

            boolean isHit = GameController.checkIsHit(enemyFleet, position);
            console.setForegroundColor(Ansi.FColor.YELLOW);
            console.println("\n\n");
            console.println(String.format("You shoot in %s%s and %s", position.getColumn(), position.getRow(), isHit ? "hit Enemy ship !" : "miss Enemy ship"));
            printHitMissImage(isHit);

            if (fleetDestroyed(enemyFleet)) {
                showYouWin();
                break;
            }

            position = getRandomPosition();
            if (position == null) {
                showGameOver();
                break;
            }

            isHit = GameController.checkIsHit(myFleet, position);
            console.setForegroundColor(Ansi.FColor.MAGENTA);
            console.println("\n\n");
            console.println(String.format("Enemy shoot in %s%s and %s", position.getColumn(), position.getRow(), isHit ? "hit your ship !" : "miss your ship"));
            printHitMissImage(isHit);

            anyEnemyShipDestroyed(myFleet);

            if (fleetDestroyed(myFleet)) {
                showGameOver();
                break;
            }
        }
    }

    private static boolean anyEnemyShipAvailable(List<Ship> enemyFleet) {
        for(Ship ship: enemyFleet){
            if(!ship.isDestroyed()){
                return true;
            }
        }
        return false;
    }

    private static boolean anyEnemyShipDestroyed(List<Ship> enemyFleet) {
        for(Ship ship: enemyFleet){
            if(ship.isDestroyed()){
                return true;
            }
        }
        return false;
    }


    private static String getPosition(Position position) {
        return position.getColumn().name()+""+position.getRow();
    }

    private static void printEnemyDestroyedList(List<Ship> enemyFleet) {
        for(Ship ship: enemyFleet){
            if (ship.isDestroyed()) {
                console.println(ship.getName());
            }
        }
    }

    private static void printEnemyAvailableList(List<Ship> enemyFleet) {
        for(Ship ship: enemyFleet){
            if(!ship.isDestroyed()){
                console.println(ship.getName());
            }
        }
    }

    private static boolean fleetDestroyed(List<Ship> fleet) {
        boolean fleetDestroyed = true;
        for(Ship ship: fleet){
            if (!ship.isDestroyed()) {
                fleetDestroyed = false;
                break;
            }
        }
        return fleetDestroyed;
    }

    private static void printHitMissImage(boolean isHit) {
        if (isHit) {
            beep();
            console.setForegroundColor(Ansi.FColor.RED);
            console.println("                     __,-~~/~    `---.");
            console.println("                   _/_,---(      ,    )");
            console.println("               __ /        <    /   )  \\___");
            console.println("- ------===;;;'====------------------===;;;===----- -  -");
            console.println("                  \\/  ~|~|~|~|~|~\\~|~)~|/");
            console.println("                  (_ (   \\  (     >    \\)");
            console.println("                   \\_( _ <         >_>/'");
            console.println("                      ~ `-i' ::>|--\"");
            console.println("                          I;|.|.|");
            console.println("                         <|i::|i|`.");
            console.println("                        (` ^\'\"`-' \")");
            console.println("                 _____.,-#%&$@%#&#~,._____");
        } else {
            console.setForegroundColor(Ansi.FColor.BLUE);
            console.println("                \\         .  ./");
            console.println("              \\      .:\" \";'.:..\" \"   /");
            console.println("                  (M^^.^~~:.'\" \").");
            console.println("            -   (/  .    . . \\ \\)  -");
            console.println("               ((| :. ~ ^  :. .|))");
            console.println("            -   (\\- |  \\ /  |  /)  -");
            console.println("                 -\\  \\     /  /-");
            console.println("                   \\  \\   /  /");
        }
        console.setForegroundColor(Ansi.FColor.WHITE);
    }

    private static void showYouWin() {
        console.setForegroundColor(Ansi.FColor.GREEN);
        console.println("You are the winner!");
        console.println("                                                                                                    ");
        console.println("                   7JJ~         !JJ!      7JJJJJJJJJJJJ:     :JJ?.     ^JJ7                         ");
        console.println("                   JPP?:^^^^^^^:JPG?      YPP5JJJJJYPPP^     ^PP5.     !GPY                         ");
        console.println("                   JPPPPPPPPPPPPPPP?      YPP!     .5PP^     ^PP5.     !PPY                         ");
        console.println("                   :^^^^^!PP5~^^^^^:      YPP~     .5PP^     ^PP5.     !PPY                         ");
        console.println("                         :PP5.            YPP7:::::^5PP^     ^PP5^:::::7PPY                         ");
        console.println("                         :PPP:            YPPPPPPPPPPPP^     ^PPPPPPPPPPPPY                         ");
        console.println("                         .^^^             ^^^^^^^^^^^^^.     .^^^^^^^^^^^^^                         ");
        console.println("                      .:::::::::::::::::::::::::::::::::::::::::::::::::::.                         ");
        console.println("                      !P5555555555555555555555555555555555555555555555555PJ                         ");
        console.println("                      !PP5JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJYPPJ                         ");
        console.println("                      !PP5???777777777777777777777777777777777777777???YPPJ                         ");
        console.println("                      !PPPPP5^:::^^^^^^^^^^^^^^^^^^^^^^^^^^^^:::^777YPPPPPJ                         ");
        console.println("                   ?55J777JJJ55P!::::::::::::::::::::::::::::^!!!PGGYJJJJJY55Y.                     ");
        console.println("                :::P&&Y::^77?&@@?::::::::::::::::::::::::::::~???&@@Y77777J&@&~::.                  ");
        console.println("               .#@@?^^:::^77?&@@?::::::::::::::::::::::::::::^77?&@@?:::::^???#&@7                  ");
        console.println("               .&@@7::7555GGGPPP555?::::::::::::::::::::::~!!?GGGYJJY55555Y?7?&@@7                  ");
        console.println("               .#@@7::J@&&@@@J77B@@P::::::::::::::::::::::!??Y@@&^::G@@@&&B?7?&@@7                  ");
        console.println("               .&@@7:::^^7@@@J77B@@P::::::::::::::::::::::~77Y@@@~::G@@P^^~77?&@@7                  ");
        console.println("               .?JJY557::^YYY?77YPP5Y5J^:::::::::::::::~!~JGGPYYJ^::JPPJ~~7GGPJJJ^                  ");
        console.println("                   P@@5^^^^^^??????P@@#^:::::::::::::::7?7G@@B^^^^^^7???J?Y&&&:                     ");
        console.println("                   ...J@@@@@@@@@@@@@@@#::::::::::::::::!77P@@@&@@@@&@@@@@@B:..                      ");
        console.println("                      ~YYYYYYYYYYYYJJJJYYY~::::::::^~~~YPP5JJJYYYYYYYYYYYY?                         ");
        console.println("                                      :@@@!::::::::^???#@@J                                         ");
        console.println("                                      ^@@@!::::::::^777#@@J                                         ");
        console.println("                                      .YYYJJY!::^~~!5PPYYY~                                         ");
        console.println("                                          B@@J::~??J@@@~                                            ");
        console.println("                                          B@@J::~??J@@@~                                            ");
        console.println("                                      .77?PGB?::^!!7GGGJ7?^                                         ");
        console.println("                                      ^@@@J77~:::::^777#@@J                                         ");
        console.println("                                      :&@@J777777777777#@@J                                         ");
        console.println("                                   :77?@@@P555555555555&@@P!7~                                      ");
        console.println("                                   7@@@@@@@@@@@@@@@@@@@@@@@@@G                                      ");
        console.println("                            .PBBBBBG5555555555555555555555555PBBBBBB~                               ");
        console.println("                            .#@@&##GYYYYYYYYYYYYYYYYYYYYYYYYY5###@@@!                               ");
        console.println("                            .#@@GYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY&@@!                               ");
        console.println("                            .#@@G55555555555555555555555555555555&@@!                               ");
        console.println("                            .#@@#GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG@@@!                               ");
        console.println("                            .#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@!                               ");
        console.println("                             ^~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.                               ");
        console.println("                   :^^:         .::.      :::::::::::::       :::      .^^:      :^^:               ");
        console.println("                   JPP7         ?PP?      YPPPPPPPPPPPP^     ^PP5.     !PPY      ?PP?               ");
        console.println("                   JPP7  :JJ?.  ?PP?      YPP7^^^^^~5PP^     ^PPPJJJ:  !PPY      ?PP?               ");
        console.println("                   JPP7  :PPP:  ?PP?      YPP~     .5PP^     ^PPPYYY~::7PPY      !YY!               ");
        console.println("                   JPG7  :PPP.  ?PP?      YPP~     .5PP^     ^PP5.  JPPPPPY                         ");
        console.println("                   :~^!JJ?~^~?JJ!^~:      YPPY?JJJJJPPP^     ^PP5.  :~^?PPY      !JJ!               ");
        console.println("                      ~YY?   JYY^         ?YYYYYYYYYYYY:     :YYJ.     ~YY?      7YY7               ");
        console.println("                                                                                                    ");
        console.setForegroundColor(Ansi.FColor.WHITE);
    }

    private static void showGameOver() {
        console.setForegroundColor(Ansi.FColor.RED);
        console.println("You lost!");
        console.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        console.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        console.println("@@@@@@@@@@@@@@@@@@@@&BBBBBBBBB#@@@@@@&BBBBB&@@@@@@&B#B&@@@@@&B#B&@@#GGGGGGGGGGGGGB@@@@@@@@@@@@@@@@@@");
        console.println("@@@@@@@@@@@@@@@@@@#B? .^^^^^^^?@@@@#B? .^: !G#@@@@Y . 7G#@#B7 . ?@@?   .~~~~~~~~~?@@@@@@@@@@@@@@@@@@");
        console.println("@@@@@@@@@@@@@@@@#G7 .^!#@@@@@@@@@#G7 .^?&5^: ~PB@@J     !G!     ?@@?   !@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        console.println("@@@@@@@@@@@@@@@@Y   ^&@@#PPPPPB@@Y   !@@@@@Y   ?@@J             ?@@?   :JYYYJ5@@@@@@@@@@@@@@@@@@@@@@");
        console.println("@@@@@@@@@@@@@@@@Y   ^&@@P~:   ~@@Y   ^YYYY5!   ?@@J   :!: :!:   ?@@?   :77777Y@@@@@@@@@@@@@@@@@@@@@@");
        console.println("@@@@@@@@@@@@@@@@G7^ .?5@@@Y   ~@@Y   :77777^   ?@@J   7@P75@J   ?@@?   !@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        console.println("@@@@@@@@@@@@@@@@@@G?~ .???^   ~@@Y   ~@@@@@J   7@@J   !@@@@@?   7@@?   .777777777J@@@@@@@@@@@@@@@@@@");
        console.println("@@@@@@@@@@@@@@@@@@@@BJJJJJJJJJP@@BJJJP@@@@@GJJJP@@GJJJP@@@@@GJJJP@@GYYYYYYYYYYYYY5@@@@@@@@@@@@@@@@@@");
        console.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        console.println("@@@@@@@@@@@@@@@@@@#GGGGGGPGGB@@@@#GGGB@@@@@#GGG#@@#555555555555YP@@BGGGGPPPPPGG#@@@@@@@@@@@@@@@@@@@@");
        console.println("@@@@@@@@@@@@@@@@#P! .~~~~~: ^5G@@Y   7@@@@@J   ?@@Y   .777777777J@@7   :~~~~~. ~PB@@@@@@@@@@@@@@@@@@");
        console.println("@@@@@@@@@@@@@@@@Y   ^&@@@@J   ~@@Y   7@@@@@Y   ?@@5   !@@@@@@@@@@@@7   7@@@@@!   ?@@@@@@@@@@@@@@@@@@");
        console.println("@@@@@@@@@@@@@@@@Y   ^&@@@@J   ~@@J   ^JP@BY!   7@@5   :77777Y@@@@@@7   ?@@@PJ:   ?@@@@@@@@@@@@@@@@@@");
        console.println("@@@@@@@@@@@@@@@@Y   ^&@@@@J   ~@@G?~   :J^   :?P@@5   :JJJJJ5@@@@@@7   ^???: ^JJ?P@@@@@@@@@@@@@@@@@@");
        console.println("@@@@@@@@@@@@@@@@Y   ^&@@@@J   ~@@@@BJ~     :JP@@@@5   ~@@@@@@@@@@@@7   ^J:   ^7P@@@@@@@@@@@@@@@@@@@@");
        console.println("@@@@@@@@@@@@@@@@BY! .~!!!!: :YG@@@@@@BY! ^YG@@@@@@Y   .~~~~~~~~~?&@7   7@GY^   .!5@@@@@@@@@@@@@@@@@@");
        console.println("@@@@@@@@@@@@@@@@@@#P5555555PG@@@@@@@@@@#5G@@@@@@@@#PPPPPPPPPPPPPG@@B5P5B@@@B5PP55B@@@@@@@@@@@@@@@@@@");
        console.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        console.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        console.setForegroundColor(Ansi.FColor.WHITE);
    }

    private static void beep() {
        console.print("\007");
    }

    protected static Position parsePosition(String input) {
        Letter letter = Letter.valueOf(input.toUpperCase().substring(0, 1));
        int number = Integer.parseInt(input.substring(1));
        return new Position(letter, number);
    }

    private static Position getRandomPosition() {
        /*int rows = 8;
        int lines = 8;
        Random random = new Random();
        Letter letter = Letter.values()[random.nextInt(lines)];
        int number = random.nextInt(rows);
        Position position = new Position(letter, number);
        return position;*/

        if (enemyInputLocations.size() <= 0) {
            return null;
        }
        String input = enemyInputLocations.remove(0);  //"A2"

        Letter letter = Letter.valueOf(Character.toString(input.charAt(0)));
        return new Position(letter, Character.getNumericValue(input.charAt(1)));
    }

    private static void InitializeGame() {
        InitializeMyFleet();
        InitializeEnemyFleet();
        initEnemyInput();
    }

    private static void InitializeMyFleet() {
        Scanner scanner = new Scanner(System.in);
        myFleet = GameController.initializeShips();

        console.println("Please position your fleet (Game board has size from A to H and 1 to 8) :");

        for (Ship ship : myFleet) {
            console.println("");
            console.println(String.format("Please enter the positions for the %s (size: %s)", ship.getName(), ship.getSize()));
            String positionInput = "";
            for (int i = 1; i <= ship.getSize(); i++) {
                console.println(String.format("Enter position %s of %s (i.e A3):", i, ship.getSize()));

                positionInput = scanner.nextLine();
                if (positionInput.equals("Preset")) {
                    break;
                }
                ship.addPosition(positionInput);
            }

            if (positionInput.equals("Preset")) {
                InitializePlayerFleet();
                break;
            }
        }
    }

    private static void InitializeEnemyFleet() {
        enemyFleet = GameController.initializeShips();

        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 4));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 5));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 6));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 7));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 8));

        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 6));
        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 7));
        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 8));
        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 9));

        enemyFleet.get(2).getPositions().add(new Position(Letter.A, 3));
        enemyFleet.get(2).getPositions().add(new Position(Letter.B, 3));
        enemyFleet.get(2).getPositions().add(new Position(Letter.C, 3));

        enemyFleet.get(3).getPositions().add(new Position(Letter.F, 8));
        enemyFleet.get(3).getPositions().add(new Position(Letter.G, 8));
        enemyFleet.get(3).getPositions().add(new Position(Letter.H, 8));

        enemyFleet.get(4).getPositions().add(new Position(Letter.C, 5));
        enemyFleet.get(4).getPositions().add(new Position(Letter.C, 6));
    }

    private static void InitializePlayerFleet() {
        myFleet = GameController.initializeShips();

        myFleet.get(0).getPositions().add(new Position(Letter.B, 4));
        myFleet.get(0).getPositions().add(new Position(Letter.B, 5));
        myFleet.get(0).getPositions().add(new Position(Letter.B, 6));
        myFleet.get(0).getPositions().add(new Position(Letter.B, 7));
        myFleet.get(0).getPositions().add(new Position(Letter.B, 8));

        myFleet.get(1).getPositions().add(new Position(Letter.E, 6));
        myFleet.get(1).getPositions().add(new Position(Letter.E, 7));
        myFleet.get(1).getPositions().add(new Position(Letter.E, 8));
        myFleet.get(1).getPositions().add(new Position(Letter.E, 9));

        myFleet.get(2).getPositions().add(new Position(Letter.A, 3));
        myFleet.get(2).getPositions().add(new Position(Letter.B, 3));
        myFleet.get(2).getPositions().add(new Position(Letter.C, 3));

        myFleet.get(3).getPositions().add(new Position(Letter.F, 8));
        myFleet.get(3).getPositions().add(new Position(Letter.G, 8));
        myFleet.get(3).getPositions().add(new Position(Letter.H, 8));

        myFleet.get(4).getPositions().add(new Position(Letter.C, 5));
        myFleet.get(4).getPositions().add(new Position(Letter.C, 6));
    }
}
