import com.wizylab.duck2d.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SelectLevelView implements View {
    public void onShow() {
        Environment.put("level", 0);
        setSettings(0, 0, 0, 0, 0, 0, 0);
    }

    @Override
    public void onTimer(long l) {
        if (Keyboard.onKey(KeyEvent.VK_ESCAPE)) Game.show(MenuView.class);
        boolean click = Mouse.onClick(MouseButton.LEFT);
        if (click && new Rectangle(235, 271, 328, 58).contains(Mouse.x(), Mouse.y())) {
            //Обычный уровень
            setSettings(60000, 40, 5, 20, 0.1, 0.3, 150);
            Game.show(GameView.class);
        }

        if (click && new Rectangle(234, 440, 329, 58).contains(Mouse.x(), Mouse.y())) {
            //Настраиваемый уровень
            Environment.put("level", 1);
            Game.show(GameView.class);
        }

        if (click && new Rectangle(234, 354, 329, 58).contains(Mouse.x(), Mouse.y())) {
            //Легкий уровень
            setSettings(60000, 20, 10, 20, 0.1, 0.3, 100);
            Game.show(GameView.class);
        }
        if (click && new Rectangle(234, 188, 329, 58).contains(Mouse.x(), Mouse.y())) {
            //Сложный уровень
            setSettings(60000, 100, 7, 14, 0.1, 0.5, 50);
            Game.show(GameView.class);
        }
        //Кнопка назад
        if (click && new Rectangle(550, 533, 250, 44).contains(Mouse.x(), Mouse.y())) Game.show(MenuView.class);
    }

    private void setSettings(int remainingTime, int finalResult, int radiusMin, int radiusMax, double speedMin, double speedMax, int numberOfBalls) {
        Environment.put("remainingTime", remainingTime);
        Environment.put("finalResult", finalResult);
        Environment.put("radiusMin", radiusMin);
        Environment.put("radiusMax", radiusMax);
        Environment.put("speedMin", speedMin);
        Environment.put("speedMax", speedMax);
        Environment.put("numberOfBalls", numberOfBalls);
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