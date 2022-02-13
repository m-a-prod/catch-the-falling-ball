import com.wizylab.duck2d.*;
import com.wizylab.duck2d.Window;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class GameView implements View {
    int counter;
    int remainingTime;
    int finalResult;
    int radiusMin, radiusMax;
    double speedMin, speedMax;
    int numberOfBalls;
    boolean pause = false;
    //0-x 1-y 2-radius
    Circle[] circles;

    @Override
    public void onShow() {
        int level = Environment.get("level");
        if (level == 2) {
            try (Scanner sc = new Scanner(new File("level.txt"))) {
                remainingTime = sc.nextInt();
                finalResult = sc.nextInt();
                numberOfBalls = sc.nextInt();
                circles = new Circle[numberOfBalls];
                for (int i = 0; i < circles.length; i++)
                    circles[i] = new Circle(radiusMin, radiusMax, speedMin, speedMax);

                radiusMax = sc.nextInt();
                radiusMin = sc.nextInt();
                speedMax = sc.nextDouble();
                speedMin = sc.nextDouble();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (level == 1) {
            counter = 0;
            remainingTime = 60000;
            finalResult = 40;
            radiusMin = 5;
            radiusMax = 20;
            speedMin = 0.1;
            speedMax = 0.3;
            numberOfBalls = 150;
            pause = false;
            circles = new Circle[numberOfBalls];
            for (int i = 0; i < circles.length; i++)
                circles[i] = new Circle(radiusMin, radiusMax, speedMin, speedMax);
        }
    }

    @Override
    public void onTimer(long t) {
        if (Keyboard.onKey(KeyEvent.VK_ESCAPE)) pause = !pause;
        boolean click = Mouse.onClick(MouseButton.LEFT);
        if (pause && click && new Rectangle(188, 263, 425, 75).contains(Mouse.x(), Mouse.y())) {
            pause = !pause;
            Game.show(MenuView.class);
        }
        if (click && Mouse.x() > 740 && Mouse.y() < 60 && Mouse.x() < 790 && Mouse.y() > 10) pause = !pause;
        if (!pause) remainingTime = (int) (remainingTime - t); // высчитывание оставшегося времени
        if (remainingTime < 0 && counter < finalResult) {
            System.out.println("You lose!");
            System.out.println("Your result is " + counter);
            System.exit(0);
        } else if (remainingTime < 0 && counter > finalResult) {
            System.out.println("You win!");
            System.out.println("Your result is " + counter);
            System.exit(0);
        }
        if (!pause) {
            for (Circle circle : circles) {
                circle.move(t);
                if (circle.y >= 610)
                    circle.reset(radiusMin, radiusMax, speedMin, speedMax);
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
        g.ctext(0, 0, 800, 100, "" + counter);
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
}