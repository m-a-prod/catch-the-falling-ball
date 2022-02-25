import com.wizylab.duck2d.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SelectLevelView implements View {

    @Override
    public void onTimer(long l) {
        if (Keyboard.onKey(KeyEvent.VK_ESCAPE)) Game.show(MenuView.class);
        boolean click = Mouse.onClick(MouseButton.LEFT);
        if (click && new Rectangle(188, 207, 425, 75).contains(Mouse.x(), Mouse.y())) {
            Environment.put("level", 1);
            Game.show(GameView.class);
        }

        if (click && new Rectangle(188, 313, 425, 75).contains(Mouse.x(), Mouse.y())) {
            Environment.put("level", 2);
            Game.show(GameView.class);
        }
        if (click && new Rectangle(550, 533, 250, 44).contains(Mouse.x(), Mouse.y())) Game.show(MenuView.class);
    }

    @Override
    public void onDraw(Graph g) {
        g.putImage("select-level/bg", 0, 0);
        g.putImage("select-level/back", 550, 533);
        g.putImage("select-level/def", 188, 207);
        g.putImage("select-level/manual", 188, 313);
    }
}
