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
     * Sets up needs.
     * @param foodAmount the amount of food
     * @param medicineAmount the amount of medicine
     * @param mineralAmount the amount of minerals
     * @param technologyAmount the amount of technology
     */
    public void addNeeds(int foodAmount, int medicineAmount, int mineralAmount, int technologyAmount) {
        needs.add(new Trade(foodAmount, Resource.food));
        needs.add(new Trade(medicineAmount, Resource.medicine));
        needs.add(new Trade(mineralAmount, Resource.minerals));
        needs.add(new Trade(technologyAmount, Resource.technology));
    }

    /**
     * Setting the needs.
     * @param index the index of the Resource
     * @param amount the amount being set
     */
    public void setNeeds(int index, int amount) {
        needs.get(index).amount = amount;
    }

    /**
     * Setting the products.
     * @param index the index of the Resource
     * @param amount the amount being set
     */
    public void setProducts(int index, int amount) {
        needs.get(index).amount = amount;
    }

    /**
     * Sets up products.
     * @param foodAmount the amount of food
     * @param medicineAmount the amount of medicine
     * @param mineralAmount the amount of minerals
     * @param technologyAmount the amount of technology
     */
    public void addProducts(int foodAmount, int medicineAmount, int mineralAmount, int technologyAmount) {
        products.add(new Trade(foodAmount, Resource.food));
        products.add(new Trade(medicineAmount, Resource.medicine));
        products.add(new Trade(mineralAmount, Resource.minerals));
        products.add(new Trade(technologyAmount, Resource.technology));
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

    /**
     * The periodic changes to the economy
     */
    public void CycleEconomy() {
        for(int i = 0; i < Math.max(needs.size(), products.size()); ++i) {
            if(i < needs.size()) ++needs.get(i).amount;
            if(i < products.size()) ++products.get(i).amount;
        }
    }

    /**
     * Buying up everything the economy needs of said Resource.
     * @param price the price of the resource
     * @param r the resource
     */
    public void BuyNeeds(int price, Resource r) {
        needs.forEach(T -> {
            if(T.resource == r) {
                deficit -= T.amount * (price/(reliability/10));
                T.amount = 0;
            }
        });
    }

    /**
     * Selling all of a resource the economy produces
     * @param price the price of the resource
     * @param r the resource
     */
    public void SellProducts(int price, Resource r) {
        products.forEach(T -> {
            if(T.resource == r) {
                deficit += T.amount * (price*(reliability/10));
                T.amount = 0;
            }
        });
    }

    /**
     * Print 100 dollars and reduce reliability by 10 points.
     */
    public void PrintMoney() {
        deficit += 100;
        reliability -= 10;
    }

    /**
     * Spend 100 dollars on an advertising campaign and increase reliability by 10.
     */
    public void AdCampaign() {
        deficit -= 100;
        reliability += 10;
    }
}
