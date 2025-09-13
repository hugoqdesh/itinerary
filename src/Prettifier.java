import java.io.*;
import java.util.*;

public class Prettifier {
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
            System.out.println("Input not found");
            return;
        }
        if(!airportLookupFile.exists()) {
            System.out.println("Airport lookup not found");
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            StringBuilder fileContent = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
            writer.write(fileContent.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("something went wrong");
        }
    }

    public static void printUsage() {
        System.out.println("itinerary usage:");
        System.out.println("$ java Prettifier.java ./input.txt ./output.txt ./airport-lookup.csv");
    }
}
