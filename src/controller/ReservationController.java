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

    public String createReservation(
            Space space,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime
    ) {
        String validationResult =
                validateReservation(space, date, startTime, endTime);

        if (!validationResult.equals("VALID")) {
            return validationResult;
        }

        Reservation reservation =
                new Reservation(space, date, startTime, endTime);

        repository.addReservation(reservation);

        return "Reservation created successfully.";
    }

    // US-7: Retrieve all reservations
    public List<Reservation> getAllReservations() {
        return repository.getAllReservations();

    }

    // US-8: Check for conflicts while ignoring the reservation being modified
    public boolean hasReservationConflictExceptCurrent(
            Reservation currentReservation,
            LocalDate newDate,
            LocalTime newStartTime,
            LocalTime newEndTime
    ) {
        List<Reservation> reservations =
                repository.findReservationsBySpaceAndDate(
                        currentReservation.getSpace().getName(),
                        newDate
                );

        for (Reservation reservation : reservations) {

            // Ignore the reservation currently being modified
            if (reservation == currentReservation) {
                continue;
            }

            if (newStartTime.isBefore(reservation.getEndTime())
                    && newEndTime.isAfter(reservation.getStartTime())) {
                return true;
            }
        }

        return false;
    }

    // US-8: Modify an existing reservation
    public String modifyReservation(
            Reservation reservation,
            LocalDate newDate,
            LocalTime newStartTime,
            LocalTime newEndTime
    ) {

        if (reservation == null
                || newDate == null
                || newStartTime == null
                || newEndTime == null) {
            return "Missing required reservation information.";
        }

        if (!newEndTime.isAfter(newStartTime)) {
            return "End time must be after start time.";
        }

        LocalDateTime newReservationStart =
                LocalDateTime.of(newDate, newStartTime);

        if (newReservationStart.isBefore(LocalDateTime.now())) {
            return "Reservation date and time cannot be in the past.";
        }

        if (hasReservationConflictExceptCurrent(
                reservation,
                newDate,
                newStartTime,
                newEndTime
        )) {
            return "Reservation conflicts with an existing reservation.";
        }

        boolean updated =
                repository.updateReservation(
                        reservation,
                        newDate,
                        newStartTime,
                        newEndTime
                );

        if (!updated) {
            return "Reservation could not be found.";
        }

        return "Reservation updated successfully.";
    }
}