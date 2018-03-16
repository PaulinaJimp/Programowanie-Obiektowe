import java.util.Locale;

import static java.lang.Double.isNaN;
import static java.lang.Math.*;

public class BoundingBox {
    private double xmin = Double.NaN;
    private double ymin = Double.NaN;
    private double xmax = Double.NaN;
    private double ymax = Double.NaN;

    public String toString() {
        return this.getXmin() + " " + this.getXmax() + " " + this.getYmin() + " " + this.getYmax();
    }

    /**
     * Powiększa BB tak, aby zawierał punkt (x,y)
     *
     * @param x - współrzędna x
     * @param y - współrzędna y
     */

    void addPoint(double x, double y) {
        if (getXmin() > x)
            setXmin(x);
        if (getXmax() < x)
            setXmax(x);
        if (getYmin() > y)
            setYmin(y);
        if (getYmax() < y)
            setYmax(y);

    }

    /**
     * Sprawdza, czy BB zawiera punkt (x,y)
     *
     * @param x
     * @param y
     * @return
     */
    boolean contains(double x, double y) {
        if (x > getXmin() && x < getXmax() && y > getYmin() && y < getYmax())
            return true;
        return false;
    }

    /**
     * Sprawdza czy dany BB zawiera bb
     *
     * @param bb
     * @return
     */
    boolean contains(BoundingBox bb) {
        return contains(bb.getXmax(), bb.getYmax()) && contains(bb.getXmin(), bb.getYmin());
    }

    /**
     * Sprawdza, czy dany BB przecina się z bb
     *
     * @param bb
     * @return
     */
    boolean intersects(BoundingBox bb) {
        return (contains(bb.getXmax(), bb.getYmax()) || contains(bb.getXmin(), bb.getYmin()) || contains(bb.getXmax(), bb.getYmin()) || contains(bb.getXmin(), bb.getYmax()))
                || (bb.contains(getXmax(), getYmax()) || bb.contains(getXmin(), getYmin()) || bb.contains(getXmax(), getYmin()) || bb.contains(getXmin(), getYmax()));
    }

    /**
     * Powiększa rozmiary tak, aby zawierał bb oraz poprzednią wersję this
     *
     * @param bb
     * @return
     */
    BoundingBox add(BoundingBox bb) {

        addPoint(bb.getXmax(), bb.getYmax());
        addPoint(bb.getXmin(), bb.getYmin());
        addPoint(bb.getXmax(), bb.getYmin());
        addPoint(bb.getXmin(), bb.getYmax());
        return this;
    }

    /**
     * Sprawdza czy BB jest pusty
     *
     * @return
     */
    boolean isEmpty() {
        return Double.isNaN(getYmin()) || isNaN(getYmax()) || isNaN(getXmin()) || isNaN(getXmax());
    }

    /**
     * Oblicza i zwraca współrzędną x środka
     *
     * @return if !isEmpty() współrzędna x środka else wyrzuca wyjątek
     * (sam dobierz typ)
     */
    double getCenterX() {
        if (!isEmpty())
            return (getXmax() + getXmin()) / 2;
        else throw new NullPointerException("Bounding Box is empty");
    }

    /**
     * Oblicza i zwraca współrzędną y środka
     *
     * @return if !isEmpty() współrzędna y środka else wyrzuca wyjątek
     * (sam dobierz typ)
     */
    double getCenterY() {
        if (!isEmpty())
            return (getYmax() + getYmin()) / 2;
        else throw new NullPointerException("Bounding Box is empty");
    }

    /**
     * Oblicza odległość pomiędzy środkami this bounding box oraz bbx
     *
     * @param bbx prostokąt, do którego liczona jest odległość
     * @return if !isEmpty odległość, else wyrzuca wyjątek lub zwraca maksymalną możliwą wartość double
     * Ze względu na to, że są to współrzędne geograficzne, zamiast odległosci euklidesowej możesz użyć wzoru haversine
     * (ang. haversine formula)
     */
    double distanceTo(BoundingBox bbx) {
        if (!isEmpty() && !bbx.isEmpty())
            return Haversine.distance(getCenterX(), getCenterY(), bbx.getCenterX(), bbx.getCenterY());
        else throw new NullPointerException("Bounding Box is empty");
    }

    String getWKT() {
        return "LINESTRING(" + getXmin() + " " + getYmin() + ", " + getXmin() + " " + getYmax() + ", " + getXmax() + " " + getYmax() + ", " + getXmax() + " " + getYmin() + "," + getXmin() + " " + getYmin() + ")\n";
    }

    public double getXmin() {
        return xmin;
    }

    public void setXmin(double xmin) {
        this.xmin = xmin;
    }

    public double getYmin() {
        return ymin;
    }

    public void setYmin(double ymin) {
        this.ymin = ymin;
    }

    public double getXmax() {
        return xmax;
    }

    public void setXmax(double xmax) {
        this.xmax = xmax;
    }

    public double getYmax() {
        return ymax;
    }

    public void setYmax(double ymax) {
        this.ymax = ymax;
    }
}