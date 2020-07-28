package Components.DataStructures;

import java.util.HashMap;

public class Economy {
    private HashMap<Resource, Integer> needs;
    private HashMap<Resource, Integer> products;
    public double reliability;
    public double deficit;

    /**
     * initialises the needs and products lists. Assigns the reliability and deficit fields.
     * @param reliability the Economies reliability (like a ledger)
     * @param deficit the deficit value
     */
    public Economy(double reliability, double deficit) {
        needs = new HashMap<>();
        products = new HashMap<>();
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
        needs.put(Resource.food, foodAmount);
        needs.put(Resource.minerals, mineralAmount);
        needs.put(Resource.technology, technologyAmount);
        needs.put(Resource.medicine, medicineAmount);
    }

    /**
     * Sets up products.
     * @param foodAmount the amount of food
     * @param medicineAmount the amount of medicine
     * @param mineralAmount the amount of minerals
     * @param technologyAmount the amount of technology
     */
    public void addProducts(int foodAmount, int medicineAmount, int mineralAmount, int technologyAmount) {
        products.put(Resource.food, foodAmount);
        products.put(Resource.minerals, mineralAmount);
        products.put(Resource.technology, technologyAmount);
        products.put(Resource.medicine, medicineAmount);
    }

    /**
     * returns the needs HashMap
     * @return the needs HashMap
     */
    public HashMap<Resource, Integer> getNeeds() {
        return needs;
    }

    /**
     * returns the products HashMap
     * @return the products HashMap
     */
    public HashMap<Resource, Integer> getProducts() {
        return products;
    }

    /**
     * The periodic changes to the economy
     */
    public void CycleEconomy() {
        for(HashMap.Entry<Resource, Integer> entry : needs.entrySet()) {
            int temp;
            if((temp = entry.getValue()) != 0) {
                entry.setValue(temp + 1);
            }
        }

        for(HashMap.Entry<Resource, Integer> entry : products.entrySet()) {
            int temp;
            if((temp = entry.getValue()) != 0) {
                entry.setValue(temp + 1);
            }
        }
    }

    /**
     * Buying up everything the economy needs of said Resource.
     * @param price the price of the resource
     * @param r the resource
     */
    public void BuyNeeds(int price, Resource r) {
        needs.replace(r, 0);
        deficit -= price;
    }

    /**
     * Selling all of a resource the economy produces
     * @param price the price of the resource
     * @param r the resource
     */
    public void SellProducts(int price, Resource r) {
        products.replace(r, 0);
        deficit += price;
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
