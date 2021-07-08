package org.rrhs.connectfour.server;

import org.rrhs.connectfour.game.*;
import mayflower.net.*;

import java.util.*;

public class C4Server extends Server {

    HashMap<Integer, Integer> playerMappings;
    HashMap<Integer, ConnectFour> gameMappings;
    Queue<Integer> lobby;
    Set<Integer> redClients, yellowClients;

    public C4Server(int port) 
    {
        super(port);

        playerMappings = new HashMap<>();
        gameMappings = new HashMap<>();
        lobby = new LinkedList<>();
        redClients = new HashSet<>();
        yellowClients = new HashSet<>();

        System.out.println("Ready and waiting for clients. Use port " + port + " to connect.");
       
    }

    public static void main(String[] args) 
    {
        new C4Server(1234); 
        //make a new board
        ConnectFour test = new ConnectFour(); 
    }

    @Override
    public void process(int i, String s) {
        System.out.println("Message received from client " + i + ": " + s);
        ConnectFour c = gameMappings.get(i);
        String[] args = s.split(" ");
        //split message and then process
        Piece which = redClients.contains(i)?Piece.red:Piece.yellow;
        if (c == null || c.isGameOver()) 
        {
            send(i, "error game not found");
        }

        else if(args[0].equals("place"))
        {
            if(c.getCurrentPlayer()==which)
            {
                boolean placement = c.placePiece(Integer.parseInt(args[1]));
                if (!placement) 
                {   
                    send(i, "error no more space");
                }
                else
                {

                    int other = playerMappings.get(i);
                    //addpiece to each board
                    send(i, "addpiece " + Integer.parseInt(args[1]) + " " +c.getCurrentPlayer().toString());
                    send(other, "addpiece " + Integer.parseInt(args[1]) + " " + c.getCurrentPlayer().toString());
                    c.nextPlayer();
                    //switch player
                    send(i, "nextPlayer"); 
                    send(other, "nextPlayer"); 
                }

            }
        }
        else if(args[0].equals("wingame"))
        {
            //automatic win game for payment
            int other = playerMappings.get(i); 
            send(i, "win"); 
            send(other, "lose"); 
        }
    }

    @Override
    public void onJoin(int i) 
    {
        lobby.add(i);
        System.out.println("Connected to client " + i);
        if (lobby.size() >= 2) {
            ConnectFour c = new ConnectFour();
            int playerA = lobby.remove();
            int playerB = lobby.remove();

            System.out.println("Starting game with " + playerA + " and " + playerB);

            playerMappings.put(playerA, playerB);
            playerMappings.put(playerB, playerA);
            gameMappings.put(playerA, c);
            gameMappings.put(playerB, c);
            //randomly decide colors
            Piece playerAPiece = (int) (Math.random() * 2) == 0 ? Piece.red : Piece.yellow;
            Piece playerBPiece = playerAPiece == Piece.red ? Piece.yellow : Piece.red;

            if (playerAPiece == Piece.red) {
                redClients.add(playerA);
                yellowClients.add(playerB);
            } else {
                redClients.add(playerB);
                yellowClients.add(playerA);
            }
            //initialize the games
            send(playerA, "start " + playerAPiece.toString());
            System.out.println(playerAPiece.toString()); 
            send(playerB, "start " + playerBPiece.toString());
        }
    }

    @Override
    public void onExit(int i) 
    {
        int playerB = playerMappings.get(i);
        disconnect(playerB);
        System.out.println("player " + i + " disconnected... Cleaning up...");

        playerMappings.remove(i);
        playerMappings.remove(playerB);
        gameMappings.remove(i);
        gameMappings.remove(playerB);

    }
}

