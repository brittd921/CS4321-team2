package persistence;

import model.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    // US-4: Find reservations for a selected space and date
    public List<Reservation> findReservationsBySpaceAndDate(
            String spaceName,
            LocalDate date
    ) {
        List<Reservation> result = new ArrayList<>();

        for (Reservation reservation : reservations) {
            if (reservation.getSpace().getName().equalsIgnoreCase(spaceName)
                    && reservation.getDate().equals(date)) {
                result.add(reservation);
            }
        }

        result.sort(
                Comparator.comparing(Reservation::getStartTime)
        );

        return result;
    }

    // US-7: Retrieve all reservations sorted by date and start time
    public List<Reservation> getAllReservations() {

        List<Reservation> result =
                new ArrayList<>(reservations);

        result.sort(
                Comparator.comparing(Reservation::getDate)
                        .thenComparing(Reservation::getStartTime)
        );

        return result;
    }
}