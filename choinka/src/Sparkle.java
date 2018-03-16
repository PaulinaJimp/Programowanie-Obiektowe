import java.awt.*;
import java.util.Random;

public class Sparkle implements XmasShape {
    int x;
    int y;
    Color color;

    Sparkle() {
        x = 0;
        y = 0;
        color = Color.yellow;
    }

    Sparkle(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    Sparkle(int x, int y) {
        this(x, y, Color.yellow);
    }

    @Override
    public void transform(Graphics2D g2d) {

    }

    @Override
    public void render(Graphics2D g2d) {
        Random generator = new Random();
        int x, y, diameter;
        double tempX, tempY;
        int[] u = new int[]{500 - 155, -90 + 600};
        int[] v = new int[]{845 - 155, 0};
        g2d.setColor(color);
        for (int i = 0; i < 100; i++) {
            tempX = generator.nextFloat();
            tempY = generator.nextFloat();

            if (tempX + tempY < 1) {
                tempX = 1 - tempX;
                tempY = 1 - tempY;
            }
            x = (int) (u[0] * tempX + v[0] * tempY) - 500 + 290;
            y = (int) (u[1] * tempX + v[1] * tempY) + 80;

            diameter = generator.nextInt(40);
            g2d.drawOval(x, y, diameter, diameter);
        }
    }
}