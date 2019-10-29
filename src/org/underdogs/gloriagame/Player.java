package org.underdogs.gloriagame;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

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
    private BufferedReader userInputStream;

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
        try {
            this.userInputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void askQuestion() throws IOException {

        String message = "";

        if (!clientSocket.isClosed()) {

                // TODO: 10/28/2019 broadcast do ascii art dos dados
                // TODO: 10/28/2019 mensagem de YouLost aparece no player que vence

                send(art.yourTurn() + "\n");

                StringInputScanner question = new StringInputScanner();
                question.setMessage("\nPress R to roll the dice, " + name + ". \n");
                message = prompt.getUserInput(question);
                getPlayerInput(message);


                int newPosition = game.checkPosition(this, position);
                position = newPosition;
                send("\n" + "You are now in the House Number : " + newPosition + "\n");
                game.checkVictory(this, position);
                exit();

        }
    }

    public int rollDice() {

        int diceNumber = (int) (Math.ceil(Math.random() * 6));
        position += diceNumber;
        send("You rolled a: " + diceNumber + "\n");
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
            changeName(newName);
        }
    }

    public void send(String message) {
        game.send(this, message);
    }

    public void send(int position) {
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

    public String getName() {
        return this.name;
    }
}
