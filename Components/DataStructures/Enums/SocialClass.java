package Components.DataStructures.Enums;

import java.awt.*;

public enum SocialClass {
    Royalty,
    Aristocrat,
    Proletariat;

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

    public static Color classToColor(SocialClass sc) {
        switch(sc) {
            case Proletariat: return new Color(142, 64, 60);
            case Aristocrat: return new Color(65, 85, 128);
            case Royalty: return new Color(127, 67, 117);
            default: return new Color(1,1,1);
        }
    }
}
