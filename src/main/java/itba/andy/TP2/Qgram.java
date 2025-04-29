package itba.andy.TP2;

import java.util.HashMap;

public class Qgram {
    private final int Q;

    public Qgram(int q) {
        this.Q = q;
    }

    private HashMap<String, Integer> getTokens(String s, int len) {
        HashMap<String, Integer> tokens = new HashMap<>();
        StringBuilder base = new StringBuilder(s);
        base.insert(0, "#".repeat(Q - 1));
        base.append("#".repeat(Q - 1));
        for (int i = Q - 1; i < len + Q; i++) {
            String token = base.substring(i - Q + 1, i + 1);
            if (tokens.containsKey(token))
                tokens.put(token, tokens.get(token) + 1);
            else
                tokens.put(token, 1);
        }
        return tokens;
    }

    public void printTokens(String s) {
        HashMap<String, Integer> tokens = getTokens(s, s.length());
        for (String token : tokens.keySet()) {
            System.out.println(token + " " + tokens.get(token));
        }
    }

    private int getDistance(HashMap<String, Integer> t1, HashMap<String, Integer> t2) {
        int d = 0;

        for (String key : t1.keySet()) {
            if (t2.containsKey(key)) {
                d += Math.min(t1.get(key), t2.get(key));
            }
        }

        return d;
    }

    public double similarity(String a, String b) {

        int a_len = a.length();
        int b_len = b.length();

        HashMap<String, Integer> t1 = getTokens(a, a_len);
        HashMap<String, Integer> t2 = getTokens(b, b_len);

        int n = a_len + Q - 1; // Cantidad de Q-grams en A
        int m = b_len + Q - 1; // Cantidad de Q-grams en B

        int d = getDistance(t1, t2);

        System.out.println("n: " + n);
        System.out.println("m: " + m);
        System.out.println("d: " + d);

        /*
         * Podría optimizarse con return (2d) / (n + m) pero quería mantener la fracción
         * de la presentación
         */
        int ns = n + m - 2 * d; // not shared tokens

        return (double) (n + m - ns) / (n + m);
    }
}