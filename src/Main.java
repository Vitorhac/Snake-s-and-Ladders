import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduce port number");
        int port = scanner.nextInt();

        System.out.println("Set limit of players");
        int players = scanner.nextInt();

        Game game = new Game(port, players);
        game.listen();

    }
}
