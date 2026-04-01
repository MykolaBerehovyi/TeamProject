package com.example.final_project;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static final String FILE_NAME = "cruises.txt";

    public static void saveCruise(Cruise cruise) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            writer.write(
                    cruise.getId() + "," +
                            cruise.getName() + "," +
                            cruise.getDestination() + "," +
                            cruise.getStartDate() + "," +
                            cruise.getEndDate() + "," +
                            cruise.getPrice() + "," +
                            cruise.getAvailableSeats() + "," +
                            cruise.getTotalSeats() + "\n"
            );
            System.out.println("cruise saved to file: " + cruise.getName());
        } catch (IOException e) {
            System.out.println("error while trying to save cruise: " + e.getMessage());
        }
    }

    public static List<Cruise> exportCruise() {
        List<Cruise> cruises = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("file " + FILE_NAME + " does not exist");
            return cruises;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                // if there empty lines, we skip it
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");

                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String destination = parts[2].trim();
                    LocalDate startDate = LocalDate.parse(parts[3].trim());
                    LocalDate endDate = LocalDate.parse(parts[4].trim());
                    double price = Double.parseDouble(parts[5].trim());
                    int availableSeats = Integer.parseInt(parts[6].trim());
                    int totalSeats = Integer.parseInt(parts[7].trim());

                    Cruise cruise = new Cruise(id, name, destination, startDate, endDate, price, totalSeats, availableSeats);
                    cruises.add(cruise);

                } catch (Exception e) {
                    System.out.println("error " + lineNumber + ": " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println("error while reading file: " + e.getMessage());
        }
        return cruises;
    }
}
