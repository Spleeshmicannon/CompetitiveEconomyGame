package Components.DataStructures;

import Components.DataStructures.Enums.Resource;

public class Trade {
    public int amount;
    public Resource resource;

    /**
     * Just a simple data structure that acts similarly to a dictionary.
     * This will probably be expanded upon in future though.
     * @param amount the amount of the resource
     * @param resource the resource
     */
    public Trade(int amount, Resource resource) {
        this.amount = amount;
        this.resource = resource;
    }
}
