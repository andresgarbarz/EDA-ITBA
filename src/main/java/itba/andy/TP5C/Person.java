package itba.andy.TP5C;

/* Código de @BautistaPessagno */

public class Person implements Comparable<Person> {
    int legajo;
    String nombre;

    public Person(int legajo, String nombre) {
        this.legajo = legajo;
        this.nombre = nombre;
    }

    @Override
    public int compareTo(Person o) {
        return Integer.compare(this.legajo, o.legajo);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
