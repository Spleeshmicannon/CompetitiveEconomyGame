package Components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Economy {
    public enum Resource {
        food,
        minerals,
        technology,
        medicine;

        /**
         * Pick a random resource value
         * @return the random value in question
         */
        public static Resource getRandomResource() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }

        /**
         * Pick a random resource value with an exclusion
         * @param exclusion the excluded resource value
         * @return the random value in question
         */
        public static Resource getRandomResource(Resource exclusion) {
            Random random = new Random();
            Resource result = null;
            do {
                result = values()[random.nextInt(values().length)];
            } while(result == exclusion);
            return result;
        }
    }

    private List<Resource> needs;
    private List<Resource> products;
    public double reliability;
    public double deficit;

    /**
     * initialises the needs and products lists. Assigns the reliability and deficit fields.
     * @param reliability the Economies reliability (like a ledger)
     * @param deficit the deficit value
     */
    public Economy(double reliability, double deficit) {
        needs = new ArrayList<Resource>();
        products = new ArrayList<Resource>();
        this.reliability = reliability;
        this.deficit = deficit;
    }

    /**
     * Adds a resource to the needs list
     * @param r the added resource
     */
    public void addNeed(Resource r) {
        needs.add(r);
    }

    /**
     * Adds a resource to the products list
     * @param r the added resource
     */
    public void addProduct(Resource r) {
        products.add(r);
    }

    /**
     * returns the needs list
     * @return the needs list
     */
    public Resource[] getNeeds() {
        return needs.toArray(new Resource[0]);
    }

    /**
     * returns the products list
     * @return the products list
     */
    public Resource[] getProducts() {
        return products.toArray(new Resource[0]);
    }

    public void BuyNeeds() {
        // Todo: implement mechanic
    }

    public void SellProducts() {
        // Todo: implement mechanic
    }

    public void PrintMoney() {
        // Todo: implement mechanic
    }

    public void AdCampaign() {
        // Todo: implement mechanic
    }
}
