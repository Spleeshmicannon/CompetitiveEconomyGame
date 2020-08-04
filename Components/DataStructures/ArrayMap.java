package Components.DataStructures;
import java.util.HashMap;
import java.util.Map;

public class ArrayMap<T> {
    private Map<T, Integer> keyIndex;
    private int nextIndex;

    public ArrayMap() {
        keyIndex = new HashMap<>();
        nextIndex = 0;
    }

    public void put(T key) {
        keyIndex.put(key, nextIndex++);
    }

    public int getIndex(T key) {
        return keyIndex.get(key);
    }
}
