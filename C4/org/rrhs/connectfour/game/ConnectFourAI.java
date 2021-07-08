package org.rrhs.connectfour.game;
import mayflower.*; 
import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.*; 

public class ConnectFourAI extends Stage
{
    Text title;
    ConnectFour game = new ConnectFour();
    Piece player; 
    Piece cpu; 
    int inittimer = 0; 
    int aitimer = 0; 
    int win; 
    int playertimer = 0; 
    Text currentPlayer; 
    public ConnectFourAI()
    {
        //make background and initialize everything
        setBackground("img/board.png");
        currentPlayer = new Text("Loading...", Color.WHITE);
        addActor(new Board(), 400, 300);
        setBackgroundColor(Color.DARK_GRAY);
        title = new Text("Connect Four!", Color.WHITE);
        addActor(title, 0, 0);
        currentPlayer.setFont("Courier New");
        title.setFont("Courier New");
        addActor(currentPlayer, 0, 30);
        addActor(new Click(this,3), 400, 335);
        addActor(new Click(this,0), 215, 335);
        addActor(new Click(this,1), 275, 335);
        addActor(new Click(this,2), 335, 335);
        addActor(new Click(this,4), 460, 335);
        addActor(new Click(this,5), 525, 335);
        addActor(new Click(this,6), 585, 335);
        player=Piece.red;
        cpu = Piece.yellow; 
        win = 0; 
    }

    public void update()
    {
        //text updates
        if (game.getCurrentPlayer() == Piece.red)
            currentPlayer.setText("It's your turn.");
        else
            currentPlayer.setText("Waiting for CPU...");
        //world change
        if (game.winner() != null)
        {
            win++; 
            if(win>130)
            {
                if (game.winner() == player)
                    getMayflower().setStage(new WinScreen("win"));
                else
                    getMayflower().setStage(new WinScreen("not win"));
            }
        } 
        else if (game.isEmpty())
            getMayflower().setStage(new WinScreen("tie"));
           
        else if(game.getCurrentPlayer().toString().equals("yellow") && aitimer>120)
        {
            //ai turn
            int i= calculate(); 
            System.out.println(i); 
            addPiece("yellow", ""+ i); 
        }
        inittimer++; 
        aitimer++; 
        playertimer++; 
    }

    public void addPiece(String type, String col) 
    {
        //adds piece
        if(type.equals(game.getCurrentPlayer().toString()) && inittimer>60 && game.winner()==null && !game.isEmpty() && playertimer>30)
        {
            boolean val = game.placePiece(Integer.parseInt(col));
            System.out.println(val);
            game.printAll();
            if(val)
            {
                addActor(new PieceActor(type), (Integer.parseInt(col) * 62) + 215, 100);
                game.nextPlayer(); 
                aitimer = 0; 
                playertimer = 0; 
            }
        }

    }

    public int calculate()
    {
        //check for current winning move
        for(int i=0; i<7; i++)
        {
            boolean val = game.placePiece(i); 
            if(!val)
            {
                continue; 
            }
            if(game.winner()==cpu)
            {
                game.removePiece(i); 
                return i; 
            }
            game.removePiece(i); 
        }
        //switch players for next step
        game.nextPlayer(); 
        //check if opponent has winning move
        for(int i=0; i<7; i++)
        {
            boolean val = game.placePiece(i); 
            if(!val)
            {
                continue; 
            }
            if(game.winner()==player)
            {
                game.nextPlayer();
                game.removePiece(i); 
                return i; 
            } 
            game.removePiece(i); 
        }
        game.nextPlayer(); 
        //check for anomaly
        for(int r=0; r<6; r++)
        {
            for(int c=1; c<5; c++)
            {
                if(game.loc(r,c)==Piece.red && game.loc(r,c+1)==Piece.red && game.loc(r,c+2)==null && game.loc(r,c-1)==null)
                {
                    return c-1; 
                }
            }
        }
        Set<Integer> possible = new HashSet<Integer>(); 
        //check for best possible move
        while(true)
        {
            if(possible.size()==7)
            {
                return (int)(Math.random()*7); 
            }
            int first = (int)(Math.random()*3)+2; 
            int[] others = {0,1,5,6}; 
            int second = (int)(Math.random()*4); 
            second = others[second]; 
            
            int check = (int)(Math.random()*2); 
            
            int c = check==0?first:second; 
            possible.add(c); 
            boolean val = game.placePiece(c); 
            if(!val)
            {
                continue; 
            }
            if(game.isEmpty())
            {
                game.removePiece(c);  
                return c; 
            }
            boolean does = true; 
            for(int i=0; i<7; i++)
            {
                boolean please = game.placePiece(i); 
                if(!please)
                {
                    continue; 
                }
                if(game.winner()==Piece.red)
                {
                    does = false; 
                    game.removePiece(i); 
                    break; 
                }
                game.removePiece(i); 
            }
            if(does)
            {
                game.removePiece(c);
                return c;
            }
            game.removePiece(c);
        }
    }
}
