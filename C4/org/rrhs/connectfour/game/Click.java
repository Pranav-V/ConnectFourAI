package org.rrhs.connectfour.game;

import mayflower.*; 

public class Click extends Actor
{
    ConnectFourClient connect; 
    ConnectFourAI ai; 
    int column; 
    boolean two = false; 
    public Click(ConnectFourClient c, int col)
    {
        setPicture("img/click.png"); 
        connect = c; 
        column = col; 
        two = true; 
    }
    public Click(ConnectFourAI a,int col)
    {
        setPicture("img/click.png"); 
        column = col;
        ai = a; 
    }
    public void update()
    {
        //send location
        if(isClicked() && two)
        {
            connect.send("place " + column); 
        }
        if(isClicked() && ai != null)
        {
            ai.addPiece("red", column + ""); 
        }
    }
}