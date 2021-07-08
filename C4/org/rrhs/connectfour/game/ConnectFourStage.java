package org.rrhs.connectfour.game;

//import jdk.internal.util.xml.impl.Input;
import mayflower.*;
//import org.rrhs.connectfour.game2.Board;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;

public class ConnectFourStage extends Stage {
    Text currentPlayer;
    Text winner;
    Text title;
    ConnectFour game;
    Piece piece;
    int win =0; 

    public ConnectFourStage(ConnectFourClient cl, ConnectFour game, Piece p) {
        //add background stuff
        this.game = game;
        this.piece = p;
        //showBounds(true);
        setBackground("img/board.png");

        setBackgroundColor(Color.DARK_GRAY);
        title = new Text("Connect Four!", Color.WHITE);
        addActor(title, 0, 0);
        title.setFont("Courier New");

        currentPlayer = new Text("Loading...", Color.WHITE);
        addActor(currentPlayer, 0, 30);
        currentPlayer.setFont("Courier New");

        winner = new Text(" ", Color.WHITE);
        addActor(winner, 0, 60);
        winner.setFont("Courier New");
        addActor(new Board(), 400, 300);
        addActor(new PaymentButton(cl), 650, 50);
        addActor(new Click(cl, 3), 400, 335);
        addActor(new Click(cl, 0), 215, 335);
        addActor(new Click(cl, 1), 275, 335);
        addActor(new Click(cl, 2), 335, 335);
        addActor(new Click(cl, 4), 460, 335);
        addActor(new Click(cl, 5), 525, 335);
        addActor(new Click(cl, 6), 585, 335);
    }

    public void update() 
    {
        //text stuff
        if (game.getCurrentPlayer() == piece)
            currentPlayer.setText("It's your turn.");
        else
            currentPlayer.setText("Waiting for opponent...");

        if (game.winner() != null)
        {
            win++; 
            if(win>120)
            {
                if (game.winner() == piece)
                    getMayflower().setStage(new WinScreen("win"));
                else
                    getMayflower().setStage(new WinScreen("not win"));
            }
        } 
        else if (game.isEmpty())
            getMayflower().setStage(new WinScreen("tie"));

    }

    public void addPiece(String type, String col) 
    {

        addActor(new PieceActor(type), (Integer.parseInt(col) * 62) + 215, 100);

    }
}
