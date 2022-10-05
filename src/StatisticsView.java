import com.wizylab.duck2d.*;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
        if (click && new Rectangle(557, 458, 243, 43).contains(Mouse.x(), Mouse.y())) reset();
    }

    @Override
    public void onDraw(Graph g) {
        g.putImage("statistics/bg", 0, 0);
        g.putImage("statistics/back", 557, 519);
        g.putImage("statistics/reset", 557, 458);
        g.setColor(Color.white);
        g.setFont(Fonts.get("font", 1, 30));
        g.ctext(510, 170, 509, 196, String.valueOf(finalCounter));

    }
    public void reset(){
        try (FileWriter fw = new FileWriter("statistics")) {
            fw.write("" + 0);
            fw.flush();
            finalCounter =0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}