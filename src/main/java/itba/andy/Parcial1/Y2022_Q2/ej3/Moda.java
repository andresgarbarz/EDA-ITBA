package itba.andy.Parcial1.Y2022_Q2.ej3;

import java.util.HashMap;
import java.util.Map;

public class Moda {
    // Complejidad Temporal: O(n)
    // Complejidad Espacial: O(n)
    public static int mialgoritmo(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        Map<Integer, Integer> frequencyMap = new HashMap<>();
        int maxCount = 0;

        for (int num : array) {
            int count = frequencyMap.getOrDefault(num, 0) + 1;
            frequencyMap.put(num, count);
            maxCount = Math.max(maxCount, count);
        }

        return maxCount;
    }
}
