import com.wizylab.duck2d.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GameView implements View {
    int counter, level;
    int remainingTime;
    int finalResult;
    int radiusMin, radiusMax;
    double speedMin, speedMax;
    int numberOfBalls;
    boolean pause = false;
    Circle[] circles;

    @Override
    public void onShow() {
        counter = 0;
        pause = false;
        level = Environment.get("level");
        if (level == 1) scanFile();
        else {
            remainingTime = Environment.get("remainingTime");
            finalResult = Environment.get("finalResult");
            radiusMin = Environment.get("radiusMin");
            radiusMax = Environment.get("radiusMax");
            speedMin = Environment.get("speedMin");
            speedMax = Environment.get("speedMax");
            numberOfBalls = Environment.get("numberOfBalls");
        }
        circles = new Circle[numberOfBalls];
        for (int i = 0; i < circles.length; i++) circles[i] = new Circle(radiusMin, radiusMax, speedMin, speedMax);
    }

    @Override
    public void onTimer(long t) {
        if (Keyboard.onKey(KeyEvent.VK_ESCAPE)) pause = !pause;
        boolean click = Mouse.onClick(MouseButton.LEFT);
        if (click && Mouse.x() > 740 && Mouse.y() < 60 && Mouse.x() < 790 && Mouse.y() > 10) pause = !pause;
        if (pause && click && new Rectangle(188, 263, 425, 75).contains(Mouse.x(), Mouse.y())) {
            pause = !pause;
            Game.show(MenuView.class);
        }
        if (!pause) remainingTime = (int) (remainingTime - t);
        if (remainingTime < 0 && counter < finalResult) {
            System.out.println("You lose!");
            System.out.println("Your result is " + counter);
            if (level != 1) saveResultToFile();
            Environment.put("counter", counter); //super important function that you shouldn't remove
            Game.show(LoseView.class);
        } else if (counter >= finalResult) {
            System.out.println("You win!");
            System.out.println("Your result is " + counter);
            if (level != 1) saveResultToFile();
            Environment.put("counter", counter);
            Game.show(WinView.class);
        }
        if (!pause) {
            for (Circle circle : circles) {
                circle.move(t);
                if (circle.y >= 610) circle.reset(radiusMin, radiusMax, speedMin, speedMax);
                if (click && circle.contains(Mouse.x(), Mouse.y())) {
                    counter++;
                    circle.reset(radiusMin, radiusMax, speedMin, speedMax);
                }
            }
        }
    }

    @Override
    public void onDraw(Graph g) {
        for (Circle circle : circles) circle.draw(g);
        if (pause) g.putImage("pause-bg", 0, 0);
        g.setColor(Color.WHITE);
        g.setTextStyle(1, 1, 20);
        g.ctext(0, 0, 800, 100, "" + finalResult + "/" + counter);
        if (pause) {
            g.setFillColor(Color.WHITE);
            g.setTextStyle(1, 1, 20);
            g.ctext(0, 0, 800, 150, "PAUSE");
            g.putImage("exit-to-menu", 188, 263);
        }
        g.setColor(Color.WHITE);
        g.setTextStyle(1, 1, 20);
        g.ctext(0, 0, 800, 50, "" + remainingTime / 1000);
        g.setFillColor(Color.white);
        g.putImage("pause", 740, 10, 50);
    }

    private void scanFile() {
        try (Scanner sc = new Scanner(new File("level.txt"))) {
            remainingTime = sc.nextInt();
            finalResult = sc.nextInt();
            numberOfBalls = sc.nextInt();
            radiusMax = sc.nextInt();
            radiusMin = sc.nextInt();
            speedMax = sc.nextDouble();
            speedMin = sc.nextDouble();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Game.show(SelectLevelView.class);
        }
    }

    private void saveResultToFile() {
        int tempCounter = counter;
        try (Scanner sc = new Scanner(new File("statistics"))) {
            tempCounter += sc.nextInt();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (FileWriter fw = new FileWriter("statistics")) {
            fw.write("" + tempCounter);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}