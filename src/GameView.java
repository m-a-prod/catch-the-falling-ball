import com.wizylab.duck2d.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameView implements View {

    int counter = 0;
    int remainingTime = 60000;
    int finalResult = 50;
    int radiusMax = 20;
    int radiusMin = 5;
    double speedMax = 0.3;
    double speedMin = 0.1;
    int numberOfBalls = 100;
    //0-x 1-y 2-radius
    double[][] circles;

    public static void main(String[] args) {
        Game.start(new GameView());
    }

    public void reset(int i) {
        circles[i][1] = -10;
        circles[i][0] = Math.random() * 800;
        circles[i][2] = radiusMin + Math.random() * radiusMax; //Радиус
        circles[i][3] = speedMin + Math.random() * speedMax; // скорость
        circles[i][4] = Math.random(); // цвет
    }

    @Override
    public void onShow() {
        try (Scanner sc = new Scanner(new File("level.txt"))) {
            remainingTime = sc.nextInt();
            finalResult = sc.nextInt();
            numberOfBalls = sc.nextInt();
            circles = new double[numberOfBalls][5];
            radiusMax = sc.nextInt();
            radiusMin = sc.nextInt();
            speedMax = sc.nextDouble();
            speedMin = sc.nextDouble();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < circles.length; i++) {   //здесь я не могу использовать
            circles[i][0] = Math.random() * 800; //X //void reset(i) по причине арабы.
            circles[i][1] = Math.random() * -620; //Y
            circles[i][2] = radiusMin + Math.random() * radiusMax; //Радиус
            circles[i][3] = speedMin + Math.random() * speedMax; //скорость
            circles[i][4] = Math.random(); //цвет
        }
    }

    @Override
    public void onTimer(long t) {
        if (Keyboard.onKey(KeyEvent.VK_ESCAPE)) System.exit(0);
        boolean click = Mouse.onClick(MouseButton.LEFT);
        remainingTime = (int) (remainingTime - t); //Высчитывание оставшегося всеремени
        if (remainingTime < 0 && counter < finalResult) {
            System.out.println("You lose!");
            System.out.println("Your result is " + counter);
            System.exit(0);
        } else if (remainingTime < 0 && counter > finalResult) {
            System.out.println("You win!");
            System.out.println("Your result is " + counter);
            System.exit(0);
        }
        for (int i = 0; i < circles.length; i++) {
            circles[i][1] = circles[i][1] + circles[i][3] * t;

            if (circles[i][1] >= 610) {
                reset(i);
            }

            if (click && circles[i][2] > Math.sqrt(Math.pow(Mouse.x() - circles[i][0], 2) + Math.pow(Mouse.y() - circles[i][1], 2))) {
                counter++;
                reset(i);
            }
        }
    }

    @Override
    public void onDraw(Graph g) {
        for (double[] circle : circles) {
            g.setFillColor(Color.getHSBColor((float) circle[4], 1F, 1F));
            g.fillCircle((int) circle[0], (int) circle[1], (int) circle[2]);
        }
        g.setColor(Color.WHITE);
        g.setTextStyle(1, 1, 20);
        g.ctext(0, 0, 800, 100, "" + counter);
        g.setColor(Color.WHITE);
        g.setTextStyle(1, 1, 20);
        g.ctext(0, 0, 800, 50, "" + remainingTime / 1000);

    }
}