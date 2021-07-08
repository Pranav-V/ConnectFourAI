package org.rrhs.connectfour.game;

import mayflower.*; 

public class PieceActor extends Actor
{
    //  1-red   2-yellow
    public boolean touch = true;  
    public PieceActor(String type)
    {
        setDrawOrder(0); 
        setPicture("img/"+(type.equals("red")?"red":"yellow")+".png");
       
    }
    
    public void update()
    {
        Actor[] act = getStage().getActors(); 
        for(int i=0; i<act.length && touch; i++)
        {
            if(isTouching(act[i]) && act[i] instanceof PieceActor)
            {
                touch = false; 
                setPosition(getX(), getY() -1); 
                if(getY()>457 && getY()<=477)
                {
                    setPosition(getX(), getY()-2);
                }
                if(getY()<440)
                {
                    //setPosition(getX(),getY()); 
                }
            }
        }
        if(getY() <=527 && touch)
        {
            setPosition(getX(), getY() + 2); 
            
        }
    }
    
}
