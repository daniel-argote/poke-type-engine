import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Defender extends BattlePosition{
    String type1;
    String type2;
    ArrayList<String> isExtremeDefending = new ArrayList<>();
    ArrayList<String> isSuperDefending = new ArrayList<>();
    ArrayList<String> isStandardDefending = new ArrayList<>();
    ArrayList<String> isWeakDefending = new ArrayList<>();
    ArrayList<String> isIneffectiveDefending = new ArrayList<>();
    ArrayList<String> isNoEffectDefending = new ArrayList<>();

    // Good chance there's a better way to map these relationships than a huge Switch
    // This takes a type and returns its strengths and weaknesses
    public void getSingleDefenseEffectiveness(Defender defender, String type) {
        switch (type) {
            case "normal" -> {
                defender.isNoEffectDefending.add("ghost");
                defender.isWeakDefending.add("fighting");
            }
            case "fire" -> {
                defender.isSuperDefending.addAll(Arrays.asList("fire", "grass", "ice", "bug", "steel", "fairy"));
                defender.isWeakDefending.addAll(Arrays.asList("water", "ground", "rock"));
            }
            case "water" -> {
                defender.isSuperDefending.addAll(Arrays.asList("fire", "water", "ice", "steel"));
                defender.isWeakDefending.addAll(Arrays.asList("electric", "grass"));
            }
            case "grass" -> {
                defender.isSuperDefending.addAll(Arrays.asList("water", "electric", "grass", "ground"));
                defender.isWeakDefending.addAll(Arrays.asList("fire", "ice", "poison", "flying", "bug"));
            }
            case "electric" -> {
                defender.isSuperDefending.addAll(Arrays.asList("electric", "flying", "steel"));
                defender.isWeakDefending.add("ground");
            }
            case "ice" -> {
                defender.isSuperDefending.add("ice");
                defender.isWeakDefending.addAll(Arrays.asList("fire", "fighting", "rock", "steel"));
            }
            case "fighting" -> {
                defender.isSuperDefending.addAll(Arrays.asList("bug", "rock", "dark"));
                defender.isWeakDefending.addAll(Arrays.asList("flying", "psychic", "fairy"));
            }
            case "poison" -> {
                defender.isSuperDefending.addAll(Arrays.asList("grass", "fighting", "poison", "bug", "fairy"));
                defender.isWeakDefending.addAll(Arrays.asList("ground", "psychic"));
            }
            case "ground" -> {
                defender.isNoEffectDefending.add("electric");
                defender.isSuperDefending.addAll(Arrays.asList("poison", "rock"));
                defender.isWeakDefending.addAll(Arrays.asList("water", "grass", "ice"));
            }
            case "flying" -> {
                defender.isNoEffectDefending.add("ground");
                defender.isSuperDefending.addAll(Arrays.asList("grass", "fighting", "bug"));
                defender.isWeakDefending.addAll(Arrays.asList("electric", "ice", "rock"));
            }
            case "psychic" -> {
                defender.isSuperDefending.addAll(Arrays.asList("fighting", "psychic"));
                defender.isWeakDefending.addAll(Arrays.asList("bug", "ghost", "dark"));
            }
            case "bug" -> {
                defender.isSuperDefending.addAll(Arrays.asList("grass", "fighting", "ground"));
                defender.isWeakDefending.addAll(Arrays.asList("fire", "flying", "rock"));
            }
            case "rock" -> {
                defender.isSuperDefending.addAll(Arrays.asList("normal", "fire", "poison", "flying"));
                defender.isWeakDefending.addAll(Arrays.asList("water", "grass", "fighting", "ground", "steel"));
            }
            case "ghost" -> {
                defender.isNoEffectDefending.addAll(Arrays.asList("normal", "fighting"));
                defender.isSuperDefending.addAll(Arrays.asList("poison", "bug"));
                defender.isWeakDefending.addAll(Arrays.asList("ghost", "dark"));
            }
            case "dragon" -> {
                defender.isSuperDefending.addAll(Arrays.asList("fire", "water", "electric", "grass"));
                defender.isWeakDefending.addAll(Arrays.asList("ice", "dragon", "fairy"));
            }
            case "dark" -> {
                defender.isNoEffectDefending.add("psychic");
                defender.isSuperDefending.addAll(Arrays.asList("ghost", "dark"));
                defender.isWeakDefending.addAll(Arrays.asList("fighting", "bug", "fairy"));
            }
            case "steel" -> {
                defender.isNoEffectDefending.add("poison");
                defender.isSuperDefending.addAll(Arrays.asList("normal", "grass", "ice", "flying", "psychic", "bug", "rock", "dragon", "steel", "fairy"));
                defender.isWeakDefending.addAll(Arrays.asList("fire", "fighting", "ground"));
            }
            case "fairy" -> {
                defender.isNoEffectDefending.add("dragon");
                defender.isSuperDefending.addAll(Arrays.asList("fighting", "bug", "dark"));
                defender.isWeakDefending.addAll(Arrays.asList("poison", "steel"));
            }
            default -> System.out.println("Invalid type.");
        }
        ArrayList<String>[] nonStandards = new ArrayList[]{this.isSuperDefending, this.isWeakDefending, this.isNoEffectDefending};
        deduceStandardDamageFrom(nonStandards);
    }

    public void getDefendEffectiveness(Defender defender) {

        getSingleDefenseEffectiveness(defender, defender.type1);
        if (this.type2 != null) {
            getSingleDefenseEffectiveness(defender, defender.type2);
        }

        allTypeGrades = new ArrayList[]{this.isNoEffectDefending, this.isExtremeDefending, this.isSuperDefending,
                this.isStandardDefending, this.isWeakDefending, this.isIneffectiveDefending};
        ArrayList<String>[] possibleDoubles = new ArrayList[]{this.isSuperDefending, this.isWeakDefending, this.isStandardDefending};

        // based on the distribution of a type, we can determine the relative effectiveness it has against the defender's type
        // possible effectiveness with two types are 0, 0.25, 0.5, 1, 2, 4
        for (String type : Main.types) {
            // if a type is immune to another, a second type does not change that
            if (this.isNoEffectDefending.contains(type)) {
                for (ArrayList<String> gradeList : possibleDoubles) { //so we find where the second type was placed and remove it
                    if (gradeList.contains(type))
                        gradeList.remove(type);
                }
            }
            else if (this.isSuperDefending.contains(type) & this.isStandardDefending.contains(type)) // 2*1=2
                this.isStandardDefending.remove(type);
            else if (this.isSuperDefending.contains(type) & this.isWeakDefending.contains(type)) { // 2*0.5=1
                this.isSuperDefending.remove(type);
                this.isWeakDefending.remove(type);
                this.isStandardDefending.add(type);
            }
            else if (this.isStandardDefending.contains(type) & this.isWeakDefending.contains(type)){ // 1*0.5=0.5
                this.isStandardDefending.remove(type);
            }
            else {
                for (ArrayList<String> gradeList : possibleDoubles) {
                    checkForDouble(type, gradeList);
                }
            }
        }
    }

    public void deduceStandardDamageFrom(ArrayList<String>[] nonStandards) {
        ArrayList<String> allNonStandardDamage = new ArrayList<>();
        for (ArrayList<String> nonStandard: nonStandards) { //creates one list from the others
            allNonStandardDamage.addAll(nonStandard);
        }
        // Each type can only occur once when passed into getSingleDefenseEffectiveness()
        // Thus, if a type is not in any of the nonStandards, it must be Standard
        for (String type : Main.types) {
            if (!allNonStandardDamage.contains(type))
                this.isStandardDefending.add(type);
        }
    }

    public boolean checkForDouble(String type, ArrayList<String> from) {

        if (Collections.frequency(from, type) == 2) {
            if (!(from == this.isStandardDefending)) {
                if (from == this.isSuperDefending) {  // 2*2=4
                    this.isExtremeDefending.add(type);
                }
                else { // 0.5*0.5=0.25
                    this.isIneffectiveDefending.add(type);
                }
                from.remove(type);
                from.remove(type); //need to remove both of them
            }
            else { // 1*1=1
                this.isStandardDefending.remove(type); //need to leave one
            }
            return true;
        }
        else {
            return false;
        }
    }

    public String getPhrase(ArrayList<String> effectivenessArray){
        String phrase = "That wasn't a phrase.";
        if (effectivenessArray == this.isNoEffectDefending)
            phrase = "is literally not even effected by            ";
        else if (effectivenessArray == this.isExtremeDefending)
            phrase = "is EXTREMELY Effective defending against     ";
        else if (effectivenessArray == this.isSuperDefending)
            phrase = "is Super Effective defending against         ";
        else if (effectivenessArray == this.isStandardDefending)
            phrase = "takes Standard Damage from                   ";
        else if (effectivenessArray == this.isWeakDefending)
            phrase = "is Weak defending against                    ";
        else if (effectivenessArray == this.isIneffectiveDefending)
            phrase = "is EXTREMELY BAD defending against           ";
        return phrase;
    }
}


/*
resources: https://pokemondb.net/type/dual
 */
