import com.wizylab.duck2d.Environment;
import com.wizylab.duck2d.Game;
import com.wizylab.duck2d.Window;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Environment.put("window.title", "Catch the falling ball");
        Game.addView(new GameView(), new MenuView(), new AboutView(), new WinView(), new LoseView(), new SelectLevelView());
        Game.start(MenuView.class);
        try {
            Window.instance().setIconImage(ImageIO.read(new File("assets/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}