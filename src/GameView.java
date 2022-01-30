import com.wizylab.duck2d.Game;
import com.wizylab.duck2d.Graph;
import com.wizylab.duck2d.Keyboard;
import com.wizylab.duck2d.View;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameView implements View {
    double[][] circles = new double[150][5];

    //0-x 1-y 2-radius
    public static void main(String[] args) {
        Game.start(new GameView());
    }

    @Override
    public void onShow() {
        for (int i = 0; i < circles.length; i++) {
            circles[i][0] = Math.random() * 800; //X
            circles[i][1] = Math.random() * -600; //Y
            circles[i][2] = Math.random() * 20; //Радиус
            circles[i][3] = 0.1 + Math.random() * 0.4;
            circles[i][4] = Math.random();
        }
    }

    @Override
    public void onTimer(long l) {
        if (Keyboard.onKey(KeyEvent.VK_ESCAPE)) System.exit(0);

        for (int i = 0; i < circles.length; i++) {
            circles[i][1] = circles[i][1] + circles[i][3] * l;
            if (circles[i][1] >= 610) {
                circles[i][1] = -10;
                circles[i][0] = Math.random() * 800;
                circles[i][2] = Math.random() * 20; //Радиус
                circles[i][3] = 0.1 + Math.random() * 0.4;
                circles[i][4] = Math.random();
            }
        }

    }

    @Override
    public void onDraw(Graph g) {
        for (double[] circle : circles) {
            g.setFillColor(Color.getHSBColor((float) circle[4], 1F, 1F));
            g.fillCircle((int) circle[0], (int) circle[1], (int) circle[2]);
        }
    }
}
