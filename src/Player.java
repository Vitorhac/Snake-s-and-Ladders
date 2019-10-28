import org.academiadecodigo.bootcamp.Prompt;

import java.io.*;
import java.net.Socket;

public class Player implements Runnable {

    private Prompt prompt;
    private Socket clientSocket;
    private String name;
    private PrintStream out;
    private InputStream in;
    private Game game;
    private int position;
    private ACIIArt art;
    private Messages message;

    Player(Game game, Socket clientSocket) {
        this.game = game;
        this.clientSocket = clientSocket;
        this.position = 0;
    }

    private void start() {

        try {
            this.out = new PrintStream(clientSocket.getOutputStream());
            this.in = clientSocket.getInputStream();
            this.prompt = new Prompt(in, out);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        start();
        this.name = setName();

        BufferedReader userInputStream = null;
        String message = "";

        while (!clientSocket.isClosed()) {
            synchronized (this) {
                try {
                    userInputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    if ((message = userInputStream.readLine()) == null) {
                        exit();
                        return;
                    }

                    if (game.endGame) {
                        broadcastMessage(art.defeat());
                        exit();
                    }
                    // TODO: 10/28/2019 broadcast do ascii art dos dados 
                    // TODO: 10/28/2019 fazer turnos
                    // TODO: 10/28/2019 mensagem de YouLost aparece no player que vence

                    broadcastMessage(art.yourTurn() + "\n");
                    broadcastMessage("\nPress R to roll the dice, " + name + ". \n");
                    System.out.println("Press R to roll the dice");
                    getPlayerInput(message);

                    int newPosition = game.checkPosition(position);
                    position = newPosition;
                    broadcastMessage("\n" + "You are now in the House Number : " + newPosition + "\n" +
                            "Now just wait 5 seconds please ok?" + "\n");
                    System.out.println(newPosition);
                    System.out.println("Your position is now: " + newPosition);
                    //broadcastMessage(newPosition);
                    game.checkVictory(this, position);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getPosition() {
        return position;
    }

    public int rollDice() {
        int diceNumber = (int) (Math.ceil(Math.random() * 6));
        /*
        if (diceNumber == 1){
            broadcastMessage(art.dice1());
        }
        if (diceNumber == 2){
            broadcastMessage(art.dice2());
        }
        if (diceNumber == 3){
            broadcastMessage(art.dice3());
        }
        if (diceNumber == 4){
            broadcastMessage(art.dice4());
        }
        if (diceNumber == 5){
            broadcastMessage(art.dice5());
        }
        if (diceNumber == 6){
            broadcastMessage(art.dice6());
        }

         */

        position += diceNumber;
        broadcastMessage("You rolled a: " + diceNumber + "\n");
        System.out.println("You rolled a " + diceNumber);
        System.out.println(position);
        return diceNumber;
    }

    private String setName() {
        String user = Thread.currentThread().getName();
        return "Knight " + user.substring(user.length() - 1);
    }

    public void changeName(String name) {
        this.name = name;
    }

    private void getPlayerInput(String message) {
        if (message.equals("")) {
            return;
        }
        if (message.equals("r")) {
            rollDice();
        }
        if (message.startsWith("/name")) {
            String newName = message.substring(5);
            System.out.println(name + " changed his name to " + newName);
            changeName(newName);
        }
    }

    public void broadcastMessage(String message) {
        game.broadcast(this, message);
    }

    public void broadcastMessage(int position) {
        game.broadcast(this, position);
    }

    private void exit() {

        try {
            in.close();
            out.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
