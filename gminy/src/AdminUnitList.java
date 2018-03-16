import java.io.PrintStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.System.out;

public class AdminUnitList {
    List<AdminUnit> units = new ArrayList<>();
    Map<Long, AdminUnit> id = new HashMap<>();

    public AdminUnitList(Stream<AdminUnit> adminUnitStream) {
        units = adminUnitStream.collect(Collectors.toList());
    }

    public AdminUnitList() {
        //
    }


    void list(PrintStream out) {
        for (AdminUnit a : units) {
            out.print(a.toString() + "\n" + a.bbox.toString() + "\n");
        }
    }

    void list(PrintStream out, int offset, int limit) {
        for (int i = 0; i < limit; i++)
            out.print(units.get(i + offset).toString() + "\n" + units.get(i).bbox.toString() + "\n");
    }

    /**
     * Zwraca nową listę zawierającą te obiekty AdminUnit, których nazwa pasuje do wzorca
     *
     * @param pattern - wzorzec dla nazwy
     * @param regex   - jeśli regex=true, użyj finkcji String matches(); jeśli false użyj funkcji contains()
     * @return podzbiór elementów, których nazwy spełniają kryterium wyboru
     */
    AdminUnitList selectByName(String pattern, boolean regex) {
        AdminUnitList ret = new AdminUnitList();
        if (regex) {
            for (AdminUnit a : units) {
                if (a.getName().matches(pattern))
                    ret.units.add(a);
            }

        } else {
            for (AdminUnit a : units) {
                if (a.getName().contains(pattern))
                    ret.units.add(a);
            }
        }
        return ret;
    }

    /**
     * Zwraca listę jednostek sąsiadujących z jendostką unit na tym samym poziomie hierarchii admin_level.
     * Czyli sąsiadami wojweództw są województwa, powiatów - powiaty, gmin - gminy, miejscowości - inne miejscowości
     *
     * @param unit        - jednostka, której sąsiedzi mają być wyznaczeni
     * @param maxdistance - parametr stosowany wyłącznie dla miejscowości, maksymalny promień odległości od środka unit,
     *                    w którym mają sie znaleźć punkty środkowe BoundingBox sąsiadów
     * @return lista wypełniona sąsiadami
     */
    AdminUnitList getNeighbors(AdminUnit unit, double maxdistance) {

        AdminUnitList Neighbors_list = new AdminUnitList();
        for (AdminUnit a : units) {
            if (unit.getAdminLevel() >= 8 && a.getAdminLevel() >= 8 && a != unit && !a.bbox.isEmpty()) {
                if (unit.bbox.distanceTo(a.bbox) <= maxdistance || unit.bbox.intersects(a.bbox))
                    Neighbors_list.units.add(a);
            } else {
                if (a.getAdminLevel() == unit.getAdminLevel() && a != unit && !a.bbox.isEmpty()) {
                    if (unit.bbox.intersects(a.bbox))
                        Neighbors_list.units.add(a);
                }
            }

        }
        return Neighbors_list;
    }

    void tree_search(AdminUnit unit, double maxdistance, AdminUnit target) {
        if (unit.getParent() == null)
            return;

        tree_search(unit.getParent(), maxdistance, target);
        for (AdminUnit pom : unit.getParent().getChildren()) {
            if (pom.getAdminLevel() >= 8 && target.getAdminLevel() >= 8 && pom != target && !pom.bbox.isEmpty()) {
                if (target.bbox.distanceTo(pom.bbox) <= maxdistance || target.bbox.intersects(pom.bbox))
                    target.neighbors.add(pom);
            } else {
                if (pom.getAdminLevel() == target.getAdminLevel() && pom != target && !pom.bbox.isEmpty()) {
                    if (target.bbox.intersects(pom.bbox))
                        target.neighbors.add(pom);
                }
            }
        }


    }

    void read() throws Exception {
        Map<AdminUnit, Long> parentid = new HashMap<>();
        Map<Long, List<AdminUnit>> parentid2child = new HashMap<>();
        CSVReader reader = new CSVReader("admin-units.csv", ",", true);
        AdminUnit kraj = new AdminUnit();
        kraj.setName("Polska");
        units.add(kraj);
        while (reader.next()) {
            AdminUnit element = new AdminUnit();
            if (!reader.isMissing("name"))
                element.setName(reader.get("name"));
            if (!reader.isMissing("admin_level")) {
                element.setAdminLevel(reader.getInt("admin_level"));
                if (reader.getInt("admin_level") == 4)
                    element.setParent(kraj);
            }
            if (!reader.isMissing("population"))
                element.setPopulation(reader.getDouble("population"));
            if (!reader.isMissing("density")) {
                element.setDensity(reader.getDouble("density"));
            }

            if (!reader.isMissing("area"))
                element.setArea(reader.getDouble("area"));

            if (!reader.isMissing("id")) {
                Long pom = reader.getLong("id");
                id.put(pom, element);
                parentid2child.put(pom, new ArrayList());
                element.setChildren(parentid2child.get(pom));
            }
            if (!reader.isMissing("parent")) {
                Long pom = reader.getLong("parent");
                element.setParent(id.get(pom));
                parentid.put(element, pom);

                if (id.get(pom) != null) {//&& !parentid2child.get(pom).contains(element)) {
                    List<AdminUnit> a = parentid2child.get(pom);
                    a.add(element);
                    parentid2child.put(pom, a);
                }
            } else element.setParent(null);

            if (!reader.isMissing("x1"))
                element.bbox.setXmin(reader.getDouble("x1"));
            if (!reader.isMissing("y1"))
                element.bbox.setYmin(reader.getDouble("y1"));
            if (!reader.isMissing("x3"))
                element.bbox.setXmax(reader.getDouble("x3"));
            if (!reader.isMissing("y2"))
                element.bbox.setYmax(reader.getDouble("y2"));
            units.add(element);


        }

        units.forEach(AdminUnit::fixMissingValues);
    }

    AdminUnitList sortInplaceByName() {
        class Compare_by_name implements Comparator<AdminUnit> {
            @Override
            public int compare(AdminUnit o1, AdminUnit o2) {
                return o1.getName().compareTo(o2.getName());
            }
        }
        units.sort(new Compare_by_name());
        return this;
    }

    AdminUnitList sortInplaceByArea() {
        Comparator<AdminUnit> comp = new Comparator<AdminUnit>() {
            @Override
            public int compare(AdminUnit o1, AdminUnit o2) {
                if (o1.getArea() > o2.getArea())
                    return 1;
                if (o1.getArea() < o2.getArea())
                    return -1;
                return 0;
            }
        };
        units.sort(comp);
        return this;
    }

    /**
     * Sortuje daną listę jednostek (in place = w miejscu)
     *
     * @return this
     */
    AdminUnitList sortInplaceByPopulation() {
        Comparator<AdminUnit> comp = Comparator.comparing(o -> (o.getPopulation()));
        units.sort(comp);
        return this;

    }

    AdminUnitList sortInplace(Comparator<AdminUnit> cmp) {
        units.sort(cmp);
        return this;
    }

    AdminUnitList sort(Comparator<AdminUnit> cmp) {
        AdminUnitList newlist = new AdminUnitList();
        for (AdminUnit n : this.units) {
            AdminUnit ad = new AdminUnit();
            ad = n;
            newlist.units.add(ad);
        }
        newlist.sortInplace(cmp);
        return newlist;
    }

    /**
     * @param pred referencja do interfejsu Predicate
     * @return nową listę, na której pozostawiono tylko te jednostki,
     * dla których metoda test() zwraca true
     */
    AdminUnitList filter(Predicate<AdminUnit> pred) {
        return new AdminUnitList(units.stream().filter(pred));
    }

    /**
     * Zwraca co najwyżej limit elementów spełniających pred
     *
     * @param pred  - predykat
     * @param limit - maksymalna liczba elementów
     * @return nową listę
     */
    AdminUnitList filter(Predicate<AdminUnit> pred, int limit) {
        return new AdminUnitList(units.stream().filter(pred).limit(limit));
    }

    /**
     * Zwraca co najwyżej limit elementów spełniających pred począwszy od offset
     * Offest jest obliczany po przefiltrowaniu
     *
     * @param pred  - predykat
     * @param -     od którego elementu
     * @param limit - maksymalna liczba elementów
     * @return nową listę
     */
    AdminUnitList filter(Predicate<AdminUnit> pred, int offset, int limit) {
        return new AdminUnitList(units.stream().filter(pred).limit(limit).skip(offset));
    }


}
