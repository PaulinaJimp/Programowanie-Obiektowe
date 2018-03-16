import java.util.ArrayList;
import java.util.List;

public class AdminUnit {
    private String name;
    private int adminLevel;
    private double population;
    private double area;
    private double density;
    private AdminUnit parent;
    BoundingBox bbox = new BoundingBox();
    private List<AdminUnit> children;
    List<AdminUnit> neighbors = new ArrayList<>();


    AdminUnit(String name1, int adminLevel1, double population1, double area1, double density1) {
        this.setName(name1);
        this.setAdminLevel(adminLevel1);
        this.setPopulation(population1);
        this.setArea(area1);
        this.setDensity(density1);
    }

    AdminUnit(String name1, int adminLevel1, double area1) {
        this(name1, adminLevel1, 0, area1, 0);
    }

    AdminUnit(String name) {
        this(name, 0, 0, 0, 0);
    }

    AdminUnit() {
        this("", 0, 0, 0, 0);
    }

    public String toString() {
        return this.getName() + ", AdminLevel: " + this.getAdminLevel() + ", Population: " + this.getPopulation() + ", Area: " + this.getArea() + ", Density: " + this.getDensity();
    }

    public String getName() {
        return name;
    }

    void fixMissingValues() {
        if (parent != null) {
            if (parent.density != 0) {
                if (this.getDensity() == 0)
                    this.setDensity(this.parent.getDensity());
                if (this.getPopulation() == 0)
                    this.setPopulation(this.getArea() * this.getDensity());
            } else {
                this.parent.fixMissingValues();
            }
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(int adminLevel) {
        this.adminLevel = adminLevel;
    }

    public double getPopulation() {
        return population;
    }

    public void setPopulation(double population) {
        this.population = population;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public AdminUnit getParent() {
        return parent;
    }

    public void setParent(AdminUnit parent) {
        this.parent = parent;
    }

    public List<AdminUnit> getChildren() {
        return children;
    }

    public void setChildren(List<AdminUnit> children) {
        this.children = children;
    }
}
