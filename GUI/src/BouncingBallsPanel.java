import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;

public class BouncingBallsPanel extends JPanel {

    AnimationThread tr = new AnimationThread();
    static class Ball{
        int x;
        int y;
        double vx;
        double vy;
        int d;
        //int speed=3;
        Color color;
        Ball()
        {
            Random generator = new Random();
            d=generator.nextInt(20)+10;
            x=generator.nextInt(700-d);
            y=generator.nextInt(700-d);
            vx=generator.nextInt(7)+7;
            vy=generator.nextInt(7)+7;

            color=new Color(generator.nextFloat(),generator.nextFloat(),generator.nextFloat());

        }

        public void draw(Graphics2D g2d){

            AffineTransform saveAt = g2d.getTransform();
            this.render(g2d);
            g2d.setTransform(saveAt);

        }

        private void render(Graphics2D g2d) {
            g2d.setColor(color);
            g2d.fillOval(x,y,d,d);
        }
        boolean contains(double x, double y) {
            if (x > this.x && x < this.x+this.d && y > this.y && y < this.y+this.d)
                return true;
            return false;
        }
        public void resolveCollision(Ball ball)//wymaga poprawy
        {
            float deltax=x-ball.x;
            float deltay=y-ball.y;
            // get the mtd
            //Vector2d delta = (position.subtract(ball.position));
            float r= d/2 + ball.d/2;
            //float r = getRadius() + ball.getRadius();
            float dist2 = 0.0f;
            //result = this.getX() * v2.getX() + this.getY() * v2.getY();
            dist2=deltax*deltax+deltay*deltay;
            //float dist2 = delta.dot(delta);


            if (dist2 > r*r) return; // they aren't colliding

//return (float)Math.sqrt(getX()*getX() + getY()*getY());
            //float dd = delta.getLength();
            float dd=(float)Math.sqrt(Math.pow(deltax,2)+Math.pow(deltay,2));
            //Vector2d mtd;
            float mtd_x;
            float mtd_y;
            if (dd != 0.0f)
            {
                mtd_x=deltax*(((d/2 + ball.d/2)-dd)/dd);
                mtd_y=deltay*(((d/2 + ball.d/2)-dd)/dd);
                //mtd = delta.multiply(((d/2 + ball.d/2)-dd)/dd); // minimum translation distance to push balls apart after intersecting

            }
            else // Special case. Balls are exactly on top of eachother.  Don't want to divide by zero.
            {
                dd = ball.d/2 + d/2 - 1.0f;
                //delta = new Vector2d(ball.getRadius() + getRadius(), 0.0f);
                deltax = ball.d/2;
                deltay=0.0f;
                mtd_x=deltax*(((d/2 + ball.d/2)-dd)/dd);
                mtd_y=deltay*(((d/2 + ball.d/2)-dd)/dd);
                //mtd = delta.multiply(((d/2 + ball.d/2)-dd)/dd);
            }

            // resolve intersection
            float im1 = 1 / d; // inverse mass quantities
            float im2 = 1 / ball.d;

            // push-pull them apart
            //position = position.add(mtd.multiply(im1 / (im1 + im2)));
            x+=mtd_x*(im1 / (im1 + im2));
            y+=mtd_y*(im1 / (im1 + im2));
            //ball.position = ball.position.subtract(mtd.multiply(im2 / (im1 + im2)));
            ball.x-=mtd_x*(im1 / (im1 + im2));
            ball.y-=mtd_y*(im1 / (im1 + im2));


            // impact speed
            //Vector2d v = (this.velocity.subtract(ball.velocity));
            vx-=ball.vx;
            vy-=ball.vy;
            //float vn = v.dot(mtd.normalize());
            float mtdl=(float)Math.sqrt(Math.pow(mtd_x,2)+Math.pow(mtd_y,2));
            float vn = 0.0f;
            if(mtdl!=0.0f)
            {

                vn= (float) ((vx*mtd_x/mtdl)+(vy*mtd_y/mtdl));
            }
            else
            {
                vn= (float) ((vx*0.0f)+(vy*0.0f));
            }


            // sphere intersecting but moving away from each other already
            if (vn > 0.0f) return;

            // collision impulse
            //float i = (-(1.0f + Constants.restitution) * vn) / (im1 + im2);
            float i=1;
            //Vector2d impulse = mtd.multiply(i);
            float impulse_x=mtd_x*i;
            float impulse_y=mtd_y*i;
            // change in momentum
            //this.velocity = this.velocity.add(impulse.multiply(im1));
            vx+=impulse_x*im1;
            vy+=impulse_y*im1;
            //ball.velocity = ball.velocity.subtract(impulse.multiply(im2));
            ball.vx+=impulse_x*im2;
            ball.vy+=impulse_y*im2;


        }
    }

    List<Ball> balls = new ArrayList<>();

    class AnimationThread extends Thread{

        public void run(){
            for(;;){
                for (Ball pom :balls
                     ) {
                    pom.x+=pom.vx;
                    pom.y+=pom.vy;
                    detectCollision(pom);

                }
                 repaint();
                try {
                    tr.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    void detectCollision(Ball pom)
    {
        Dimension d=getSize();
        if(pom.x<=0){
            pom.x=pom.d/2;
            pom.vx*=-1;}
        if(pom.x>=d.width-pom.d){
            pom.x=d.width-pom.d;
            pom.vx*=-1;}
       if(pom.y<=0){
            pom.y=pom.d/2;
            pom.vy*=-1;}
        if(pom.y>=d.height-pom.d || pom.y<=0){
            pom.y= d.height-pom.d;
            pom.vy*=-1;}
        balls.forEach(ball -> {collide(ball,pom);});
    }

    BouncingBallsPanel(){

        setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
        //tr.start();
    }
   public void paintComponent(Graphics g)
   {
       super.paintComponent(g);
       balls.forEach(ball -> ball.draw((Graphics2D) g));
   }

    void onStart(){
        tr = new AnimationThread();
        tr.start();
        System.out.println("Start or resume animation thread");
    }

    void onStop(){
        tr.suspend();
        System.out.println("Suspend animation thread");
    }

    void onPlus(){
        balls.add(new Ball());
        System.out.println("Add a ball");
    }

    void onMinus(){
        if(!balls.isEmpty())
        {
            balls.remove(0);
        }
        System.out.println("Remove a ball");
    }

    void collide(Ball a, Ball b)
    {
        if(a==b)
            return;
        if(b.contains(a.x+a.d, a.y) || b.contains(a.x, a.y+a.d) || b.contains(a.x+a.d, a.y) || b.contains(a.x, a.y)
                || (a.contains(b.x+b.d, b.y) || a.contains(b.x, b.y+b.d) || a.contains(b.x+b.d, b.y+b.d) || a.contains(b.x, b.y)))
        {
            //System.out.print(a.vx);
            a.vx*=-1;
            a.vy*=-1;
            b.vx*=-1;
            b.vy*=-1;
        }

        /*Vector collision = a.position() - b.position();
        double distance = collision.length();
        if (distance == 0.0) {              // hack to avoid div by zero
            collision = Vector(1.0, 0.0);
            distance = 1.0;
        }
        if (distance > 1.0)
            return;

        // Get the components of the velocity vectors which are parallel to the collision.
        // The perpendicular component remains the same for both fish
        collision = collision / distance;
        double aci = a.velocity().dot(collision);
        double bci = b.velocity().dot(collision);

        // Solve for the new velocities using the 1-dimensional elastic collision equations.
        // Turns out it's really simple when the masses are the same.
        double acf = bci;
        double bcf = aci;

        // Replace the collision velocity components with the new ones
        a.velocity() += (acf - aci) * collision;
        b.velocity() += (bcf - bci) * collision;*/
    }
}