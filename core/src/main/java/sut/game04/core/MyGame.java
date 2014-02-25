package sut.game04.core;

import playn.core.Game;
import playn.core.Key;
import playn.core.Keyboard;
import playn.core.PlayN;
import playn.core.util.Clock;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

public class MyGame extends Game.Default {

    public static final int UPDATE_RATE=25;
    private ScreenStack ss = new ScreenStack();
    protected final Clock.Source clock = new Clock.Source(UPDATE_RATE);


    public MyGame() {
        super(UPDATE_RATE);
    }

    @Override
    public void init() {
        final Screen home = new HomeScreen(ss);
        ss.push(home);
        ss.push(new HomeScreen(ss));
        PlayN.keyboard().setListener(new Keyboard.Listener() {
            @Override
            public void onKeyDown(Keyboard.Event event) {

            }

            @Override
            public void onKeyTyped(Keyboard.TypedEvent typedEvent) {

            }

            @Override
            public void onKeyUp(Keyboard.Event event) {
                if(event.key()== Key.ESCAPE){
                    ss.popTo(home);
                }
            }
        }
        );

    }

    @Override
    public void update(int delta) {
        ss.update(delta);
    }

    @Override
    public void paint(float alpha) {
        clock.paint(alpha);
        ss.paint(clock);
    }
}