package org.underdogs.gloriagame;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {

    private final int WIN_CONDITION = 64;
    private List<Player> playersList;
    private ServerSocket serverSocket;
    private BufferedReader in;
    private ACIIArt art;
    private int port;
    private Map<Integer, Rule> gameRules;
    private int maxPlayers;
    private int minPlayers;
    public boolean endGame;

    public Game(int port, int maxPlayers) {

        this.port = port;
        this.playersList = new ArrayList<>();
        this.gameRules = new HashMap<>();
        this.art = new ACIIArt();
        this.maxPlayers = maxPlayers;
        this.minPlayers = 0;

        gameRules.put(5, new Rule(22, Messages.HOUSE_5));
        gameRules.put(14, new Rule(31, Messages.HOUSE_14));
        gameRules.put(27, new Rule(37, Messages.HOUSE_27));
        gameRules.put(45, new Rule(53, Messages.HOUSE_45));
        gameRules.put(52, new Rule(59, Messages.HOUSE_52));
        gameRules.put(16, new Rule(3, Messages.HOUSE_16));
        gameRules.put(25, new Rule(9, Messages.HOUSE_25));
        gameRules.put(34, new Rule(20, Messages.HOUSE_34));
        gameRules.put(42, new Rule(28, Messages.HOUSE_42));
        gameRules.put(51, new Rule(35, Messages.HOUSE_51));
        gameRules.put(55, new Rule(44, Messages.HOUSE_55));
        gameRules.put(63, new Rule(2, Messages.HOUSE_63));
    }

    public void listen() {
        System.out.println("======================== Server initiated ========================\n");

        System.out.println(art.welcome());
        try {
            serverSocket = new ServerSocket(port);

            ExecutorService pool = Executors.newFixedThreadPool(maxPlayers);

            while (minPlayers < maxPlayers) {

                Socket socket = serverSocket.accept();
                Player player = new Player(this, socket);
                playersList.add(player);
                minPlayers++;

                send(player, art.welcome());
                send(player,Messages.INICIO + "\n");
            }

            addPlayersToPool(playersList, pool);

            while (!endGame){
                start();
            }
            broadcast(art.defeat());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addPlayersToPool(List<Player> playersList, ExecutorService pool) {
        for (Player player: playersList) {
            pool.submit(player);
        }
    }

    public synchronized void send(Player player, String message){

        if (playersList.contains(player)){

            DataOutputStream outMessage = null;

            try{
                outMessage = new DataOutputStream(player.getClientSocket().getOutputStream());
                outMessage.writeBytes(message);

            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public synchronized void broadcast(Player player, int position){
        if (playersList.contains(player)){

            DataOutputStream outMessage = null;

            try{
                outMessage = new DataOutputStream(player.getClientSocket().getOutputStream());
                outMessage.writeByte(position);

            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public synchronized void broadcast(String message) {
        for (Player players : playersList) {

            DataOutputStream outMessage = null;
            try {
                outMessage = new DataOutputStream(players.getClientSocket().getOutputStream());
                outMessage.writeBytes(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Player> getPlayersList() {
        return playersList;
    }


    public void start() throws IOException {
        // TODO: 28/10/2019 jogadores  acumulam inputs. if ready?boolean
        for (Player player: playersList) {
            System.out.println("question asked to player " + player.getName());
            player.askQuestion();
        }
    }

    public int checkPosition(Player player, int position) {

        for (Map.Entry<Integer, Rule> entry : gameRules.entrySet()) {
            if (entry.getKey() == position) {
                send(player,"\n" + entry.getValue().getMessage() + "\n");
                return entry.getValue().getNewPosition();
            }
        }
        return position;
    }

    public void checkVictory(Player player, int position) {

        if (position == WIN_CONDITION || position > WIN_CONDITION) {
            send(player, art.victory());
            minPlayers = 0;
            try {
                serverSocket.close();
                for (Player players:playersList) {
                    players.getClientSocket().close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            endGame = true;
        }
    }
}

