package src.test;

import controller.ReservationController;
import model.Reservation;
import model.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CancelReservationTest {

    private ReservationRepository repository;
    private ReservationController controller;

    private Space targetSpace;
    private LocalDate targetDate;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        repository = new ReservationRepository();
        controller = new ReservationController(repository);

        targetSpace = new Space(
                "S001",
                "Library",
                "Main Building",
                100,
                "Large study and meeting space"
        );

        targetDate = LocalDate.of(2026, 10, 10);

        reservation = new Reservation(
                targetSpace,
                targetDate,
                LocalTime.of(9, 0),
                LocalTime.of(10, 0)
        );

        repository.addReservation(reservation);
    }

    @Test
    @DisplayName("Existing reservation is removed when canceled")
    void testCancelReservationRemovesReservation() {

        controller.cancelReservation(reservation);

        List<Reservation> reservations =
                controller.getReservationsByDay(
                        "Library",
                        targetDate
                );

        assertFalse(reservations.contains(reservation));
    }

    @Test
    @DisplayName("Canceled reservation time becomes available again")
    void testCanceledReservationTimeBecomesAvailable() {

        controller.cancelReservation(reservation);

        boolean available = controller.isTimeAvailable(
                "Library" ,
                targetDate,
                LocalTime.of(9, 0),
                LocalTime.of(10, 0)
        );

        assertTrue(available);
    }

    @Test
    @DisplayName("Canceling one reservation does not remove other reservations")
    void testCancelDoesNotRemoveOtherReservations() {

        Reservation otherReservation = new Reservation(
                targetSpace,
                targetDate,
                LocalTime.of(11, 0),
                LocalTime.of(12, 0)
        );

        repository.addReservation(otherReservation);

        controller.cancelReservation(reservation);

        List<Reservation> reservations =
                controller.getReservationsByDay(
                        "Library",
                        targetDate
                );

        assertEquals(1, reservations.size());
        assertTrue(reservations.contains(otherReservation));
        assertFalse(reservations.contains(reservation));
    }
}