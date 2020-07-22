package Components;

import java.util.ArrayList;
import java.util.List;

public class Economy {
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
