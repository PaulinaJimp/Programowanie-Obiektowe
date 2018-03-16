import java.io.PrintStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class ListaOsob {
    List<Osoba> osoby = new ArrayList<>();
    //Map<Long, Osoba> id = new HashMap<>();
    Map<String, Integer> iloscM = new HashMap<>();
    Map<String, Integer> iloscK = new HashMap<>();
    Map<String, Double> popNajwK = new HashMap<String, Double>();
    Map<String, Double> popNajmK = new HashMap<String, Double>();
    Map<String, Double> popNajwM = new HashMap<String, Double>();
    Map<String, Double> popNajmM = new HashMap<String, Double>();

    public ListaOsob(Stream<Osoba> adminUnitStream) {
        osoby = adminUnitStream.collect(Collectors.toList());
    }

    public ListaOsob() {
        //
    }


    void list(PrintStream out) {
        for (Osoba a : osoby) {
            out.print(a.toString() + "\n");
        }
    }

    void list(PrintStream out, int offset, int limit) {
        for (int i = 0; i < limit; i++)
            out.print(osoby.get(i + offset).toString() + "\n");
    }

    /**
     * Zwraca nową listę zawierającą te obiekty Osoba, których nazwa pasuje do wzorca
     *
     * @param pattern - wzorzec dla nazwy
     * @param regex   - jeśli regex=true, użyj finkcji String matches(); jeśli false użyj funkcji contains()
     * @return podzbiór elementów, których nazwy spełniają kryterium wyboru
     */
    ListaOsob selectByName(String pattern, boolean regex) {
        ListaOsob ret = new ListaOsob();
        if (regex) {
            for (Osoba a : osoby) {
                if (a.getName().matches(pattern))
                    ret.osoby.add(a);
            }

        } else {
            for (Osoba a : osoby) {
                if (a.getName().contains(pattern))
                    ret.osoby.add(a);
            }
        }
        return ret;
    }

    void read() throws Exception {
        CSVReader reader = new CSVReader("imiona-2000-2016.csv", ";", true);
        while (reader.next()) {
            Osoba element = new Osoba();
            if (!reader.isMissing(1))
                element.setName(reader.get(1));
            if (!reader.isMissing(0)) {
                element.setYear(reader.getInt(0));
            }
            if (!reader.isMissing(2))
                element.setNumber(reader.getInt(2));
            if (!reader.isMissing(3))
                element.setSex(reader.get(3));

            osoby.add(element);
        }
        // ustawienie_map();
    }

    void ustawienie_map() {


        for (Osoba element : this.osoby
                ) {
            double popularity = ((double) element.getNumber() * 100) / this.filter(a -> a.getYear() == element.getYear()).ileurodzonych();

            if (element.getSex().equals("M")) {
                iloscM.computeIfPresent(element.getName(), (k, v) -> v + element.getNumber());
                iloscM.computeIfAbsent(element.getName(), k -> element.getNumber());
                if (!popNajmM.containsKey(element.getName())) {
                    //System.out.print(popularity+" "+element.getNumber()+" "+this.filter(a -> a.getYear() == element.getYear()).ileurodzonych()+"\n");
                    popNajmM.put(element.getName(), popularity);
                    popNajwM.put(element.getName(), popularity);
                } else {
                    if (popNajwM.get(element.getName()) <= popularity)
                        popNajwM.put(element.getName(), popularity);
                    if (popNajmM.get(element.getName()) >= popularity)
                        popNajmM.put(element.getName(), popularity);
                }
            } else {
                iloscK.computeIfPresent(element.getName(), (k, v) -> v + element.getNumber());
                iloscK.computeIfAbsent(element.getName(), k -> element.getNumber());
                if (!popNajmK.containsKey(element.getName())) {
                    //System.out.print(popularity+" "+element.getNumber()+" "+this.filter(a -> a.getYear() == element.getYear()).ileurodzonych()+"\n");
                    popNajmK.put(element.getName(), popularity);
                    popNajwK.put(element.getName(), popularity);
                } else {
                    if (popNajwK.get(element.getName()) <= popularity)
                        popNajwK.put(element.getName(), popularity);
                    if (popNajmK.get(element.getName()) >= popularity)
                        popNajmK.put(element.getName(), popularity);
                }
            }

        }
    }

    ListaOsob sortInplaceByName() {
        class Compare_by_name implements Comparator<Osoba> {
            @Override
            public int compare(Osoba o1, Osoba o2) {
                return o1.getName().compareTo(o2.getName());
            }
        }
        osoby.sort(new Compare_by_name());
        return this;
    }

    /**
     * Sortuje daną listę jednostek (in place = w miejscu)
     *
     * @return this
     */
    ListaOsob sortInplaceByPopulation() {
        Comparator<Osoba> comp = Comparator.comparing(o -> (o.getNumber()));
        osoby.sort(comp);
        return this;

    }

    ListaOsob sortInplace(Comparator<Osoba> cmp) {
        osoby.sort(cmp);
        return this;
    }

    ListaOsob sort(Comparator<Osoba> cmp) {
        ListaOsob newlist = new ListaOsob();
        for (Osoba n : this.osoby) {
            Osoba ad = new Osoba();
            ad = n;
            newlist.osoby.add(ad);
        }
        newlist.sortInplace(cmp);
        return newlist;
    }

    /**
     * @param pred referencja do interfejsu Predicate
     * @return nową listę, na której pozostawiono tylko te jednostki,
     * dla których metoda test() zwraca true
     */
    ListaOsob filter(Predicate<Osoba> pred) {
        return new ListaOsob(osoby.stream().filter(pred));
    }

    /**
     * Zwraca co najwyżej limit elementów spełniających pred
     *
     * @param pred  - predykat
     * @param limit - maksymalna liczba elementów
     * @return nową listę
     */
    ListaOsob filter(Predicate<Osoba> pred, int limit) {
        return new ListaOsob(osoby.stream().filter(pred).limit(limit));
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
    ListaOsob filter(Predicate<Osoba> pred, int offset, int limit) {
        return new ListaOsob(osoby.stream().filter(pred).limit(limit).skip(offset));
    }


    void najpopularniejszeImona() {
        for (Osoba element : this.osoby
                ) {
            if (element.getSex().equals("M")) {
                iloscM.computeIfPresent(element.getName(), (k, v) -> v + element.getNumber());
                iloscM.computeIfAbsent(element.getName(), k -> element.getNumber());
            } else {
                iloscK.computeIfPresent(element.getName(), (k, v) -> v + element.getNumber());
                iloscK.computeIfAbsent(element.getName(), k -> element.getNumber());
            }
        }
        System.out.print("Męskie najpopularniejsze imię : " + Collections.max(iloscM.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey() + "\n");
        System.out.print("Żeńskie najpopularniejsze imię : " + Collections.max(iloscK.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey() + "\n");
    }

    int ileurodzonych() {
        int suma = 0;
        for (Osoba pom : this.osoby
                ) {
            suma = suma + pom.getNumber();
        }
        return suma;
    }

    void najwieksz_zmiana() {
        for (Osoba element : this.osoby
                ) {
            double popularity = ((double) element.getNumber() * 100) / this.filter(a -> a.getYear() == element.getYear()).ileurodzonych();

            if (element.getSex().equals("M")) {
                if (!popNajmM.containsKey(element.getName())) {
                    popNajwM.put(element.getName(), popularity);
                    popNajmM.put(element.getName(), popularity);
                } else {
                    if (popNajwM.get(element.getName()) <= popularity)
                        popNajwM.put(element.getName(), popularity);
                    if (popNajmM.get(element.getName()) >= popularity)
                        popNajmM.put(element.getName(), popularity);
                }
            } else {
                if (!popNajmK.containsKey(element.getName())) {
                    popNajmK.put(element.getName(), popularity);
                    popNajwK.put(element.getName(), popularity);
                } else {
                    if (popNajwK.get(element.getName()) <= popularity)
                        popNajwK.put(element.getName(), popularity);
                    if (popNajmK.get(element.getName()) >= popularity)
                        popNajmK.put(element.getName(), popularity);
                }
            }
        }
        double dif_max = 0;
        double dif_pom = 0;
        String m = "";
        String k = "";
        for (Osoba pom : this.osoby
                ) {
            if (pom.getSex().equals("M"))
                dif_pom = this.popNajwM.get(pom.getName()) - this.popNajmM.get(pom.getName());
            if (dif_max < dif_pom) {
                dif_max = dif_pom;
                m = pom.getName();
            } else if (pom.getSex().equals("K")) {
                dif_pom = this.popNajwK.get(pom.getName()) - this.popNajmK.get(pom.getName());
                if (dif_max < dif_pom) {
                    dif_max = dif_pom;
                    k = pom.getName();
                }
            }

        }
        System.out.print("największy skok popularności mieli: " + k + " " + m);
    }

    public static void main(String[] args) throws Exception {

        ListaOsob osoby = new ListaOsob();
        osoby.read();
        osoby.najpopularniejszeImona();
        System.out.print("Ilość dzieci urodzonych w 2000-2016 : " + osoby.ileurodzonych() + "\n");
        osoby.najwieksz_zmiana();

    }
}
