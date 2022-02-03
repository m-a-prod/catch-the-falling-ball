import com.wizylab.duck2d.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameView implements View {
    int counter = 0;
    int remainingTime = 60000;
    int finalResult = 40;
    int radiusMin = 5, radiusMax = 20;
    double speedMin = 0.1, speedMax = 0.3;
    int numberOfBalls = 150;
    //0-x 1-y 2-radius
    Circle[] circles;

    public static void main(String[] args) {
        Game.start(new GameView());
    }

    @Override
    public void onShow() {
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

    @Override
    public void onTimer(long t) {
        if (Keyboard.onKey(KeyEvent.VK_ESCAPE)) System.exit(0);
        boolean click = Mouse.onClick(MouseButton.LEFT);

        remainingTime = (int) (remainingTime - t); // высчитывание оставшегося времени
        if (remainingTime < 0 && counter < finalResult) {
            System.out.println("You lose!");
            System.out.println("Your result is " + counter);
            System.exit(0);
        } else if (remainingTime < 0 && counter > finalResult) {
            System.out.println("You win!");
            System.out.println("Your result is " + counter);
            System.exit(0);
        }

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

    @Override
    public void onDraw(Graph g) {
        for (Circle circle : circles) circle.draw(g);

        g.setColor(Color.WHITE);
        g.setTextStyle(1, 1, 20);
        g.ctext(0, 0, 800, 100, "" + counter);
        g.setColor(Color.WHITE);
        g.setTextStyle(1, 1, 20);
        g.ctext(0, 0, 800, 50, "" + remainingTime / 1000);
    }
}
