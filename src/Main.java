import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static String[] types = {"normal", "fire", "water", "grass", "electric", "ice", "fighting", "poison", "ground", "flying",
            "psychic", "bug", "rock", "ghost", "dark", "dragon", "steel", "fairy"};

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Attacker (a)  or Defender(d)? ");
        char position = scanner.nextLine().toLowerCase().charAt(0); //convert input to lowercase char for efficiency

        switch (position) {
            case 'a' -> {
                System.out.println("What type of move are you using?: ");
                String aType = scanner.nextLine().toLowerCase();
                if (!verifyTypeInput(aType)) { //end program if type is not valid; inform user
                    System.out.println(aType + " is not a valid Pokemon Type. Please try again.");
                } else {
                    Attacker attacker = new Attacker();
                    attacker.type = aType; //the switch in attackType.getAttackEffectiveness needs this to be initialized
                    //get the attack grades for the given type
                    Attacker.getAttackEffectiveness(attacker); //static function

                    /* OUTPUT */
                    //formatting the type string, giving it type-based color and capitalizing the first letter
                    String formattedType = Formats.getTypeFormat(aType).format(aType.substring(0,1).toUpperCase() + aType.substring(1));
                    System.out.println(formattedType);
                    for (ArrayList<String> attackGrade : attacker.allTypeGrades) { //all attack grades including relevant phrasing
                        String phrase = attacker.getPhrase(attackGrade);
                        if (attackGrade.isEmpty())
                            System.out.println(phrase + "...nothing"); //empty collections
                        else {
                            String[] convertedGrades = attackGrade.toArray(new String[attackGrade.size()]);
                            String [] formattedAttackGrade = Formats.getAllColors(convertedGrades);
                            System.out.println(phrase + Arrays.toString(formattedAttackGrade)); //populated collections
                        }
                    }
                }
            }
            case 'd' -> {
                // When defending, a Pokémon can have 1 or 2 types, so it is a bit more complicated
                System.out.println("What type(s) does the defending Pokemon have? (i.e. 'bug, flying'): ");
                String dType = scanner.nextLine().toLowerCase();
                String[] parsedString;
                ArrayList<String> parsedTypes = new ArrayList<>();

                Defender defender = new Defender();
                if (!dType.contains(" ")) {
                    defender.type1 = dType;
                }
                else {
                    parsedString = dType.split(", "); //expecting user to use the format we suggest
                    for (String type : parsedString) {
                        if (!verifyTypeInput(type)) { //if they don't, we ask them to try again
                            System.out.println(type + " could not be parsed as valid Pokemon Types. Please try again.");
                        }
                        parsedTypes.add(type);
                    }
                    defender.type1 = parsedString[0]; //after types have been verified and parsed, they are assigned to Defender
                    defender.type2 = parsedString[1];
                }

                defender.getDefendEffectiveness(defender);

                /* OUTPUT */
                //formatting the type string, giving it type-based color and capitalizing the first letter
                //again, the approach is a bit different since the defender can have 1 or 2 types
                String formattedType;
                ArrayList<String> formattedTypes = new ArrayList<>();
                if (parsedTypes.isEmpty()){ //checks if there is 1 or 2 types
                    formattedType = formatTypeInputForOutput(dType);
                    System.out.println(formattedType);
                }
                else {
                    for (String type : parsedTypes) {
                        formattedType = formatTypeInputForOutput(type);
                        formattedTypes.add(formattedType);
                    }
                    System.out.println(formattedTypes.toArray()[0] + " and " + formattedTypes.toArray()[1]);
                }

                formatTypeGradesForDefenseOutput(defender);

            }
            case 's' -> { //ah, you found the secret stats code and wish to run it
                System.out.println("What do you want to know?");
                String input = scanner.nextLine().toLowerCase();

                if (input.equals("all combos")){
                    ArrayList<ArrayList<String>> allCombos = Stats.getAllDefenseCombos();
                    for (ArrayList<String> typeCombo : allCombos) {
                        Defender defender = new Defender();
                        defender.type1 = typeCombo.get(0);
                        defender.type2 = typeCombo.get(1);
                        defender.getDefendEffectiveness(defender);

                        /*OUTPUT*/
                        String formattedType;
                        ArrayList<String> formattedTypes = new ArrayList<>();

                        for (String type : typeCombo) {
                            formattedType = formatTypeInputForOutput(type);
                            formattedTypes.add(formattedType);
                        }
                        System.out.println(formattedTypes.toArray()[0] + " and " + formattedTypes.toArray()[1]);

                        formatTypeGradesForDefenseOutput(defender);
                    }
                }
                else if (input.equals("best defender")) {
                    Defender bestDefender = Stats.getBestDefender();

                    /*OUTPUT*/
                    String[] nonFormattedTypes = {bestDefender.type1, bestDefender.type2};
                    ArrayList<String> formattedTypes = new ArrayList<>();
                    for (String type : nonFormattedTypes) {
                        String formattedType = formatTypeInputForOutput(type);
                        formattedTypes.add(formattedType);
                    }
                    System.out.println(formattedTypes.toArray()[0] + " and " + formattedTypes.toArray()[1]);
                    formatTypeGradesForDefenseOutput(bestDefender);
                }

            }
            default ->
                    System.out.println("Please retry. Choose either 'a' or 'd' in order for the PokeTypeEngine to work properly.");
        }
    }

    public static boolean verifyTypeInput(String type){ // checks user type input against known types
        List<String> typesList = Arrays.asList(types);
        return typesList.contains(type);
    }

    //takes the type the user input, capitalizes the first letter, and colorizes it
    public static String formatTypeInputForOutput(String type){
        String formattedType = Formats.getTypeFormat(type).format(type.substring(0, 1).toUpperCase() + type.substring(1));
        return formattedType;
    }

    //this formats the damage grades, gets phrases, and prints things out
    //couldn't figure out how to make this take either a defender or attacker
    public static void formatTypeGradesForDefenseOutput(Defender defender){
        for (ArrayList<String> defendGrade : defender.allTypeGrades) {
            String phrase = defender.getPhrase(defendGrade);
            if (defendGrade.isEmpty())
                System.out.println(phrase + "nothing...");
            else {
                String[] convertedGrades = defendGrade.toArray(new String[defendGrade.size()]);
                String[] formattedGrades = Formats.getAllColors(convertedGrades);
                System.out.println(phrase + Arrays.toString(formattedGrades));
            }
        }
    }
}


//need to add a Pokemon lookup for type...since usually you don't know what the type of te defending Pokemon are
//I do like the format better than the chart

/*
Assumptions
Generally, a Trainer will choose to have a Pokemon learn moves of the same type. But they can also have moves of different
types and it can also be helpful to have moves that are strong against the Pokémon's weaknesses.

As such, it is particularly useful to know a Pokemon's offensive strengths and defensive weaknesses.

It is common to want to know the strengths and weaknesses of both the attacking and defending Pokemon.

Usually the problem is you don't know what type the defending Pokemon is...

Ideas
The goal is to take an input of two types and output the strengths/weaknesses as both Attacker and Defender

Another idea is to just take a pokemon name, which can then be looked-up on Pokemon DB. It's type as well as bass stats
could be scraped from there.

From there I may even decide to scrape the data and create my own db, if more performant than checking the Pokemon Db.
 */