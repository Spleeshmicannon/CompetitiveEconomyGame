package Components;

import java.util.Random;

public enum Resource {
    food,
    minerals,
    technology,
    medicine;

    /**
     * Converts Resource to int
     * @param r the Resource
     * @return the int
     */
    public static int ResourceToInt(Resource r) {
        switch(r) {
            case food: return 0;
            case minerals: return 1;
            case technology: return 2;
            case medicine: return 3;
            default: return -1;
        }
    }

    /**
     * Converts Resource to String.
     * @param r the Resource
     * @return the String
     */
    public static String ResourceToString(Resource r) {
        switch(r) {
            case food: return "food";
            case minerals: return "minerals";
            case technology: return "technology";
            case medicine: return "medicine";
            default: return "";
        }
    }

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