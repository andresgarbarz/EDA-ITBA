package itba.andy.TP2;

public class Soundex {
    private static final int[] SOUNDEX_TABLE = {
            0, 1, 2, 3, 0, 1, 2, 0, 0, 2,
            2, 4, 5, 5, 0, 1, 2, 6, 2, 3,
            0, 1, 0, 2, 0, 2
    };

    public static String representation(String name) {
        String IN = name.toUpperCase();
        StringBuilder soundexCode = new StringBuilder();
        char firstLetter = IN.charAt(0);
        soundexCode.append(firstLetter);

        int last = getSoundexCode(firstLetter);
        int IN_len = IN.length();
        int zeros = 3;
        for (int i = 1; i < IN_len && zeros > 0; i++) {
            char c = IN.charAt(i);
            int current = getSoundexCode(c);

            if (current != 0 && current != last) {
                soundexCode.append(current);
                last = current;
                zeros--;
            }
        }
        soundexCode.append("0".repeat(zeros));
        return soundexCode.toString();
    }

    private static int getSoundexCode(char c) {
        if (c >= 'A' && c <= 'Z') {
            return SOUNDEX_TABLE[c - 'A'];
        }
        return 0;
    }
}
