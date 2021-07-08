package org.rrhs.connectfour.game;
import mayflower.*;
import org.rrhs.connectfour.game.*;
import org.rrhs.connectfour.game.ConnectFourClient;

import java.awt.*;

public class Lobby extends Stage {
    ConnectFourClient c;
    Actor start1p;
    Actor start2p;
    Mayflower m;
    boolean waiting;
    Text connecting;
    String[] phrases;
    int animPhase;
    Timer t;
    public Lobby() {
        phrases = new String[]{"Waiting for opponent", "Waiting for opponent.", "Waiting for opponent..", "Waiting for opponent..."};
        waiting = false;
        setBackgroundColor(Color.BLACK);
        Picture s1p = new Picture("img/1p.png");
        s1p.resize(225, 75);
        Picture s2p = new Picture("img/2p.png");
        s2p.resize(225, 75);

        start1p = new LobbyButton(s1p);
        start2p = new LobbyButton(s2p);
        Text title = new Text("Connect Four!");
        title.setFont("Calibri Light");
        title.setSize(64);
        title.setColor(Color.WHITE);
        addActor(title, 200, 150);
        addActor(start2p, 575, 500);
        addActor(start1p, 200, 500);
        m = new Mayflower("connect 4!", 800, 600, this);
    }
    @Override
    public void update() {
        if (start2p.isClicked()) {
            c = new ConnectFourClient(m);
            connecting = new Text("Waiting for opponent");
            connecting.setFont("Calibri Light");
            connecting.setColor(Color.WHITE);
            addActor(connecting, 275, 250);
            animPhase = 0;
            waiting = true;
            t = new Timer();
        } else if (start1p.isClicked()) {
            m.setStage(new ConnectFourAI());
        }
        if (waiting) {
            if (t.getTimePassed() > 300) {
                t.reset();
                animPhase++;
                animPhase %= phrases.length;
                connecting.setText(phrases[animPhase]);
            }
        }
    }
}
