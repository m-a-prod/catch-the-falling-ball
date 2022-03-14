import com.wizylab.duck2d.*;

import java.awt.*;

public class WinView implements View {
    int count;

    @Override
    public void onShow() {
        count = Environment.get("counter");
    }

    @Override
    public void onTimer(long l) {
        boolean click = Mouse.onClick(MouseButton.LEFT);
        if (click && new Rectangle(231, 381, 339, 60).contains(Mouse.x(), Mouse.y())) Game.show(MenuView.class);
    }

    @Override
    public void onDraw(Graph g) {
        g.putImage("win/bg", 0, 0);
        g.setColor(Color.white);
        g.setTextStyle(1, 2, 20);
        g.ctext(319, 323, 481, 345, "Счет :" + count);
        g.putImage("win/exitmenu", 231, 381);
    }
}