<div align="center">
  <h3 align="center">Itinerary Prettifier</h3>
</div>

## About The Project

A command-line tool that converts flight itineraries from administrative format to customer-friendly format.

## Getting Started

### Prerequisites

* Java 8 or higher
* Airport lookup CSV file with columns: name, iso_country, municipality, icao_code, iata_code, coordinates

### Installation

1. Clone the repo
   ```
   git clone https://github.com/hugoqdesh/itinerary.git
   cd itinerary
   ```
2. Compile Java code
   ```
   javac Prettifier.java
   ```
   
### Usage

```
java Prettifier.java input.txt output.txt airport-lookup.csv
```

### Example

* Input:
  ```
  Flight from #LAX to ##EGLL on D(2023-10-15T14:30+00:00)
  Departure: T12(2023-10-15T14:30+00:00) Arrival: T24(2025-10-16T06:30+01:00)
  ```

* Output:
  ```
  Flight from Los Angeles International Airport to London Heathrow Airport on 15 Oct 2025
  Departure: 02:30PM (+00:00) Arrival: 06:30 (+01:00)
  ```
