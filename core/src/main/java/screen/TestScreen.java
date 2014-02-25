package screen;

import react.UnitSlot;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.ui.*;
import tripleplay.ui.layout.AxisLayout;


public class TestScreen extends UIScreen {

    private final ScreenStack ss;
    private Root root;

    public TestScreen(ScreenStack ss){
        this.ss = ss;
    }



    @Override
    public void wasShown() {
        super.wasShown();
        root = iface.createRoot(
                AxisLayout.vertical().gap(15),
                SimpleStyles.newSheet(), layer);
        root.addStyles(Style.BACKGROUND.is(Background.bordered(0xFFCCCCCC, 0xFF99CCFF, 5).inset(5, 10)));
        root.setSize(width(), height());
        root.add(new Button("Back").onClick(new UnitSlot() {
            @Override
            public void onEmit() {
                ss.push(new TestScreen(ss));
            }
        }));
    }
}
