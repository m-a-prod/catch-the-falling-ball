import com.wizylab.duck2d.*;

import java.awt.*;

public class LoseView implements View {
    int count;
    @Override
    public void onTimer(long l) {
        boolean click = Mouse.onClick(MouseButton.LEFT);
        if (click && new Rectangle(231, 381, 339, 60).contains(Mouse.x(), Mouse.y())) Game.show(MenuView.class);
    }

    @Override
    public void onShow() {
        count = Environment.get("counter");
    }

    @Override
    public void onDraw(Graph g) {
        g.putImage("lose/bg", 0, 0);
        g.setColor(Color.white);
        g.setFont(Fonts.get("font", 1, 40));
//        g.setTextStyle(1, 2, 20);
        g.ctext(319,323, 481,345, "Счет :" + count);
        g.putImage("lose/exitmenu", 231, 381);
    }
}
