package Components.Data;

import java.util.Random;

public class letters {

    // Messages to be displayed in the letters tab of the GUI
    private static final String[] text = new String[] {
            "Dear President, my family is starving. Please Lower the taxes.",
            "Dear President, thankyou so much for your latest policy!",
            "Dear President, help, we're dying out here",
            "Dear President, you are amazing, I love you, I love you!",
            "Dear President, thankyou.",
            "Dear President, you are ruining this country",
            "Dear President, have you considered the Prolitariate?",
            "Dear President, have you considered the Aristocrats?",
            "Dear President, are you considering anyone but yourself?",
            "Dear President, if you don't change your ways, we will depose you. Don't test us."
    };

    // First names to be chosen from when signing the letter to government
    private static final String[] firstName = new String[] {
            "John",
            "Mary",
            "Jack",
            "Nathan",
            "Rhonda",
            "Alice",
            "Steve",
            "Lucy",
            "Tim",
            "Katy"
    };

    // Last names to be chosen from when signing the letter to government
    private static final String[] lastName = new String[] {
            "McHugh",
            "Benson",
            "Smith",
            "Nguyen",
            "Mack",
            "Stevenson",
            "Jefferds",
            "Adam",
            "Dihl"
    };

    /**
     * Chooses between a signed or anonymous letter,
     * and returns it as a String to be displayed
     * @return String, message with a signed name or anonymous
     */
    public static String getRandomText() {

        String fullName;

        if (new Random().nextInt(10) < 2) {
            fullName = "Anonymous";
        } else {
            fullName = firstName[new Random().nextInt(firstName.length)] + " " +
                              lastName[new Random().nextInt(lastName.length)];
        }

        return text[new Random().nextInt(text.length)] + "~ " + fullName;
    }
}
