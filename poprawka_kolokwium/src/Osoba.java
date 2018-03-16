public class Osoba {
    private String name;
    private int year;
    private int number;
    private String  sex;

    public double getDiffrence_in_populatity() {
        return diffrence_in_populatity;
    }

    public void setDiffrence_in_populatity(double diffrence_in_populatity) {
        this.diffrence_in_populatity = diffrence_in_populatity;
    }

    private double diffrence_in_populatity;


    Osoba(String name1, int year1, int number1, String sex1) {
        this.setName(name1);
        this.setYear(year1);
        this.setNumber(number1);
        this.setSex(sex1);
        this.diffrence_in_populatity =0;
    }

    Osoba() {
        this("", 0, 0, "");
    }

    public String toString() {
        return this.getName() + ", rok: " + this.getYear() + ", ilość: " + this.getNumber() + ", płeć: " + this.getSex();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
