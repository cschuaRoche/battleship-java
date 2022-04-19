package org.scrum.psd.battleship.ascii;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import org.scrum.psd.battleship.controller.GameController;
import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;
import org.scrum.psd.battleship.controller.dto.Ship;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static List<Ship> myFleet;
    private static List<Ship> enemyFleet;
    private static ColoredPrinter console;

    public static void main(String[] args) {
        console = new ColoredPrinter.Builder(1, false).background(Ansi.BColor.BLACK).foreground(Ansi.FColor.WHITE).build();

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
        do {
            console.setForegroundColor(Ansi.FColor.WHITE);
            console.println("---------- Round "+ (round++) +" ----------");
            console.println("");
            console.println("Player, it's your turn");

            console.println("@@@@@@@@ Destroyed Ships List  @@@@@@@@");
            printEnemyDestroyedList(enemyFleet);
            console.println("@@@@@@@@ Available Ships List  @@@@@@@@");
            printEnemyAvailableList(enemyFleet);

            console.println("Enter coordinates for your shot :");
            Position position = parsePosition(scanner.next());
            boolean isHit = GameController.checkIsHit(enemyFleet, position);
            printHitMiss(true, isHit);

            if (fleetDestroyed(enemyFleet)) {
                showYouWin();
                break;
            }

            position = getRandomPosition();
            isHit = GameController.checkIsHit(myFleet, position);
            console.setForegroundColor(Ansi.FColor.MAGENTA);
            console.println("");
            console.println(String.format("Computer shoot in %s%s and %s", position.getColumn(), position.getRow(), isHit ? "hit your ship !" : "miss"));
            printHitMiss(false, isHit);

            if (fleetDestroyed(myFleet)) {
                showGameOver();
                break;
            }
        } while (true);
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

    private static void printHitMiss(boolean isPlayer, boolean isHit) {
        if (isHit) {
            beep();
            console.setForegroundColor(Ansi.FColor.RED);
            if (isPlayer) {
                console.println(isHit ? "Yeah ! Nice hit !" : "Miss");
            }
        } else {
            console.setForegroundColor(Ansi.FColor.BLUE);
        }
        console.println("                \\         .  ./");
        console.println("              \\      .:\" \";'.:..\" \"   /");
        console.println("                  (M^^.^~~:.'\" \").");
        console.println("            -   (/  .    . . \\ \\)  -");
        console.println("               ((| :. ~ ^  :. .|))");
        console.println("            -   (\\- |  \\ /  |  /)  -");
        console.println("                 -\\  \\     /  /-");
        console.println("                   \\  \\   /  /");
    }

    private static void showYouWin() {
        console.setForegroundColor(Ansi.FColor.GREEN);
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
        int rows = 8;
        int lines = 8;
        Random random = new Random();
        Letter letter = Letter.values()[random.nextInt(lines)];
        int number = random.nextInt(rows);
        Position position = new Position(letter, number);
        return position;
    }

    private static void InitializeGame() {
        InitializeMyFleet();

        InitializeEnemyFleet();
    }

    private static void InitializeMyFleet() {
        Scanner scanner = new Scanner(System.in);
        myFleet = GameController.initializeShips();

        console.println("Please position your fleet (Game board has size from A to H and 1 to 8) :");

        for (Ship ship : myFleet) {
            console.println("");
            console.println(String.format("Please enter the positions for the %s (size: %s)", ship.getName(), ship.getSize()));
            for (int i = 1; i <= ship.getSize(); i++) {
                console.println(String.format("Enter position %s of %s (i.e A3):", i, ship.getSize()));

                String positionInput = scanner.next();
                ship.addPosition(positionInput);
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
}
