import java.io.*;
import java.util.*;

public class Prettifier {
    public static void main(String[] args) {
        if(args.length == 1 && args[0].equals("-h")) {
            System.out.println("itinerary usage:");
            System.out.println("$ java Prettifier.java ./input.txt ./output.txt ./airport-lookup.csv");
            return;
        }

        if (args.length != 3) {
            System.out.println("itinerary usage:");
            System.out.println("$ java Prettifier.java ./input.txt ./output.txt ./airport-lookup.csv");
            return;
        }

        String inputPath = args[0];
        String outputPath = args[1];
        String lookupPath = args[2];

        File inputFile = new File(inputPath);
        File lookupFile = new File(lookupPath);

        if(!inputFile.exists()) {
            System.out.println("Input not found");
            return;
        }
        if(!lookupFile.exists()) {
            System.out.println("Airport lookup not found");
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();

            String content = sb.toString();

            BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            System.out.println("Something went wrong reading input file");
        }

        String[] requiredColumns = {"name", "iso_country", "municipality", "icao_code", "iata_code", "coordinates"};
        ValidateCsv.hasRequiredColumns(lookupFile, requiredColumns);
    }
}
