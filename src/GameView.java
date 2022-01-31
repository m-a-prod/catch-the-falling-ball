import com.wizylab.duck2d.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameView implements View {
    //0-x 1-y 2-radius
    double[][] circles = new double[100][5];
    int counter = 0;
    int remainingTime = 60000;

    /*
    Просто заметки в коде, не обращайте внимание, они упрощают жизнь
    Mouse.x();
    Mouse.y();
    Mouse.hasClick(MouseButton.LEFT);
    Mouse.onClick(MouseButton.RIGHT);
     */
    public static void main(String[] args) {
        Game.start(new GameView());
    }

    @Override
    public void onShow() {
        for (int i = 0; i < circles.length; i++) {
            circles[i][0] = Math.random() * 800; //X
            circles[i][1] = Math.random() * -620; //Y
            circles[i][2] = 5 + Math.random() * 20; //Радиус
            circles[i][3] = 0.1 + Math.random() * 0.3; //скорость
            circles[i][4] = Math.random(); //цвет
        }
    }

    @Override
    public void onTimer(long t) {
        if (Keyboard.onKey(KeyEvent.VK_ESCAPE)) System.exit(0);
        boolean click = Mouse.onClick(MouseButton.LEFT);
        remainingTime = (int) (remainingTime - t); //Высчитывание оставшегося всеремени
        if (remainingTime < 0 && counter < 30) {
            System.out.println("You lose!");
            System.exit(0);
        } else if (remainingTime < 0 && counter > 30) {
            System.out.println("You win!");
            System.exit(0);
        }
        for (int i = 0; i < circles.length; i++) {
            circles[i][1] = circles[i][1] + circles[i][3] * t;

            if (circles[i][1] >= 610) {
                circles[i][1] = -10;
                circles[i][0] = Math.random() * 800;
                circles[i][2] = 5 + Math.random() * 20; //Радиус
                circles[i][3] = 0.1 + Math.random() * 0.3; // скорость
                circles[i][4] = Math.random(); // цвет
            }

            if (click && circles[i][2] > Math.sqrt(Math.pow(Mouse.x() - circles[i][0], 2) + Math.pow(Mouse.y() - circles[i][1], 2))) {
                counter++;
                circles[i][1] = -10;
                circles[i][0] = Math.random() * 800;
                circles[i][2] = 5 + Math.random() * 20; //Радиус
                circles[i][3] = 0.1 + Math.random() * 0.4; // скорость
                circles[i][4] = Math.random(); // цвет
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