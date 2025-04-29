package TP2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import itba.andy.TP2.Soundex;

public class SoundexTester {
    @Test
    void soundexRepresentation_threshold_Test() {
        assertEquals("T624",Soundex.representation("threshold"));
    }
    @Test
    void soundexRepresentation_hold_Test() {
        assertEquals("H430",Soundex.representation("hold"));
    }
    @Test
    void soundexRepresentation_phone_Test() {
        assertEquals("P500",Soundex.representation("phone"));
    }
    @Test
    void soundexRepresentation_foun_Test() {
        assertEquals("F500",Soundex.representation("foun"));
    }
}
