package Parcial1.Y2022_2Q;

import itba.andy.Parcial1.Y2022_Q2.ej2.StringEval;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringEvalTester {
    @Test
    public void testExample1() {
        StringEval evaluator = new StringEval();
        String result = evaluator.evaluate("AA BB CC DEF ^ * AE / + BC -");
        assertEquals("AABCDCCDCCDF", result);
    }

    @Test
    public void testExample2() {
        StringEval evaluator = new StringEval();
        String result = evaluator.evaluate("HOLA QUE + TAL COMO ^ ESTAS / BIEN * + BIEN -");
        assertEquals("HOLAQUELBCILECNOLCOMLCOMO", result);
    }
}
