package sut.game04.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import sut.game04.core.MyGame;

public class MyGameHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform.Config config = new HtmlPlatform.Config();
    // use config to customize the HTML platform, if needed
    HtmlPlatform platform = HtmlPlatform.register(config);
    platform.assets().setPathPrefix("game04/");
    PlayN.run(new MyGame());
  }
}
