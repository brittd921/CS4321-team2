package controller;

import model.Reservation;
import persistence.ReservationRepository;

import java.time.LocalDate;
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
}