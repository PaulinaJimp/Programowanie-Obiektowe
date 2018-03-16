import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class DrawPanel extends JPanel {
    List<XmasShape> shapes = new ArrayList<>();

    DrawPanel() {
        setBackground(new Color(89, 104, 142));
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Branch branch = new Branch();
        Bubble bubble = new Bubble();
        Star star = new Star();
        Light light = new Light();
        Sparkle spark = new Sparkle();

        branch.branches(shapes);
        bubble.bubbles(shapes);
        light.lights(shapes);
        star.star(shapes);
        shapes.add(spark);


        for (XmasShape s : shapes) {
            s.draw((Graphics2D) g);
        }
    }
}
