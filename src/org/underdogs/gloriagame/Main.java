package org.underdogs.gloriagame;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        int port = 5050;//scanner.nextInt();

        System.out.println("Set limit of players");
        int players = scanner.nextInt();

        Game game = new Game(port, players);
        game.listen();

    }
}
