package org.rrhs.connectfour.game;

import mayflower.Actor;
import mayflower.Picture;

public class PaymentButton extends Actor {
        Picture q;
        Picture p;
        boolean nextTick;
        ConnectFourClient c; 
    public PaymentButton(ConnectFourClient cl) {
        p = new Picture("img/instantwin.png");
        q = new Picture("img/checkconsole.png");
        q.resize(250, 75);
        p.resize(250, 75);
        setPicture(p);
        nextTick = false;
        c=cl;
    }

    @Override
    public void update() {
        if (nextTick) {
            //new PaymentHandler(this);
            c.send("wingame"); 
            nextTick = false;
        }
        if (isClicked()) {
            setPicture(q);
            nextTick = true;
        }
    }
}
