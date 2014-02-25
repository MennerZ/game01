package sprite;

import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import tripleplay.game.UIScreen;

/**
 * Created by Anupat on 29/1/2557.
 */
public class Hp extends UIScreen{

    private int spriteIndex = 0;
    private boolean hasload = false;
    private Sprite sprite;
    public static final int UPDATE_RATE=25;


    public enum State{
        HPP
    };

    private State state = State.HPP;

    private int e = 0;
    private int hh = 100;
    private int f = 0;
    private int offset = 0;
    private float xx = 25.0f;

    public Hp(final float x, final float y){
        sprite = SpriteLoader.getSprite("images/hp.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f,
                        sprite.height() / 2f);
                sprite.layer().setTranslation(x + 25.0f, y);
                hasload = true;
            }

            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error loading image HP!",cause);

            }

        });



        /*sprite.layer().addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                state = State.DIE;
                spriteIndex = -1;
                e = 0;
            }
        });*/



        /*PlayN.keyboard().setListener(new Keyboard.Listener() {
            @Override
            public void onKeyDown(Keyboard.Event event) {

                if (event.key() == Key.RIGHT) {
                    state = State.RUN;
                    spriteIndex = 1;
                    e = 0;
                }
                if (event.key() == Key.LEFT) {
                    state = State.RUNL;
                    spriteIndex = 1;
                    e = 0;
                }
                if (event.key() == Key.SPACE) {

                    if (state == State.IDLE) {
                        state = State.ATTK;
                        spriteIndex = -1;
                        e = 0;
                    }
                    if (state == State.IDLEL) {
                        state = State.ATTKL;
                        spriteIndex = 0;
                        e = 0;
                    }
                }
            }

            @Override
            public void onKeyTyped(Keyboard.TypedEvent event) {

            }

            @Override
            public void onKeyUp(Keyboard.Event event) {
                if (event.key() == Key.RIGHT) {
                    state = State.IDLE;
                    spriteIndex = 0;
                    e = 0;
                }
                if (event.key() == Key.LEFT) {
                    state = State.IDLEL;
                    spriteIndex = 0;
                    e = 0;
                }
            }
        });*/
    }

    public void update(int delta) {
        if (!hasload) return;
        e += delta;

        if (e > 1000){
            switch (state) {
                case HPP:offset = 0;
                    break;
            }
            spriteIndex = offset + ((spriteIndex + 1)%3);
            sprite.setSprite(spriteIndex);
            e = 0;
        }
    }

    public Layer layer(){
        return sprite.layer();
    }

    public State state(){
        return state;
    }

}
