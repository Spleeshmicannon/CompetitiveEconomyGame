package Components.DataStructures;

import java.util.HashMap;
import java.util.Random;

public class GlobalPrices {
    private HashMap<Resource, Integer> globalPrices;

    /**
     * Not yet implemented fully. Will work with the network class for online stuff when finished.
     */
    public GlobalPrices() {

    }

    /**
     * Sets the globalPrices field to the globalPrices parameter.
     * @param globalPrices the HashMap the globalPrices field is set too
     */
    public void setGlobalPrices(HashMap<Resource, Integer> globalPrices) {
        this.globalPrices = globalPrices;
    }

    /**
     * Sets the globalPrices field to random default values.
     */
    public void setGlobalPrices() {
        globalPrices = new HashMap<>();
        globalPrices.put(Resource.food, new Random().nextInt(15)*10);
        globalPrices.put(Resource.minerals, new Random().nextInt(15)*20);
        globalPrices.put(Resource.technology, new Random().nextInt(15)*20);
        globalPrices.put(Resource.medicine, new Random().nextInt(15)*10);
    }

    /**
     * gets the globalPrices field as a HashMap
     * @return globalPrices HashMap
     */
    public HashMap<Resource, Integer> getGlobalPrices() {
        return globalPrices;
    }

    /**
     * Gets an int based on the Resource given in the parameter. Using the Resource as a key.
     * @param r the key  (i.e. the resource in question)
     * @return the value  (i.e. the price of the resource)
     */
    public int getGlobalPrices(Resource r) {
        return globalPrices.get(r);
    }

    public void CyclePrices() {
        // TODO: implement price change cycle
    }
}
