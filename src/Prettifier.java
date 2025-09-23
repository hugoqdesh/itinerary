import java.io.*;
import java.util.*;

public class Prettifier {
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";

    public static void main(String[] args) {
        if(args.length == 1 && args[0].equals("-h")) {
            printUsage();
            return;
        }
        if (args.length != 3) {
            printUsage();
            return;
        }

        String inputPath = args[0];
        String outputPath = args[1];
        String airportLookupPath = args[2];

        File inputFile = new File(inputPath);
        File airportLookupFile = new File(airportLookupPath);

        if(!inputFile.exists()) {
            System.out.println(RED + "Input not found" + RESET);
            return;
        }
        if(!airportLookupFile.exists()) {
            System.out.println(RED + "Airport lookup not found" + RESET);
            return;
        }

        try {
            AirportLookup airportLookup = new AirportLookup(airportLookupPath);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            StringBuilder fileContent = new StringBuilder();

            if (!airportLookup.isValid()) {
                return;
            }

            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
            reader.close();

            String prettified = Formater.prettify(fileContent, airportLookup);

            BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));

            System.out.println(colorizeOutput(prettified));

            writer.write(prettified);
            writer.close();
        } catch (IOException e) {
            System.out.println("something went wrong");
        }
    }

    public static void printUsage() {
        System.out.println(PURPLE + "itinerary usage:" + RESET);
        System.out.println(CYAN + "$ java Prettifier.java ./input.txt ./output.txt ./airport-lookup.csv" + RESET);
    }

    private static String colorizeOutput(String content) {
        String colorized = content;

        // date colors
        colorized = colorized.replaceAll("(\\d{1,2} [A-Za-z]{3} \\d{4})", PURPLE + "$1" + RESET);

        // AM/PM timestamp colors
        colorized = colorized.replaceAll("(\\d{1,2}:\\d{2}(?:AM|PM)?)", CYAN + "$1" + RESET);
        colorized = colorized.replaceAll("(\\([+-]\\d{2}:\\d{2}\\)|\\(Z\\))", CYAN + "$1" + RESET);

        // T timestamp colors
        colorized = colorized.replaceAll("(T\\d+\\([^)]+\\))", CYAN + "$1" + RESET);

        return colorized;
    }
}
