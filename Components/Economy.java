package Components;

import java.util.ArrayList;
import java.util.List;

public class Economy {
    private List<Trade> needs;
    private List<Trade> products;
    public double reliability;
    public double deficit;

    /**
     * initialises the needs and products lists. Assigns the reliability and deficit fields.
     * @param reliability the Economies reliability (like a ledger)
     * @param deficit the deficit value
     */
    public Economy(double reliability, double deficit) {
        needs = new ArrayList<Trade>();
        products = new ArrayList<Trade>();
        this.reliability = reliability;
        this.deficit = deficit;
    }

    /**
     * Adds a resource to the needs list
     * @param r the added resource
     */
    public void addNeed(Resource r) {
        needs.add(new Trade(1, r));
    }

    /**
     * Adds a resource to the products list
     * @param r the added resource
     */
    public void addProduct(Resource r) {
        products.add(new Trade(1,r));
    }

    /**
     * returns the needs list
     * @return the needs list
     */
    public Trade[] getNeeds() {
        return needs.toArray(new Trade[0]);
    }

    /**
     * returns the products list
     * @return the products list
     */
    public Trade[] getProducts() {
        return products.toArray(new Trade[0]);
    }

    public void CycleEconomy() {
        for(int i = 0; i < Math.max(needs.size(), products.size()); ++i) {
            if(i <= needs.size()) ++needs.get(i).amount;
            if(i <= needs.size()) ++products.get(i).amount;
        }
    }

    public void BuyNeeds() {

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
