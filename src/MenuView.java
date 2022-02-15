import com.wizylab.duck2d.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuView implements View {
    @Override
    public void onTimer(long l) {
        if (Keyboard.onKey(KeyEvent.VK_ESCAPE)) System.exit(0);
        boolean click = Mouse.onClick(MouseButton.LEFT);
        if (click && new Rectangle(188, 200, 425, 75).contains(Mouse.x(), Mouse.y())) {
            Environment.put("level", 1);
            Game.show(GameView.class);
        }


        if (click && new Rectangle(188, 300, 425, 75).contains(Mouse.x(), Mouse.y())) {
            Environment.put("level", 2);
            Game.show(GameView.class);
        }
        if (click && new Rectangle(551, 534, 249, 44).contains(Mouse.x(), Mouse.y())) System.exit(0);
       if (click && new Rectangle(550, 467, 250, 44).contains(Mouse.x(), Mouse.y())) Game.show(AboutView.class);
    }

    @Override
    public void onDraw(Graph g) {
        g.putImage("menu-bg", 0, 0);
         g.putImage("about", 550, 467);
        g.putImage("exit", 551, 534);
        g.putImage("file-level", 188, 300);
        g.putImage("normal", 188, 200);
    }
}