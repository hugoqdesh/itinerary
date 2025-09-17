import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Formater {

    public static String prettify(StringBuilder fileContent, AirportLookup airportLookup) {
        String text = fileContent.toString();

        text = codeToCity(text, airportLookup);
        text = codeToName(text, airportLookup);
        text = dateTimes(text);
        text = cleanWhitespace(text);

        return text;
    }

    public static String codeToCity(String text, AirportLookup airportLookup) {
        Pattern iataCityPattern = Pattern.compile("\\*#[A-Z]{3}");
        Matcher iataCityMatcher = iataCityPattern.matcher(text);
        StringBuilder iataCityResult = new StringBuilder();

        while(iataCityMatcher.find()) {
            String group = iataCityMatcher.group();
            String code = group.substring(2);
            String city = airportLookup.getIataCity(code);

            if (city != null && !city.isEmpty()) {
                iataCityMatcher.appendReplacement(iataCityResult, Matcher.quoteReplacement(city));
            } else {
                iataCityMatcher.appendReplacement(iataCityResult, Matcher.quoteReplacement(group));
            }
        }
        iataCityMatcher.appendTail(iataCityResult);

        Pattern icaoCityPattern = Pattern.compile("\\*##[A-Z]{4}");
        Matcher icaoCityMatcher = icaoCityPattern.matcher(iataCityResult.toString());
        StringBuilder icaoCityResult = new StringBuilder();

        while(icaoCityMatcher.find()) {
            String group = icaoCityMatcher.group();
            String code = group.substring(3);
            String city = airportLookup.getIcaoCity(code);

            if (city != null && !city.isEmpty()) {
                icaoCityMatcher.appendReplacement(icaoCityResult, Matcher.quoteReplacement(city));
            } else {
                icaoCityMatcher.appendReplacement(icaoCityResult, Matcher.quoteReplacement(group));
            }
        }
        icaoCityMatcher.appendTail(icaoCityResult);

        return icaoCityResult.toString();
    }

    public static String codeToName(String text, AirportLookup airportLookup) {
        Pattern iataNamePattern = Pattern.compile("(?<!#)#[A-Z]{3}");
        Matcher iataNameMatcher = iataNamePattern.matcher(text);
        StringBuilder iataNameResult = new StringBuilder();

        while(iataNameMatcher.find()) {
            String group = iataNameMatcher.group();
            String code = group.substring(1);
            String name = airportLookup.getIataName(code);

            if (name != null && !name.isEmpty()) {
                iataNameMatcher.appendReplacement(iataNameResult, Matcher.quoteReplacement(name));
            } else {
                iataNameMatcher.appendReplacement(iataNameResult, Matcher.quoteReplacement(group));
            }
        }
        iataNameMatcher.appendTail(iataNameResult);

        Pattern icaoNamePattern = Pattern.compile("##[A-Z]{4}");
        Matcher icaoNameMatcher = icaoNamePattern.matcher(iataNameResult.toString());
        StringBuilder icaoNameResult = new StringBuilder();

        while(icaoNameMatcher.find()) {
            String group = icaoNameMatcher.group();
            String code = group.substring(2);
            String name = airportLookup.getIcaoName(code);

            if (name != null && !name.isEmpty()) {
                icaoNameMatcher.appendReplacement(icaoNameResult, Matcher.quoteReplacement(name));
            } else {
                icaoNameMatcher.appendReplacement(icaoNameResult, Matcher.quoteReplacement(group));
            }
        }
        icaoNameMatcher.appendTail(icaoNameResult);

        return icaoNameResult.toString();
    }

    public static String dateTimes(String text) {
        Pattern pattern = Pattern.compile("(D|T12|T24)\\(([^)]+)\\)");
        Matcher matcher = pattern.matcher(text);

        StringBuilder result = new StringBuilder();

        while(matcher.find()) {
            String format = matcher.group(1);
            String date = matcher.group(2);

            try {
                ZonedDateTime zone = ZonedDateTime.parse(date);
                String time;

                switch (format) {
                    case "D":
                        time = zone.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
                        break;
                    case "T12":
                        time = zone.format(DateTimeFormatter.ofPattern("hh:mma (XXX)"));
                        break;
                    case "T24":
                        time = zone.format(DateTimeFormatter.ofPattern("HH:mm (XXX)"));
                        break;
                    default:
                        time = matcher.group(0);
                        break;
                }

                matcher.appendReplacement(result, Matcher.quoteReplacement(time));

            } catch (DateTimeParseException e) {
                matcher.appendReplacement(result, Matcher.quoteReplacement(matcher.group(0)));
            }
        }
        matcher.appendTail(result);

        return result.toString();
    }

    public static String cleanWhitespace(String text) {
        text = text.replace("\\v", "\n");
        text = text.replace("\\f", "\n");
        text = text.replace("\\r", "\n");

        text = text.replace("[\\v\\f\\r]", "\n");
        text = text.replaceAll("\n{3,}","\n\n");

        return text.trim();
    }
}
