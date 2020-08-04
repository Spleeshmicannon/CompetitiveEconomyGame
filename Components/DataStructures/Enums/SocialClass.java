package Components.DataStructures.Enums;

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
}
