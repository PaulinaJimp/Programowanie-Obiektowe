import java.awt.*;
import java.awt.geom.Path2D;
import java.util.List;

public class Star implements XmasShape {
    int x;
    int y;
    double scale;
    Color innerColor;
    Color outerColor;
    double innerRadius;
    double outerRadius;
    int numRays;
    double startAngleRad;

    Star() {
        x = 0;
        y = 0;
        scale = 1;
        innerColor = Color.RED;
        outerColor = Color.YELLOW;
        innerRadius = 0;
        outerRadius = 0;
        numRays = 4;
        startAngleRad =0;
    }

    Star(int x, int y, double scale, Color innerColor, Color outerColor, double innerRadius , double outerRadius, int numRays, double startAngleRad) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.innerColor = innerColor;
        this.outerColor = outerColor;
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
        this.numRays=numRays;
        this.startAngleRad=startAngleRad;
    }

    Star(int x, int y, double scale) {
        this(x, y, scale, Color.RED, Color.YELLOW,0,0,4,0);
    }
    Star(int x, int y, double scale,double innerRadius,double outerRadius,int numRays,double startAngleRad) {
        this(x, y, scale, Color.RED, Color.YELLOW,innerRadius,outerRadius,numRays,startAngleRad);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setPaint(new RadialGradientPaint(
                new Point(x, y), (float) outerRadius, new float[]{0, 1},
                new Color[]{innerColor, outerColor}));
        g2d.fill(createStar(x, y, innerRadius, outerRadius, numRays, startAngleRad));
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.scale(scale, scale);
    }

    void star(List<XmasShape> shapes) {
        shapes.add(new Star(500, 90, 1,20,60,8,0));
    }
    private static Shape createStar(double centerX, double centerY,
                                    double innerRadius, double outerRadius, int numRays,
                                    double startAngleRad) {
        Path2D path = new Path2D.Double();
        double deltaAngleRad = Math.PI / numRays;
        for (int i = 0; i < numRays * 2; i++) {
            double angleRad = startAngleRad + i * deltaAngleRad;
            double ca = Math.cos(angleRad);
            double sa = Math.sin(angleRad);
            double relX = ca;
            double relY = sa;
            if ((i & 1) == 0) {
                relX *= outerRadius;
                relY *= outerRadius;
            } else {
                relX *= innerRadius;
                relY *= innerRadius;
            }
            if (i == 0) {
                path.moveTo(centerX + relX, centerY + relY);
            } else {
                path.lineTo(centerX + relX, centerY + relY);
            }
        }
        path.closePath();
        return path;
    }
}
