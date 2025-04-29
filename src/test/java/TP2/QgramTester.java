package TP2;

import itba.andy.TP2.Qgram;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QgramTester {
    Qgram g= new Qgram(2);

    @Test
    public void print_alal() {
        g.printTokens("alal");
    }

    @Test
    public void print_salesal() {
        g.printTokens("salesal");
    }

    @Test
    public void print_alale() {
        g.printTokens("alale");
    }

    @Test
    public void similarity() {
        assertEquals(0.4285, g.similarity("salesal", "alale"), 0.0001);
    }

}
