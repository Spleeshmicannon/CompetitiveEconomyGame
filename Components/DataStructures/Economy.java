package Components.DataStructures;

import Components.DataStructures.Enums.Resource;
import Components.DataStructures.Enums.SocialClass;

import java.util.HashMap;

public class Economy {
    private HashMap<Resource, Integer> needs;
    private HashMap<Resource, Integer> products;
    private HashMap<SocialClass, Integer> people;
    private HashMap<SocialClass, Integer> tax;
    public int Health;
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
        people = new HashMap<>();
        tax = new HashMap<>();

        Health = 100;

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
     * Adds default values for people.
     */
    public void addPeople() {
        people.put(SocialClass.Royalty, 1);
        people.put(SocialClass.Aristocrat, 10);
        people.put(SocialClass.Proletariat, 100);
    }

    /**
     * Adds specific values for people.
     * @param royaltyAmount the amount of royalty people
     * @param aristocracyAmount the amount of aristocrats
     * @param proletariatAmount the amount of workers
     */
    public void addPeople(int royaltyAmount, int aristocracyAmount, int proletariatAmount) {
        people.put(SocialClass.Royalty, royaltyAmount);
        people.put(SocialClass.Aristocrat, aristocracyAmount);
        people.put(SocialClass.Proletariat, proletariatAmount);
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
     * Gets the amount of people in a class.
     * @param sc the class in question
     * @return the amount of people in said class
     */
    public int getPeopleInClass(SocialClass sc) {
        return people.get(sc);
    }

    /**
     * The periodic changes to the economy
     */
    public void CycleEconomy() {

        int aristocratCoefficient = (int) (0.01 * people.get(SocialClass.Aristocrat));
        int proletariatCoefficient = (int) (0.01 * people.get(SocialClass.Proletariat));

        for (HashMap.Entry<Resource, Integer> entry : needs.entrySet()) {
            int temp;
            if ((temp = entry.getValue()) != -1) {
                entry.setValue(temp + 1);
            }
        }

        for (HashMap.Entry<Resource, Integer> entry : products.entrySet()) {
            int temp;
            if ((temp = entry.getValue()) != -1) {
                entry.setValue(temp + 1);
                if (SocialClass.resourceToClass(entry.getKey()) == SocialClass.Aristocrat) {
                    people.replace(SocialClass.Aristocrat, people.get(SocialClass.Aristocrat) + 1);
                } else if (SocialClass.resourceToClass(entry.getKey()) == SocialClass.Proletariat) {
                    people.replace(SocialClass.Proletariat, people.get(SocialClass.Proletariat) + 1);
                }
            }
        }

        if (needs.get(Resource.food) != -1) {
            if (needs.get(Resource.food) > people.get(SocialClass.Royalty) * 100) {
                if (aristocratCoefficient > 0) {
                    Health -= aristocratCoefficient;
                    people.replace(SocialClass.Proletariat, people.get(SocialClass.Proletariat) - ((int) (0.01 * (people.get(SocialClass.Proletariat))) + 1));
                }
            }
        }

        if (needs.get(Resource.medicine) != -1) {
            if (needs.get(Resource.medicine) > people.get(SocialClass.Royalty) * 100) {
                if (proletariatCoefficient > 0) {
                    Health -= proletariatCoefficient;
                    people.replace(SocialClass.Aristocrat, people.get(SocialClass.Aristocrat) - ((int) (0.01 * (people.get(SocialClass.Aristocrat))) + 1));
                }
            }

        }

        if (needs.get(Resource.minerals) != -1) {
            if (needs.get(Resource.minerals) > people.get(SocialClass.Royalty) * 100) {
                if (proletariatCoefficient > 0) {
                    Health -= proletariatCoefficient;
                    people.replace(SocialClass.Aristocrat, people.get(SocialClass.Aristocrat) - ((int) (0.01 * (people.get(SocialClass.Aristocrat))) + 1));
                }
            }
        }

        if (needs.get(Resource.technology) != -1) {
            if (needs.get(Resource.technology) > people.get(SocialClass.Royalty) * 100) {
                if (proletariatCoefficient > 0) {
                    Health -= proletariatCoefficient;
                    people.replace(SocialClass.Aristocrat, people.get(SocialClass.Aristocrat) - ((int) (0.01 * (people.get(SocialClass.Aristocrat))) + 1));
                }
            }
        }
    }


    /**
     * Buying up everything the economy needs of said Resource.
     * @param price the price of the resource
     * @param r the resource
     */
    public void BuyNeeds(int price, Resource r) {
        deficit -= needs.get(r) * price;
        needs.replace(r, 0);
    }

    /**
     * Selling all of a resource the economy produces
     * @param price the price of the resource
     * @param r the resource
     */
    public void SellProducts(int price, Resource r) {
        deficit += products.get(r) * (price + reliability + getPeopleInClass(SocialClass.resourceToClass(r)));
        products.replace(r, 0);
    }

    /**
     * Print 100 dollars and reduce reliability by 10 points.
     */
    public void PrintMoney() {
        deficit += 100;
        reliability -= 10;
    }

    /**
     * Spend an exponentially increasing amount to get an advertisement campaign to increase reliability by 10
     * (increasing the sell price of created products).
     */
    public void AdCampaign() {

        if (reliability < 1000) {
            if (reliability < 100) {
                System.out.println(Math.round((1 + 0.01 * Math.pow(reliability, 1.1)) * 10) * 10);
                deficit -= Math.round((1 + 0.01 * Math.pow(reliability, 1.1)) * 10) * 10;

            } else if (reliability >= 100 & reliability < 250) {
                System.out.println(Math.round((1 + 0.01 * (Math.pow(100, 1.1) + Math.pow(reliability-100, 1.2)) * 10)) * 10);
                deficit -= Math.round((1 + 0.01 * (Math.pow(100, 1.1) + Math.pow(reliability-100, 1.2)) * 10)) * 10;

            } else {
                System.out.println(Math.round((1 + 0.01 * (Math.pow(100, 1.1) + Math.pow(150, 1.2) + Math.pow(reliability-250, 1.3)) * 10)) * 10);
                deficit -= Math.round((1 + 0.01 * (Math.pow(100, 1.1) + Math.pow(150, 1.2) + Math.pow(reliability-250, 1.3)) * 10)) * 10;

            }

            reliability += 10;
        }

    }
}
