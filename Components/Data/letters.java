package Components.Data;

import java.util.Random;

public class letters {
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
