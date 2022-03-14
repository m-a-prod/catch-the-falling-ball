import com.wizylab.duck2d.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SelectLevelView implements View {

    @Override
    public void onTimer(long l) {
        if (Keyboard.onKey(KeyEvent.VK_ESCAPE)) Game.show(MenuView.class);
        boolean click = Mouse.onClick(MouseButton.LEFT);
        if (click && new Rectangle(235, 271, 328, 58).contains(Mouse.x(), Mouse.y())) {
            Environment.put("level", 1); //обычный уровень
            Game.show(GameView.class);
        }

        if (click && new Rectangle(234, 440, 329, 58).contains(Mouse.x(), Mouse.y())) {
            Environment.put("level", 2); //Настраиваемый
            Game.show(GameView.class);
        }
        if (click && new Rectangle(234, 354, 329, 58).contains(Mouse.x(), Mouse.y())) {
            Environment.put("level", 3); //легкий
            Game.show(GameView.class);
        }
        if (click && new Rectangle(234, 188, 329, 58).contains(Mouse.x(), Mouse.y())) {
            Environment.put("level", 4); //сложный
            Game.show(GameView.class);
        }
        //Кнопка назад
        if (click && new Rectangle(550, 533, 250, 44).contains(Mouse.x(), Mouse.y())) Game.show(MenuView.class);
    }

    @Override
    public void onDraw(Graph g) {
        g.putImage("select-level/bg", 0, 0);
        g.putImage("select-level/back", 550, 533);
        g.putImage("select-level/def", 235, 271);
        g.putImage("select-level/manual", 234, 440);
        g.putImage("select-level/hard", 234, 188);
        g.putImage("select-level/light", 234, 354);
    }
}