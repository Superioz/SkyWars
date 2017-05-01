package de.quicklp.skywars.utils;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Code from https://github.com/Superioz/MooProject
 */
public class StringUtil {

    private static final Pattern SIMPLE_PLACEHOLER_REGEX = Pattern.compile("\\{[0-9]*}");
    private static final Pattern FORWARDING_PLACEHOLER_REGEX = Pattern.compile("\\{[a-zA-Z\\-_]*}");
    private static final Pattern DECIDING_PLACEHOLDER_REGEX = Pattern.compile("\\{\"[^\"]*\"\\|\"[^\"]*\"}");
    private static final Pattern KEY_PLACEHOLER_REGEX = Pattern.compile("%[a-zA-Z]*%");
    private static final Pattern REPLACE_REGEX = Pattern.compile(SIMPLE_PLACEHOLER_REGEX
            + "|" + FORWARDING_PLACEHOLER_REGEX
            + "|" + DECIDING_PLACEHOLDER_REGEX
            + "|" + KEY_PLACEHOLER_REGEX);

    /**
     * Formats given text by replacing all placeholders with given replacements<br>
     * If the placeholder contains a "abc-defg-hij" key then use the function
     * to search for a string which fits to this key.<br>
     * Can be used for {@link de.quicklp.skywars.config.PropertiesConfig}
     *
     * @param text            The text
     * @param fetchUnknownKey The function if the placeholder contains a key for the property file
     * @param replacements    The replacements
     * @return The formatted string
     */
    public static String format(String text, Function<String, String> fetchUnknownKey, Object... replacements) {
        // if there are no replacements, just return the text
        if(replacements.length == 0) return text;

        // get all placeholders from the text inside a HashSet (no duplicates)
        // after getting the placeholders put them into a list (for sorting)
        // order them after this system: {0}, {1} first and then the others chronologically
        LinkedHashSet<String> placeHoldersSet = new LinkedHashSet<>();
        placeHoldersSet.addAll(StringUtil.find(REPLACE_REGEX.pattern(), text));
        List<String> placeHolders = new ArrayList<>(placeHoldersSet);
        placeHolders.sort((o1, o2) -> {
            boolean integerOrder1 = SIMPLE_PLACEHOLER_REGEX.matcher(o1).matches();
            boolean integerOrder2 = SIMPLE_PLACEHOLER_REGEX.matcher(o2).matches();

            // if both strings look like {x=number}
            if(integerOrder1 && integerOrder2) {
                return Integer.valueOf(o1.replaceAll("[{}]", ""))
                        .compareTo(Integer.valueOf(o2.replaceAll("[{}]", "")));
            }
            // if one string looks like {x=number}
            if(integerOrder1 || integerOrder2) {
                return integerOrder1 ? -1 : integerOrder2 ? 1 : 0;
            }
            return 0;
        });

        // replace the placeholders
        for(int i = 0; i < replacements.length; i++) {
            if(i >= placeHolders.size()) break;
            String placeHolder = placeHolders.get(i);
            Object replacement = replacements[i];

            // if the placeHolder contains an forwarding key
            if(FORWARDING_PLACEHOLER_REGEX.matcher(placeHolder).matches()
                    && fetchUnknownKey != null) {
                replacement = format(
                        fetchUnknownKey.apply(placeHolder.replaceAll("[{}]", "")),
                        fetchUnknownKey,
                        replacement instanceof List ? ((List) replacement).toArray() : replacement
                );
            }
            if(DECIDING_PLACEHOLDER_REGEX.matcher(placeHolder).matches()) {
                List<String> parts = StringUtil.find("\"[^\"]*\"", placeHolder);

                if(replacement instanceof Boolean) {
                    replacement = parts.get((Boolean) replacement ? 0 : 1).replaceAll("[\"]", "");
                }
                else if(replacement instanceof List) {
                    List l = (List) replacement;
                    Object key;

                    if(l.size() > 1 && ((key = l.get(0)) instanceof Boolean)) {
                        l = l.subList(1, l.size());
                        replacement = format(parts.get((Boolean) key ? 0 : 1).replaceAll("[\"]", ""), l.toArray());
                    }
                }
            }
            text = text.replace(placeHolder, replacement + "");
        }
        return text;
    }

    public static String format(String text, Object... replacements) {
        return format(text, null, replacements);
    }

    /**
     * Find matches in string from regex as filter
     *
     * @param regex  The regex
     * @param string The string to search in
     * @return The result as matches
     */
    public static List<String> find(Pattern regex, String string) {
        List<String> listMatches = new ArrayList<>();
        Matcher matcher = regex.matcher(string);

        while(matcher.find()){
            listMatches.add(matcher.group());
        }
        return listMatches;
    }

    public static List<String> find(String regex, String string) {
        return find(Pattern.compile(regex), string);
    }

    /**
     * Uses google#Joiner to join given seperator into given objects
     *
     * @param seperator The seperator
     * @param objects   The objects
     * @return The successful as string
     */
    public static String join(String seperator, Object... objects) {
        List<String> l = new ArrayList<>();
        for(Object o : objects) {
            String s = "null";
            if(o != null) {
                s = o.toString();
            }
            l.add(s);
        }
        return Joiner.on(seperator).join(l);
    }

}
