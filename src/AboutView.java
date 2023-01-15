import com.wizylab.duck2d.*;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AboutView implements View {

    @Override
    public void onTimer(long l) {
        boolean click = Mouse.onClick(MouseButton.LEFT);
        if (click && new Rectangle(275, 486, 250, 44).contains(Mouse.x(), Mouse.y())) Game.show(MenuView.class);
        if (click && new Rectangle(275, 416, 250, 44).contains(Mouse.x(), Mouse.y())) openGitHub();

    }

    @Override
    public void onDraw(Graph g) {
        g.putImage("about/bg-about", 0, 0);
        g.putImage("about/back", 275, 486);
        g.putImage("about/repo-git", 275, 416);
    }

    public void openGitHub() {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/m-a-prod/catch-the-falling-ball"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}