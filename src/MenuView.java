import com.wizylab.duck2d.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuView implements View {
    private static final Rectangle[] BUTTONS = {
            new Rectangle(180, 212, 440, 78), //Кнопка выбора уровня
            new Rectangle(550, 467, 250, 44), //О нас
            new Rectangle(551, 534, 249, 44), //Выход
            new Rectangle(180,326,440,78) //Статистика
    };

    @Override
    public void onTimer(long l) {
        if (Keyboard.onKey(KeyEvent.VK_ESCAPE)) System.exit(0);
        boolean click = Mouse.onClick(MouseButton.LEFT);
        if (click && BUTTONS[0].contains(Mouse.x(), Mouse.y())) Game.show(SelectLevelView.class);
        if (click && BUTTONS[2].contains(Mouse.x(), Mouse.y())) System.exit(0);
        if (click && BUTTONS[1].contains(Mouse.x(), Mouse.y())) Game.show(AboutView.class);
        if (click && BUTTONS[3].contains(Mouse.x(), Mouse.y())) Game.show(StatisticsView.class);
    }

    @Override
    public void onDraw(Graph g) {
        g.putImage("menu-bg", 0, 0);
        g.putImage("level-selection", BUTTONS[0].x, BUTTONS[0].y);
        g.putImage("about", BUTTONS[1].x, BUTTONS[1].y);
        g.putImage("exit", BUTTONS[2].x, BUTTONS[2].y);
        g.putImage("statistics", BUTTONS[3].x, BUTTONS[3].y);
    }
}