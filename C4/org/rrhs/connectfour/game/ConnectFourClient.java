package org.rrhs.connectfour.game;

import mayflower.*;
import mayflower.net.*;

import java.util.*; 
import java.io.*; 

public class ConnectFourClient extends Client
{
    ConnectFour game; 
    Piece piece; 
    ConnectFourStage stage;
    Mayflower m;

    public ConnectFourClient(Mayflower m)
    {
        this.m = m;
        Scanner in = new Scanner(System.in);
        System.out.println("Use localhost to connect to a server running on your computer.");
        System.out.print("IP Address > ");
        String ip = "localhost";

        //System.out.print("Port > ");
        //int port = in.nextInt();
        int port = 1234;                //default server port

        System.out.println("Connecting...");
        connect(ip, port);          //connect to the server at the specified ip and port
    }

    /*
     *  Messages:
     *      youare [piece]
     *      addpiece [row] [col]
     *      winner disconnect
     *      error [message...]
     */
    public void process(String message)
    {
        //output the message recieved from the server. This is useful for debugging
        System.out.println("Message from server: " + message);

        //split message into an array of Strings separated by space characters
        //  "addpiece 1 2" would become the array ["addpiece", "1", "2"]
        String[] parts = message.split(" ");

        //use the first value in the array as the "command"
        //use if statements to determine which command to process
        if("start".equals(parts[0]))
        {

            //TODO:
            //parts[1] contains "X" or "O", this is your piece
            Piece piece = parts[1].equals("red")?Piece.red:Piece.yellow; 
            game = new ConnectFour(); 
            stage = new ConnectFourStage(this,game,piece); 
            m.setStage(stage);
            /*
            TicTacToePiece piece = parts[1].equals("X")?TicTacToePiece.X:TicTacToePiece.O;

            game = new TicTacToe(); 
            System.out.println("hi3"); 
            stage = new TicTacToeStage(this,game,piece); 
            System.out.println("hi2"); 
            Mayflower object = new Mayflower("TicTacToe", 800, 600, stage); 
            System.out.println("hi"); 
             */

            //1. Create a TicTacToePiece variable to store your piece
            //2. Create a new TicTacToe game and store it in the game instance variable
            //3. Create a new TicTacToeStage and pass it this client, the game, and your piece
            //4. create a new Mayflower object with the stage you just created. This will open the GUI and start the game
        }
        else if("addpiece".equals(parts[0]))
        {
            //TODO:
            //parts[1] contains the row
            //parts[2] contains the column

            //game.addPiece(Integer.parseInt(parts[1]),Integer.parseInt(parts[2]));
            //game.nextPlayer(); 
            //stage.updatePiece(game.getPiece(Integer.parseInt(parts[1]),Integer.parseInt(parts[2])),Integer.parseInt(parts[1]),Integer.parseInt(parts[2])); 
            stage.addPiece(parts[2], parts[1]);
            game.placePiece(Integer.parseInt(parts[1])); 
            System.out.println("does this");
            //1. add a piece to the game at the specified (row, col)
            //2. tell the game it is the next player's turn
            //3. tell the stage to update the piece at (row, col) to the correct type (X or O) depending on which piece was just added to (row, col)
        }
        else if("win".equals(parts[0]))
        {
            stage.getMayflower().setStage(new WinScreen("win"));
        }
        else if("lose".equals(parts[0]))
        {
            stage.getMayflower().setStage(new WinScreen("not win"));
        }
        else if("nextPlayer".equals(parts[0]))
        {
            game.nextPlayer(); 
        }
        else if("error".equals(parts[0]))
        {
            //Output the error message sent from the server
            System.out.println(message);
        }
        else if("winner".equals(parts[0]))
        {
            //Congratulations, you win becuase you opponent quit!
            System.out.println("Opponent disconnected. You win!");
        } else if ("nextPlayer".equals(parts[0])) {
            game.nextPlayer();
        }

    }

    public void onDisconnect(String message)
    {
        System.out.println("Disconnected from server.");
    }

    public void onConnect()
    {
        System.out.println("Connected!");
    }
}