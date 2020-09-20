package Components.DataStructures.Enums;
import javax.swing.*;

public enum Tables {
    Education,
    Economy,
    Science,
    Military,
    Production;

    static Object[][] educationTable = new Object[][]{
            {"Public Schools", "$100", "$500", "$1500", "$3000"},
            {"University Funding", "$2000", "$5000", "$10000", "$25000"},
            {"Foreign Language Studies", "$1000", "$3000", "$10000", "$50000"},
            {"Experimental Thinking", "$20000", "$50000", "$100000", "$250000"}
            };

    static Object[][] economyTable = new Object[][]{
            {"Public Schools", "$100", "$500", "$1500", "$3000"},
            {"University Funding", "$2000", "$5000", "$10000", "$25000"},
            {"Foreign Language Studies", "$1000", "$3000", "$10000", "$50000"},
            {"Experimental Thinking", "$20000", "$50000", "$100000", "$250000"}
            };

    static Object[][] scienceTable = new Object[][]{
            {"Public Schools", "$100", "$500", "$1500", "$3000"},
            {"University Funding", "$2000", "$5000", "$10000", "$25000"},
            {"Foreign Language Studies", "$1000", "$3000", "$10000", "$50000"},
            {"Experimental Thinking", "$20000", "$50000", "$100000", "$250000"}
            };

    static Object[][] militaryTable = new Object[][]{
            {"Public Schools", "$100", "$500", "$1500", "$3000"},
            {"University Funding", "$2000", "$5000", "$10000", "$25000"},
            {"Foreign Language Studies", "$1000", "$3000", "$10000", "$50000"},
            {"Experimental Thinking", "$20000", "$50000", "$100000", "$250000"}
            };

    static Object[][] productionTable = new Object[][]{
            {"Public Schools", "$100", "$500", "$1500", "$3000"},
            {"University Funding", "$2000", "$5000", "$10000", "$25000"},
            {"Foreign Language Studies", "$1000", "$3000", "$10000", "$50000"},
            {"Experimental Thinking", "$20000", "$50000", "$100000", "$250000"}
            };

    static String[] headers = new String[]{" ", "Tier 1", "Tier 2", "Tier 3", "Tier 4"};

    public static JTable returnTable(Tables table) {
        switch(table) {
            case Education: return new JTable(educationTable, headers);
            case Economy: return new JTable(economyTable, headers);
            case Science: return new JTable(scienceTable, headers);
            case Military: return new JTable(militaryTable, headers);
            case Production: return new JTable(productionTable, headers);
            default: return null;
        }
    }

}
