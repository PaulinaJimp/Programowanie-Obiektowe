import java.awt.*;
import java.util.List;

import static java.lang.Math.pow;

public class Branch implements XmasShape {
    int x;
    int y;
    double scale_x;
    double scale_y;
    Color lineColor;
    Color fillColor;

    Branch() {
        x = 0;
        y = 0;
        scale_x = 0.7;
        scale_y = 0.5;
        lineColor = new Color(0, 95, 0);
        fillColor = new Color(121, 255, 105, 255);
    }

    Branch(int x, int y, double scale_x, double scale_y, Color lineColor, Color fillColor) {
        this.x = x;
        this.y = y;
        this.scale_x = scale_x;
        this.scale_y = scale_y;
        this.lineColor = lineColor;
        this.fillColor = fillColor;
    }

    Branch(int x, int y, double scale_x, double scale_y) {
        this(x, y, scale_x, scale_y, new Color(0, 95, 0), new Color(121, 255, 105, 255));
    }

    @Override
    public void render(Graphics2D g2d) {
        GradientPaint grad = new GradientPaint(0, 0, fillColor, 0, 100, lineColor);
        g2d.setPaint(grad);
        int x[] = {286, 286, 233, 180, 148, 119, 69, 45, 0};
        int y[] = {0, 127, 109, 118, 99, 105, 86, 90, 76};
        g2d.fillPolygon(x, y, x.length);
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x, y);
        g2d.scale(scale_x, scale_y);
    }

    void branches(List<XmasShape> shapes) {
        for (int i = 0; i < 7; i++) {
            shapes.add(new Branch((int) (pow(0, i) + 500 - 286 * (1.2 - i * 0.15)), 450 - i * 60, (1.2 - i * 0.15), 1.5 - i * 0.08));
            shapes.add(new Branch((int) (500 + 286 * (1.2 - i * 0.15)), 450 - i * 60, -1 * (1.2 - i * 0.15), 1.5 - i * 0.08));
        }
    }
}
