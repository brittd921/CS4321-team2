package controller;

import model.Reservation;
import model.Space;
import persistence.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public boolean hasReservationConflict(
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

    // US-6: Validate reservation information
    public String validateReservation(
            Space space,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime
    ) {

        // Required information must be present
        if (space == null
                || date == null
                || startTime == null
                || endTime == null) {
            return "Missing required reservation information.";
        }

        // End time must be after start time
        if (!endTime.isAfter(startTime)) {
            return "End time must be after start time.";
        }

        // Reservation cannot be in the past
        LocalDateTime reservationStart =
                LocalDateTime.of(date, startTime);

        if (reservationStart.isBefore(LocalDateTime.now())) {
            return "Reservation date and time cannot be in the past.";
        }
        if (hasReservationConflict(
                space.getName(),
                date,
                startTime,
                endTime
        )) {
            return "Reservation conflicts with an existing reservation.";
        }

        return "VALID";
    }
}