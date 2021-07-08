package org.rrhs.connectfour.game;
import mayflower.*;
import org.rrhs.connectfour.game.*;
import org.rrhs.connectfour.game.ConnectFourClient;

import java.awt.*;
import java.util.LinkedList;

public class WinScreen extends Stage {
    java.util.List<Picture> images;
    int p;
    Actor pic;
    public WinScreen(String type) {
        images = new LinkedList<>();
        if (type.equals("win")) {
            p = 0;
            for (int i = 1; i < 66; i++) {
                images.add(i-1, new Picture(String.format("img/win/frame_%02d.jpg", i)));
                images.get(i-1).resize(800, 600);
            }
            //pic = new LobbyButton(images[0]);
            //addActor(pic, 0, 0);
            //System.out.println(pic.getPicture());
            Text t = new Text("you have winned");
            t.setFont("Courier New");
            t.setSize(64);
            t.setColor(Color.RED);
            addActor(t, 38, 49);
            Sound s = new Sound("img/winned.wav");
            s.loop();
        } else if (type.equals("not win")) {
            p = 0;
            for (int i = 0; i < 15; i++) {
                images.add(i, new Picture(String.format("img/lose/frame_%02d.jpg", i)));
                images.get(i).resize(800, 600);
            }
            //pic = new LobbyButton(images[0]);
            //addActor(pic, 0, 0);
            //System.out.println(pic.getPicture());
            Text t = new Text("you have lose");
            t.setFont("Comic Sans MS");
            t.setSize(64);
            t.setColor(Color.PINK);
            addActor(t, 297, 437);
            Sound s = new Sound("img/death.wav");
            s.loop();
        } else {
            p = 0;
            for (int i = 0; i < 15; i++) {
                images.add(i, new Picture(String.format("img/lose/frame_%02d.jpg", i)));
                images.get(i).resize(800, 600);
            }
            //pic = new LobbyButton(images[0]);
            //addActor(pic, 0, 0);
            //System.out.println(pic.getPicture());
            Text t = new Text("you have lose");
            t.setFont("Comic Sans MS");
            t.setSize(64);
            t.setColor(Color.PINK);
            addActor(t, 297, 437);
            Sound s = new Sound("img/death.wav");
            s.loop();

        }
    }
    @Override
    public void update() {
        p+=2;
        p %= images.size();
        setBackground(images.get(p));
    }
}
