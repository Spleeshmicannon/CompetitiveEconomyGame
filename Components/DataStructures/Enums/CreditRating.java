package Components.DataStructures.Enums;

public enum CreditRating {
    AAA,
    AA_high,
    AA,
    AA_low,
    A_high,
    A,
    A_low,
    BBB_high,
    BBB,
    BBB_low,
    BB_high,
    BB,
    BB_low,
    B_high,
    B,
    B_low,
    CCC_high,
    CCC,
    CCC_low,
    CC_high,
    CC,
    CC_low,
    C_high,
    C,
    C_low,
    D;

    public static Integer ratingToInt(CreditRating cr) {
        int iterator = 1;
        for (CreditRating rating : CreditRating.values()) {
            if (cr == rating) {
                return iterator;
            }
            iterator++;
        }
        return -1;
    }

    public static String ratingToString(CreditRating cr) {

        String creditString = cr.name();

        if (creditString.contains("_high")) {
            creditString = creditString.replaceAll("_high", "+");
        } else if (creditString.contains("_low")) {
            creditString = creditString.replaceAll("_low", "-");
        }

        return creditString;
    }

}
