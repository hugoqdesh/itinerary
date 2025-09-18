import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AirportLookup {

    private static final Map<String, String> iataName = new HashMap<>();
    private static final Map<String, String> iataCity = new HashMap<>();
    private static final Map<String, String> icaoName = new HashMap<>();
    private static final Map<String, String> icaoCity = new HashMap<>();

    public AirportLookup(String csvPath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvPath));

            String header = reader.readLine();
            String[] headerColumns = header.split(",");
            Set<String> columnCheck = new HashSet<>(Arrays.asList(headerColumns));

            String[] requiredColumns = {"name", "iso_country", "municipality", "icao_code" , "iata_code", "coordinates"};

            for(String col : requiredColumns) {
                if(!columnCheck.contains(col)) {
                    System.out.println("Airport lookup malformed");
                    return;
                }
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",", -1);

                String name = row[0];
                String city = row[2];
                String icao = row[3];
                String iata = row[4];

                iataName.put(iata, name);
                iataCity.put(iata, city);
                icaoName.put(icao, name);
                icaoCity.put(icao, city);
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Airport lookup malformed");
        }
    }

    public String getIataName(String code) {
        return iataName.get(code);
    }

    public String getIataCity(String code) {
        return iataCity.get(code);
    }

    public String getIcaoName(String code) {
        return icaoName.get(code);
    }

    public String getIcaoCity(String code) {
        return icaoCity.get(code);
    }
}
