package Components.DataStructures.Enums;

import java.util.Random;

public enum Country {

    // All playable countries
    britishEmpire,
    mingDynasty,
    sovietUnion,
    scandinavia,
    oceania,
    japan,
    thirdReich,
    easternEurope,
    kongo,
    southAfrica,
    libya,
    yugoslavia;

    /**
     * Pick a random country value
     * @return the random value in question
     */
    public static Country getRandomCountry() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }

    /**
     * Get the country as a string.
     * @param c the Country
     * @return the string
     */
    public static String getCountryString(Country c) {
        switch(c) {
            case britishEmpire: return "The British Empire";
            case mingDynasty: return "The Ming Dynasty";
            case sovietUnion: return "The Soviet Union";
            case scandinavia: return "Scandinavia";
            case oceania: return "Oceania";
            case japan: return "Japan";
            case thirdReich: return "The Third Reich of Nazi Germany";
            case easternEurope: return "Eastern Europe Republic";
            case kongo: return "The Democratic Republic of Kongo";
            case southAfrica: return "South Africa";
            case libya: return "Libya";
            case yugoslavia: return "Yugoslavia";
            default: return "The Custom Empire";
        }
    }
}
