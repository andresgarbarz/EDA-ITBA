package TP2;

import itba.andy.TP2.Metaphone;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MetaphoneTester {

    @Test
    public void testBasicWords() {
        assertEquals("WRT", Metaphone.encode("word"));
        assertEquals("TST", Metaphone.encode("test"));
        assertEquals("HFN", Metaphone.encode("hyphon"));
    }

    @Test
    public void testSimilarSoundingWords() {
        String code1 = Metaphone.encode("phonetic");
        String code2 = Metaphone.encode("fonetic");
        assertEquals(code1, code2);

        code1 = Metaphone.encode("wright");
        code2 = Metaphone.encode("right");
        assertEquals(code1, code2);
    }

    @Test
    public void testSpecialCases() {
        assertEquals("XN", Metaphone.encode("sean"));
        assertEquals("JN", Metaphone.encode("jean"));
        assertEquals("KN", Metaphone.encode("khan"));
    }

    @Test
    public void testEmptyAndNull() {
        assertEquals("", Metaphone.encode(""));
        assertNull(Metaphone.encode(null));
    }
}
