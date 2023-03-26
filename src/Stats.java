import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//having some fun programmatically figuring out the best defensive double types
public class Stats {

    // have two identical arrays, loop through one, creating pairs with the other to create a third array of all the possible pairs
    // simply run each possible pair through t
    String[] typeList1 = Main.types;
    String[] typeList2 = Main.types;
    static List<String> types = Arrays.asList(Main.types);

    public static ArrayList<ArrayList<String>> getAllDefenseCombos() { //17 * 18 = 306 combos
        ArrayList<ArrayList<String>> allDefenseTypeCombinations = new ArrayList<ArrayList<String>>();

        for (String type1 : types) {
            for (String type2 : types) {
                if (type1 != type2) { //can't have the same type twice
                    ArrayList<String> typeCombo = new ArrayList<>();
                    typeCombo.add(type1);
                    typeCombo.add(type2);
                    allDefenseTypeCombinations.add(typeCombo);
                }
            }
        }
        return allDefenseTypeCombinations;
    }

    public static Defender getBestDefender() {
        ArrayList<ArrayList<String>> allCombos = getAllDefenseCombos();
        ArrayList<Defender> allDefenders = new ArrayList<>();
        for (ArrayList<String> typeCombo : allCombos) {
            Defender defender = new Defender();
            defender.type1 = typeCombo.get(0);
            defender.type2 = typeCombo.get(1);
            defender.getDefendEffectiveness(defender);
            allDefenders.add(defender);
        }
        ArrayList<ArrayList<String>> gradesForTopDefender = new ArrayList<>();
        int mostCounters = 0;
        Defender bestDefender = null;
        for (Defender defender : allDefenders) {
            ArrayList<ArrayList<String>> topCounters = new ArrayList<>();
            //int counterCount = defender.isNoEffectDefending.size() + defender.isExtremeDefending.size() + defender.isSuperDefending.size();
            int counterCount = defender.isNoEffectDefending.size() + defender.isExtremeDefending.size();
            if (counterCount > mostCounters){
                mostCounters = counterCount;
                bestDefender = defender;
            }
        }

        return bestDefender;
    }

}
