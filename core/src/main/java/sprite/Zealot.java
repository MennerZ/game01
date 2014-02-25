package sprite;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.*;
import playn.core.util.Callback;
import playn.core.util.Clock;
import screen.GamePlay;
import screen.TestScreen;
import tripleplay.game.UIScreen;



public class Zealot extends UIScreen{

    private int spriteIndex = 0;
    private boolean hasload = false;
    private Sprite sprite;
    public static final int UPDATE_RATE=25;
    private int e = 0;
    private int hh = 100;
    private int f = 0;
    private int offset = 0;
    private float xx = 25.0f;
    private Hp hpzealot;
    private World world;
    private Body body;


    public enum State{
        IDLE,RUN,ATTK,IDLEL,RUNL,ATTKL,DIE,JUMP
    };

    private State state = State.IDLE;





    public Zealot(final World world,final float x_px, final float y_px){
        sprite = SpriteLoader.getSprite("images/zealot.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f,
                        sprite.height() / 2f);
                sprite.layer().setTranslation(x_px, y_px);

                body = initPhysicsBody(world,
                        GamePlay.M_PER_PIXEL * x_px,
                        GamePlay.M_PER_PIXEL * y_px);

                hasload = true;


            }

            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error loading image!",cause);

            }

        });



        sprite.layer().addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                state = State.DIE;
                spriteIndex = -1;
                e = 0;
            }
        });
        PlayN.keyboard().setListener(new Keyboard.Listener() {
            @Override
            public void onKeyDown(Keyboard.Event event) {

                if (event.key() == Key.RIGHT) {
                    state = State.RUN;
                    body.applyForce(new Vec2(100f,0f),body.getPosition());
                    //e = 0;
                }
                if (event.key() == Key.LEFT) {
                    state = State.RUNL;
                    body.applyForce(new Vec2(-100f,0f),body.getPosition());
                    //e = 0;
                }
                if (event.key() == Key.SPACE) {
                    state = State.JUMP;
                    body.applyForce(new Vec2(0f,-200f),body.getPosition());

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
        });





    }

    private Body initPhysicsBody(World world, float x, float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0,0);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(56 * GamePlay.M_PER_PIXEL / 2,
                        sprite.layer().height()* GamePlay.M_PER_PIXEL /2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.4f;
        fixtureDef.density = 0.1f;

        body.createFixture(fixtureDef);

        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x,y), 0f);
        return body;



    }


    public void update(int delta) {
        if (!hasload) return;
        e += delta;
        f += delta;
 



        if (e > 180){
            switch (state) {
                case IDLE:offset = 0;
                    break;
                case RUN:offset = 4;
                    break;
                case ATTK:offset = 8;
                    if (spriteIndex == 8){
                        state = State.IDLE;
                    }
                    break;
                case IDLEL:offset = 10;
                    break;
                case RUNL:offset = 14;
                    break;
                case ATTKL:offset = 18;
                    if (spriteIndex == 19){
                        state = State.IDLEL;
                    }
                    break;
                case DIE:offset = 21;
                    if (spriteIndex == 23){
                        state = State.DIE;
                    }
                    break;
            }
            spriteIndex = offset + ((spriteIndex +1)%4);
            sprite.setSprite(spriteIndex);
            e = 0;
        }
        if (body.getAngle() > 1.5 ){
            body.getAngle()=0;
        }


       /* if (state == State.RUN){
                xx += 2f ;
                sprite.layer().setTranslation(xx,380);

        }
        if (state == State.RUNL){
            xx -= 2f ;
            sprite.layer().setTranslation(xx,380);

        }*/


    }

    @Override
    public void paint(Clock clock) {
        if(!hasload) return;
        sprite.layer().setTranslation(
                (body.getPosition().x / GamePlay.M_PER_PIXEL) -10,
                body.getPosition().y / GamePlay.M_PER_PIXEL);

        sprite.layer().setRotation(body.getAngle());

    }

    public Layer layer(){
        return sprite.layer();
    }
}
