package Components.DataStructures.Enums;

import java.awt.*;

public enum SocialClass {

    // Classes
    Royalty,
    Aristocrat,
    Proletariat;

    /**
     * Retrieve the SocialClass in control of a given resource
     * @param r the chosen resource
     * @return the given SocialClass
     */
    public static SocialClass resourceToClass(Resource r) {
        switch(r) {
            case food:
            case minerals:
                return Proletariat; // if food or minerals, return Proletariat

            case technology:
            case medicine:
                return Aristocrat; // if technology or medicine, return Aristocrat
            default: return Royalty;
        }
    }

    /**
     * returns an RGB colour value representing the given SocialClass
     * @param sc the given SocialClass
     * @return an RGB colour value
     */
    public static Color classToColor(SocialClass sc) {
        switch(sc) {
            case Proletariat: return new Color(142, 64, 60);
            case Aristocrat: return new Color(65, 85, 128);
            case Royalty: return new Color(127, 67, 117);
            default: return new Color(1,1,1);
        }
    }
}
