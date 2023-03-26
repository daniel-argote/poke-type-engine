import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;

import java.util.ArrayList;


public class Formats {

    public static AnsiFormat typeTextColor;

    public static AnsiFormat getTypeFormat(String type) {
        switch (type) {
            case "normal" -> {
                typeTextColor = new AnsiFormat(Attribute.TEXT_COLOR(168,167,122));
            }
            case "fire" -> {
                typeTextColor = new AnsiFormat(Attribute.TEXT_COLOR(238,129,48));
            }
            case "water" -> {
                typeTextColor = new AnsiFormat(Attribute.TEXT_COLOR(99,144,240));
            }
            case "grass" -> {
                typeTextColor = new AnsiFormat(Attribute.TEXT_COLOR(122,199,76));
            }
            case "electric" -> {
                typeTextColor = new AnsiFormat(Attribute.TEXT_COLOR(247,208,44));
            }
            case "ice" -> {
                typeTextColor = new AnsiFormat(Attribute.TEXT_COLOR(150,217,214));
            }
            case "fighting" -> {
                typeTextColor = new AnsiFormat(Attribute.TEXT_COLOR(194,46,40));
            }
            case "poison" -> {
                typeTextColor = new AnsiFormat(Attribute.TEXT_COLOR(163,62,161));
            }
            case "ground" -> {
                typeTextColor = new AnsiFormat(Attribute.TEXT_COLOR(226,191,101));
            }
            case "flying" -> {
                typeTextColor = new AnsiFormat(Attribute.TEXT_COLOR(169,143,243));
            }
            case "psychic" -> {
                typeTextColor = new AnsiFormat(Attribute.TEXT_COLOR(249,85,135));
            }
            case "bug" -> {
                typeTextColor = new AnsiFormat(Attribute.TEXT_COLOR(166,185,26));
            }
            case "rock" -> {
                typeTextColor = new AnsiFormat(Attribute.TEXT_COLOR(182,161,54));
            }
            case "ghost" -> {
                typeTextColor = new AnsiFormat(Attribute.TEXT_COLOR(115,87,151));
            }
            case "dragon" -> {
                typeTextColor = new AnsiFormat(Attribute.TEXT_COLOR(111,53,252));
            }
            case "dark" -> {
                typeTextColor = new AnsiFormat(Attribute.TEXT_COLOR(112,87,70));
            }
            case "steel" -> {
                typeTextColor = new AnsiFormat(Attribute.TEXT_COLOR(183,183,206));
            }
            case "fairy" -> {
                typeTextColor = new AnsiFormat(Attribute.TEXT_COLOR(214,133,173));
            }
            default -> System.out.println("Invalid type.");
        }
        return typeTextColor;
    }

    public static String [] getAllColors(String[] types){
        ArrayList<String> typesAsList = new ArrayList<>();
        for (String type : types) {
            typesAsList.add(getTypeFormat(type).format(type));
        }
        String [] formattedTypes = new String[typesAsList.size()];
        typesAsList.toArray(formattedTypes);
        return formattedTypes;
    }

}
