import java.util.ArrayList;
import java.util.Arrays;

public class Attacker extends BattlePosition{
    String type;
    ArrayList<String> isSuperAttacking = new ArrayList<>();
    ArrayList<String> isStandardAttacking = new ArrayList<>();
    ArrayList<String> isWeakAttacking = new ArrayList<>();
    ArrayList<String> isNoEffectAttacking = new ArrayList<>();

    public static void getAttackEffectiveness(Attacker attacker) {

        /*here's an idea: use dictonary instead of switch...then I can have like a Super Effective dictonary with all of
        key:value pairs combos that fall under that catgory
         */

        switch (attacker.type) {
            case "normal" -> {
                attacker.isWeakAttacking.addAll(Arrays.asList("rock", "steel"));
                attacker.isNoEffectAttacking.add("ghost");
            }
            case "fire" -> {
                attacker.isSuperAttacking.addAll(Arrays.asList("grass", "ice", "bug", "steel"));
                attacker.isWeakAttacking.addAll(Arrays.asList("fire", "water", "rock", "dragon"));
            }
            case "water" -> {
                attacker.isSuperAttacking.addAll(Arrays.asList("fire", "ground", "rock"));
                attacker.isWeakAttacking.addAll(Arrays.asList("water", "grass", "dragon"));
            }
            case "grass" -> {
                attacker.isSuperAttacking.addAll(Arrays.asList("water", "ground", "rock"));
                attacker.isWeakAttacking.addAll(Arrays.asList("fire", "grass", "poison", "flying", "bug", "dragon", "steel"));
            }
            case "electric" -> {
                attacker.isSuperAttacking.addAll(Arrays.asList("water", "flying"));
                attacker.isWeakAttacking.addAll(Arrays.asList("electric", "grass", "dragon"));
                attacker.isNoEffectAttacking.add("ground");
            }
            case "ice" -> {
                attacker.isSuperAttacking.addAll(Arrays.asList("grass", "ground", "flying", "dragon"));
                attacker.isWeakAttacking.addAll(Arrays.asList("fire", "water", "ice", "steel"));
            }
            case "fighting" -> {
                attacker.isSuperAttacking.addAll(Arrays.asList("normal", "ice", "rock", "dark", "steel"));
                attacker.isWeakAttacking.addAll(Arrays.asList("poison", "flying", "psychic", "bug", "fairy"));
                attacker.isNoEffectAttacking.add("ghost");
            }
            case "poison" -> {
                attacker.isSuperAttacking.addAll(Arrays.asList("grass", "fairy"));
                attacker.isWeakAttacking.addAll(Arrays.asList("poison", "ground", "rock", "ghost"));
                attacker.isNoEffectAttacking.add("steel");
            }
            case "ground" -> {
                attacker.isSuperAttacking.addAll(Arrays.asList("fire", "electric", "poison", "rock", "steel"));
                attacker.isWeakAttacking.addAll(Arrays.asList("grass", "bug"));
                attacker.isNoEffectAttacking.add("flying");
            }
            case "flying" -> {
                attacker.isSuperAttacking.addAll(Arrays.asList("grass", "fighting", "bug"));
                attacker.isWeakAttacking.addAll(Arrays.asList("electric", "rock", "steel"));
            }
            case "psychic" -> {
                attacker.isSuperAttacking.addAll(Arrays.asList("fighting", "poison"));
                attacker.isWeakAttacking.addAll(Arrays.asList("psychic", "steel"));
                attacker.isNoEffectAttacking.add("dark");
            }
            case "bug" -> {
                attacker.isSuperAttacking.addAll(Arrays.asList("grass", "psychic", "dark"));
                attacker.isWeakAttacking.addAll(Arrays.asList("fire", "fighting", "poison", "flying", "ghost", "steel", "fairy"));
            }
            case "rock" -> {
                attacker.isSuperAttacking.addAll(Arrays.asList("fire", "ice", "flying", "bug"));
                attacker.isWeakAttacking.addAll(Arrays.asList("fighting", "ground", "steel"));
            }
            case "ghost" -> {
                attacker.isSuperAttacking.addAll(Arrays.asList("psychic", "ghost"));
                attacker.isWeakAttacking.add("dark");
                attacker.isNoEffectAttacking.add("normal");
            }
            case "dragon" -> {
                attacker.isSuperAttacking.add("dragon");
                attacker.isWeakAttacking.add("steel");
                attacker.isNoEffectAttacking.add("fairy");
            }
            case "dark" -> {
                attacker.isSuperAttacking.addAll(Arrays.asList("psychic", "ghost"));
                attacker.isWeakAttacking.addAll(Arrays.asList("fighting", "dark", "fairy"));
            }
            case "steel" -> {
                attacker.isSuperAttacking.addAll(Arrays.asList("ice", "rock", "fairy"));
                attacker.isWeakAttacking.addAll(Arrays.asList("fire", "water", "electric", "steel"));
            }
            case "fairy" -> {
                attacker.isSuperAttacking.addAll(Arrays.asList("fighting", "dragon", "dark"));
                attacker.isWeakAttacking.addAll(Arrays.asList("fire", "poison", "steel"));
            }
            default -> System.out.println("Invalid type.");
        }
        ArrayList<String>[] nonStandards = new ArrayList[]{attacker.isSuperAttacking, attacker.isWeakAttacking,
                attacker.isNoEffectAttacking};
        deduceStandardDamageFrom(nonStandards, attacker);
        attacker.allTypeGrades = new ArrayList[]{attacker.isSuperAttacking, attacker.isWeakAttacking,
                attacker.isStandardAttacking, attacker.isNoEffectAttacking};
    }

    // newer and better
    public static void deduceStandardDamageFrom(ArrayList[] nonStandards, Attacker attacker) {
        ArrayList<String> allNonStandardDamage = new ArrayList<>();
        for (ArrayList nonStandard: nonStandards) { //creates one list from the others
            allNonStandardDamage.addAll(nonStandard);
        }
        // Each type can only occur once when passed into getSingleDefenseEffectiveness()
        // Thus, if a type is not in any of the nonStandards, it must be Standard
        for (String type : Main.types) {
            if (!allNonStandardDamage.contains(type))
                attacker.isStandardAttacking.add(type);
        }
    }
// could probably make this a dictionary?
    public String getPhrase(ArrayList<String> effectivenessArray){
        String phrase = "That wasn't a phrase.";
        if (effectivenessArray == this.isSuperAttacking)
            phrase = "is Super Effective against     ";
        else if (effectivenessArray == this.isStandardAttacking)
            phrase = "does Standard Damage against   ";
        else if (effectivenessArray == this.isWeakAttacking)
            phrase = "is Weak against                ";
        else if (effectivenessArray == this.isNoEffectAttacking)
            phrase = "has No Effect against          ";
    return phrase;
    }
}
