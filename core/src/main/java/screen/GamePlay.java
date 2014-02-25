package screen;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.CanvasImage;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import playn.core.util.Clock;
import sprite.Hp;
import sprite.Zealot;
import sut.game04.core.HomeScreen;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.ui.Root;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;


public class GamePlay extends Screen {

    public static float M_PER_PIXEL = 1 / 26.666667f;
    //Size World
    private static int width = 24;
    private static int height = 18;

    private World world;

    private Zealot z;
    private Hp hh;
    private Root root;
    private DebugDrawBox2D debugDraw;
    private boolean showDebugDraw = true;


    private float x = 250f,y = 380f,xx = 200f,yy = 30f;
    private float ix = 25f , iy = 25f;
    private Image backIm,bg,pause;
    private ImageLayer backLayer,bgLayer,pauseLayer ;
    private final ScreenStack ss;



    public GamePlay(ScreenStack ss){
        this.ss = ss;
    }

    @Override
    public void wasAdded() {

        super.wasAdded();


        hh = new Hp(540f,50f);
        layer.add(hh.layer());


        //Set แรงโน้มถ่วง
        Vec2 gravity = new Vec2(0.0f,10.0f);
        world = new World(gravity,true);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);


        //วาดขอบเขต Debug
        if (showDebugDraw){
            CanvasImage image = graphics().createImage(
                    (int)(width / GamePlay.M_PER_PIXEL),
                    (int)(height / GamePlay.M_PER_PIXEL));
            layer.add(graphics().createImageLayer(image));
            debugDraw = new DebugDrawBox2D();
            debugDraw.setCanvas(image);
            debugDraw.setFlipY(false);
            debugDraw.setStrokeAlpha(150);
            debugDraw.setFillAlpha(75);
            debugDraw.setStrokeWidth(2.0f);
            debugDraw.setFlags(DebugDraw.e_shapeBit|
                               DebugDraw.e_jointBit|
                               DebugDraw.e_aabbBit
            );
            debugDraw.setCamera(0,0,1f/GamePlay.M_PER_PIXEL);
            world.setDebugDraw(debugDraw);
        }

        Body ground = world.createBody(new BodyDef());
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsEdge(new Vec2(2f,height-2),
                              new Vec2(width-2f,height-2f));
        ground.createFixture(groundShape,0.0f);



        //Set พื้นหลัง
        bg = assets().getImage("images/bgg.png");
        bgLayer = graphics().createImageLayer(bg);
        graphics().rootLayer().add(bgLayer);


        //Set ปุ่มหยุด
        pause = assets().getImage("images/pause.png");
        pauseLayer = graphics().createImageLayer(pause);
        graphics().rootLayer().add(pauseLayer);
        pauseLayer.setTranslation(50,10);


        //Set ปุ่มกลับ
        backIm = assets().getImage("images/Back2.png");
        backLayer = graphics().createImageLayer(backIm);
        graphics().rootLayer().add(backLayer);
        backLayer.setTranslation(10,10);


        backLayer.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                final Screen home = new HomeScreen(ss);
                ss.push(home);
            }
        });

        createBox();
        z = new Zealot(world,x,y);
        layer.add(z.layer());

    }



    private void createBox(){
        BodyDef bf = new BodyDef();
        bf.type = BodyType.DYNAMIC;
        bf.position = new Vec2(0,0);

        Body body = world.createBody(bf);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f,1f);
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 0.1f;
        fd.friction = 0.1f;
        fd.restitution = 1f;
        body.createFixture(fd);
        body.setLinearDamping(0.5f);
        body.setTransform(new Vec2(10f,0f),0);
    }


    /*@Override
    public void wasShown() {
        super.wasShown();
        root = iface.createRoot(
                AxisLayout.vertical().gap(15),
                SimpleStyles.newSheet(), layer);
        //root.addStyles(Style.BACKGROUND.is(Background.bordered(0xFFCCCCCC, 0xFF99CCFF, 5).inset(5, 10)));
        root.setSize(width(), height());
        root.add(new Button("Back").onClick(new UnitSlot() {
            @Override
            public void onEmit() {
                ss.remove(ss.top());
            }
        }));
    }*/

    @Override
    public void update(int delta) {
        super.update(delta);
        z.update(delta);
        hh.update(delta);
        world.step(0.033f,10,10);

    }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        if(showDebugDraw){
            debugDraw.getCanvas().clear();
            world.drawDebugData();
        }
        z.paint(clock);
    }
}

