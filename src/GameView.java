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
    int remainingTime, nchRemainingTime;
    int finalResult;
    int radiusMin, radiusMax;
    double speedMin, speedMax;
    int numberOfBalls;
    boolean pause = false;
    boolean win;
    boolean end;
    //0-x 1-y 2-radius
    Circle[] circles;

    @Override
    public void onShow() {
        end = false;
        win = false;
        pause = false;
        counter = 0;
        int level = Environment.get("level");
        if (level == 2) {
            try (Scanner sc = new Scanner(new File("level.txt"))) {
                remainingTime = sc.nextInt();
                nchRemainingTime = remainingTime;
                finalResult = sc.nextInt();
                numberOfBalls = sc.nextInt();
                radiusMax = sc.nextInt();
                radiusMin = sc.nextInt();
                speedMax = sc.nextDouble();
                speedMin = sc.nextDouble();
                circles = new Circle[numberOfBalls];
                for (int i = 0; i < circles.length; i++)
                    circles[i] = new Circle(radiusMin, radiusMax, speedMin, speedMax);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (level == 1) {
            counter = 0;
            remainingTime = 60000;
            nchRemainingTime = remainingTime;
            finalResult = 90;
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
        if ((end | pause) && click && new Rectangle(188, 263, 425, 75).contains(Mouse.x(), Mouse.y())) {
            pause = false;
            win = false;
            end = false;
            Game.show(MenuView.class);
        }
        if (!end && click && Mouse.x() > 740 && Mouse.y() < 60 && Mouse.x() < 790 && Mouse.y() > 10) pause = !pause;
        if (!pause | !end && remainingTime > 0)
            remainingTime = (int) (remainingTime - t); // высчитывание оставшегося времени

        if (remainingTime < 0 && counter < finalResult) {
            win = false;
            end = true;
        } else if (remainingTime < 0 && counter > finalResult) {
            win = true;
            end = true;
        }
        if (!pause && !end) {
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
        g.ctext(0, 0, 800, 100, "" + finalResult + "/" + counter);
        if (pause || end) {
            g.setFillColor(Color.WHITE);
            g.setTextStyle(1, 1, 20);
            g.ctext(0, 0, 800, 150, pause ? "PAUSE" : win ? "You win!" : "You lose!");
            g.putImage("exit-to-menu", 188, 263);
        }
        g.setColor(Color.WHITE);
        g.setTextStyle(1, 1, 20);
        g.ctext(0, 0, 800, 50, "" + remainingTime / 1000);
        g.setFillColor(Color.white);
        g.putImage("pause", 740, 10, 50);
    }
}