package controller;

import model.Reservation;
import persistence.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservationController {

    private final ReservationRepository repository;

    public ReservationController(ReservationRepository repository) {
        this.repository = repository;
    }

    // US-4: Retrieve reservations for a selected space and date
    public List<Reservation> getReservationsByDay(
            String spaceName,
            LocalDate date
    ) {
        return repository.findReservationsBySpaceAndDate(spaceName, date);
    }

    // US-5: Determine whether a time period is reserved
    public boolean isTimeReserved(
            String spaceName,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime
    ) {
        List<Reservation> reservations =
                repository.findReservationsBySpaceAndDate(spaceName, date);

        for (Reservation reservation : reservations) {
            if (startTime.isBefore(reservation.getEndTime())
                    && endTime.isAfter(reservation.getStartTime())) {
                return true;
            }
        }

        return false;
    }

    // US-5: Determine whether a time period is available
    public boolean isTimeAvailable(
            String spaceName,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime
    ) {
        return !isTimeReserved(
                spaceName,
                date,
                startTime,
                endTime
        );
    }
}