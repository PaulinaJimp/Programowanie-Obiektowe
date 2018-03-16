import java.awt.*;
import java.util.List;
import java.util.Random;

public class Light extends Star {

    Light(int x, int y, double scale, Color innerColor, Color outerColor, double innerRadius, double outerRadius, int numRays, double startAngleRad) {
        super(x, y, scale, innerColor, outerColor, innerRadius, outerRadius, numRays, startAngleRad);
    }

    Light() {
        super(0, 0, 1, Color.YELLOW, Color.WHITE, 0, 0, 4, 0);
    }

    void lights(List<XmasShape> shapes) {
        Random generator = new Random();
        double x, y;
        int p1, p2;
        int[] u = new int[]{500 - 155, -90 + 600};
        int[] v = new int[]{845 - 155, 0};

        for (int i = 0; i < 50; i++) {
            x = generator.nextFloat();
            y = generator.nextFloat();
            if (x + y < 1) {
                x = 1 - x;
                y = 1 - y;
            }
            p1 = (int) ((u[0] * x + v[0] * y - 500 + 286) * 3.33);
            p2 = (int) ((u[1] * x + v[1] * y + 90) * 3.33);
            shapes.add(new Light(p1, p2, 0.3, Color.WHITE, Color.YELLOW, 10, 40, 4, 0));
        }
    }
}
