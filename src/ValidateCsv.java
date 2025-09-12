import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ValidateCsv {
    private static final Map<String, String> data = new HashMap<>();

    public static void hasRequiredColumns(File lookupPath, String[] requiredColumns) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(lookupPath));

            String headerRow = reader.readLine();
            String[] headerColumns = headerRow.split(",");
            Set<String> columnCheck = new HashSet<>(Arrays.asList(headerColumns));

            for(String required : requiredColumns) {
                if(!columnCheck.contains(required)) {
                    System.out.println("Missing: " + required);
                    return;
                }
            }

            String line;
            while((line = reader.readLine()) != null) {
                String[] row = line.split(",", -1);

                for (String s : row) {
                    if (s == null || s.isEmpty()) {
                        System.out.println("Data malformed");
                    }
                }

                data.put(row[0], line);
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Airport lookup malformed");
        }
    }
}
