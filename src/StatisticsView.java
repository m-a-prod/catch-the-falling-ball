import com.wizylab.duck2d.*;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class StatisticsView implements View {
    int finalCounter;

    @Override
    public void onShow() {
        try (Scanner sc = new Scanner(new File("statistics"))) {
            finalCounter = sc.nextInt();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTimer(long l) {
        boolean click = Mouse.onClick(MouseButton.LEFT);
        if (click && new Rectangle(557, 519, 243, 43).contains(Mouse.x(), Mouse.y())) Game.show(MenuView.class);
    }

    @Override
    public void onDraw(Graph g) {
        g.putImage("statistics/bg", 0, 0);
        g.putImage("statistics/back", 557, 519);
        g.setColor(Color.white);
        g.setFont(Fonts.get("font", 1, 30));
        g.ctext(510, 170, 509, 196, String.valueOf(finalCounter));

    }
}