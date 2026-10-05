package persistence;

import model.Reservation;
import model.Space;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationFilePersistence {

    private final Path filePath;

    public ReservationFilePersistence(String fileName) {
        this.filePath = Path.of(fileName);
    }

    public void saveReservations(List<Reservation> reservations) {

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {

            for (Reservation reservation : reservations) {

                Space space = reservation.getSpace();

                String line =
                        safe(space.getId()) + "|"
                                + safe(space.getName()) + "|"
                                + safe(space.getBuilding()) + "|"
                                + space.getCapacity() + "|"
                                + safe(space.getDescription()) + "|"
                                + reservation.getDate() + "|"
                                + reservation.getStartTime() + "|"
                                + reservation.getEndTime();

                writer.write(line);
                writer.newLine();
            }

        } catch (IOException exception) {
            throw new RuntimeException(
                    "Unable to save reservation data.",
                    exception
            );
        }
    }

    public List<Reservation> loadReservations() {

        List<Reservation> reservations = new ArrayList<>();

        if (!Files.exists(filePath)) {
            return reservations;
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split("\\|", -1);

                if (parts.length != 8) {
                    continue;
                }

                String spaceId = parts[0];
                String spaceName = parts[1];
                String building = parts[2];
                int capacity = Integer.parseInt(parts[3]);
                String description = parts[4];

                LocalDate date = LocalDate.parse(parts[5]);
                LocalTime startTime = LocalTime.parse(parts[6]);
                LocalTime endTime = LocalTime.parse(parts[7]);

                Space space =
                        new Space(
                                spaceId,
                                spaceName,
                                building,
                                capacity,
                                description
                        );

                Reservation reservation =
                        new Reservation(
                                space,
                                date,
                                startTime,
                                endTime
                        );

                reservations.add(reservation);
            }

        } catch (IOException | NumberFormatException exception) {
            throw new RuntimeException(
                    "Unable to load reservation data.",
                    exception
            );
        }

        return reservations;
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}