
import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.util.*;
import java.util.regex.Pattern;

import static java.lang.System.out;


public class Main {


    public static void main(String[] args) throws Exception {

        AdminUnitList units = new AdminUnitList();
        units.read();
        //   wypis
//        units.list(System.out);

        // wypis z limitem
//        units.list(System.out, 2, 12);

        //wypis z "Rokietnica"
//        units.selectByName("Rokietnica", false).list(System.out);

        //sąsiedzi gminy Rokietnica
//        units.getNeighbors(units.units.get(5135), 0).list(System.out);

        //sąsiedzi Lisiki liniowo i rekurencyjnie
//        units.getNeighbors(units.units.get(15196), 0).list(System.out);
//        units.tree_search(units.units.get(15196), 10, units.units.get(15196));
//        System.out.print(units.units.get(15196).neighbors.toString());

        // sortowanie w miejscu
//        units.sortInplaceByName().list(System.out, 0, 100);
//        units.sortInplaceByArea().list(System.out, 0, 100);


        //sortowanie nie w miejcu
//        AdminUnitList unitsSorted = units.sort(Comparator.comparing(AdminUnit::getName));
//        unitsSorted.list(System.out, 0, 5);

        //filtry
//        units.filter(a -> a.getName().startsWith("Ż")).sortInplaceByArea().list(out);
//        units.filter(a -> a.getName().startsWith("K")).sortInplaceByArea().list(out);

        //query

//        AdminUnitQuery query = new AdminUnitQuery()
//                .selectFrom(units)
//                .where(a -> a.getArea() > 1000)
//                .or(a -> a.getName().startsWith("Sz"))
//                .sort(Comparator.comparingDouble(AdminUnit::getArea))
//                .limit(100);
//        query.execute().list(out);

    }

}
