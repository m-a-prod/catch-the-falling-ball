import com.wizylab.duck2d.Graph;

import java.awt.*;

public class Circle {
    double x, y, speed;
    float color;
    int radius;

    public Circle(double radiusMin, double radiusMax, double speedMin, double speedMax) {
        reset(radiusMin, radiusMax, speedMin, speedMax);
        y = Math.random() * -620;
    }

    void move(long t) {
        y += speed * t;
    }

    boolean contains(int x, int y) {
        return radius * radius > Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2);
    }

    public void reset(double radiusMin, double radiusMax, double speedMin, double speedMax) {
        x = Math.random() * 800;
        y = -radiusMax - 10;
        radius = (int) (radiusMin + Math.random() * radiusMax);
        speed = speedMin + Math.random() * speedMax;
        color = (float) Math.random();
    }

    public void draw(Graph g) {
        g.setFillColor(Color.getHSBColor(color, 1F, 1F));
        g.fillCircle((int) x, (int) y, radius);
    }
}