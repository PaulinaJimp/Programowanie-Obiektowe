import java.awt.*;
import java.util.*;
import java.util.List;


public class Bubble implements XmasShape {
    int x;
    int y;
    double scale;
    Color lineColor;
    Color fillColor;

    Bubble() {
        x = 0;
        y = 0;
        scale = 1;
        lineColor = new Color(0, 0, 0, 255);
        fillColor = new Color(255, 255, 255, 255);
    }

    Bubble(int x, int y, double scale, Color lineColor, Color fillColor) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.lineColor = lineColor;
        this.fillColor = fillColor;
    }

    Bubble(Color lineColor, Color fillColor) {
        this(0, 0, 1, lineColor, fillColor);
    }

    Bubble(int x, int y, double scale) {
        this(x, y, scale, new Color(0, 0, 0, 255), new Color(255, 255, 255, 255));
    }

    @Override
    public void render(Graphics2D g2d) {

        g2d.setColor(fillColor);
        g2d.fillOval(0, 0, 30, 30);

        g2d.setColor(lineColor);
        g2d.drawOval(0, 0, 30, 30);
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x, y);
        g2d.scale(scale, scale);
    }

    void bubbles(List<XmasShape> shapes) {

        Random generator = new Random();

//        shapes.add(new Bubble( 330,312,1,new Color(0x320909),new Color(0xCE241F)));
//        shapes.add(new Bubble( 430,212,1,new Color(0x320909),new Color(0xCE241F)));
//        shapes.add(new Bubble( 500,350,1,new Color(0x320909),new Color(0xCE241F)));
//        for (int i = 0; i < 6; i++) {
//            Color col=new Color(generator.nextFloat(),generator.nextFloat(),generator.nextFloat());
//            shapes.add(new Bubble((int) (720-30*i*(1.5 - i * 0.08)) + generator.nextInt(20*(7-i))-10*(7-i), (int) (560 - i * 70*(1.5 - i * 0.08)) - generator.nextInt(10),1,col,col.brighter().brighter()));
//           col=new Color(generator.nextFloat(),generator.nextFloat()/2f,generator.nextFloat()/2f);
//            shapes.add(new Bubble((int) (250+30*i*(1.5 - i * 0.08))+ generator.nextInt(20*(7-i))-10*(7-i), (int) (560 - i * 70*(1.5 - i * 0.08)) - generator.nextInt(10),1,col,col.brighter().brighter()));
//            col=new Color(generator.nextFloat(),generator.nextFloat()/2f,generator.nextFloat()/2f);
//            shapes.add(new Bubble((int) (400+10*i*(1.5 - i * 0.08))+ generator.nextInt(10*(7-i))-5*(7-i), (int) (590 - i * 70*(1.5 - i * 0.08)) - generator.nextInt(10),1,col,col.brighter().brighter()));
//            col=new Color(generator.nextFloat(),generator.nextFloat()/2f,generator.nextFloat()/2f);
//            shapes.add(new Bubble((int) (570-10*i*(1.5 - i * 0.08))+ generator.nextInt(10*(7-i))-5*(7-i), (int) (590 - i * 70*(1.5 - i * 0.08)) - generator.nextInt(10),1,col,col.brighter().brighter()));
        double tempX, tempY;
        int pointOnTheTreeX, pointOnTheTreeY, index;
        int[] u = new int[]{500 - 155, -90 + 600};
        int[] v = new int[]{845 - 155, 0};
        List<Integer> pointsX = new ArrayList<>();
        List<Integer> pointsY = new ArrayList<>();
        boolean flag;
        for (int i = 0; i < 50; i++) {
            flag = false;
            tempX = generator.nextFloat();
            tempY = generator.nextFloat();

            if (tempX + tempY < 1) {
                tempX = 1 - tempX;
                tempY = 1 - tempY;
            }
            pointOnTheTreeX = (int) (u[0] * tempX + v[0] * tempY) - 500 + 290;
            pointOnTheTreeY = (int) (u[1] * tempX + v[1] * tempY) + 80;

            for (int tempObject : pointsX
                    ) {
                if (tempObject >= pointOnTheTreeX - 35 && tempObject <= pointOnTheTreeX + 35) {
                    index = pointsX.indexOf(tempObject);
                    int tempYValue = pointsY.get(index);
                    if (tempYValue >= pointOnTheTreeY - 35 && tempYValue <= pointOnTheTreeY + 35) {
                        flag = true;
                        break;
                    }
                }
            }
            if (flag)
                continue;
            pointsX.add(pointOnTheTreeX);
            pointsY.add(pointOnTheTreeY);
            Color col = new Color(generator.nextFloat(), generator.nextFloat(), generator.nextFloat());
            shapes.add(new Bubble(pointOnTheTreeX, pointOnTheTreeY, 1, col, col.brighter()));
        }
    }
}