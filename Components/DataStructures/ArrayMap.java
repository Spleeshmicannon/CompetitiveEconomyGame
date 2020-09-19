package Components.DataStructures;
import java.util.HashMap;
import java.util.Map;

public class ArrayMap<T> {

    // Establishes map and integer iterator
    private Map<T, Integer> keyIndex;
    private int nextIndex;

    /**
     * Creates a new HashMap
     */
    public ArrayMap() {
        keyIndex = new HashMap<>();
        nextIndex = 0;
    }

    /**
     * adds new entry to the HashMap with the value: last index + 1
     * @param key the key used for the given map
     */
    public void put(T key) {
        keyIndex.put(key, nextIndex++);
    }

    /**
     * grabs the index of a given key in the map
     * @param key the key used for the given map
     * @return the integer value representing the key's index
     */
    public int getIndex(T key) {
        return keyIndex.get(key);
    }
}
