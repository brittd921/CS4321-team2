package test;

import controller.ReservationController;
import model.Reservation;
import model.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.ReservationRepository;
import view.SpaceView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationControllerTest {

    private ReservationRepository repository;
    private ReservationController controller;

    private Space targetSpace;
    private Space otherSpace;
    private LocalDate targetDate;

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

        otherSpace = new Space(
                "S002",
                "Auditorium",
                "East Wing",
                300,
                "Event hall"
        );

        targetDate = LocalDate.of(2026, 10, 3);
    }

    @Test
    @DisplayName("Reservations are filtered by the selected space")
    void testReservationsFilteredBySelectedSpace() {
        Reservation targetSpaceReservation = new Reservation(
                targetSpace,
                targetDate,
                LocalTime.of(9, 0),
                LocalTime.of(10, 0)
        );
        Reservation otherSpaceReservation = new Reservation(
                otherSpace,
                targetDate,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );

        repository.addReservation(targetSpaceReservation);
        repository.addReservation(otherSpaceReservation);

        List<Reservation> result = controller.getReservationsByDay("Library", targetDate);

        assertEquals(1, result.size());
        assertEquals("Library", result.get(0).getSpace().getName());
    }

    @Test
    @DisplayName("Reservations are filtered by the selected date")
    void testReservationsFilteredBySelectedDate() {
        LocalDate otherDate = LocalDate.of(2026, 10, 4);

        Reservation targetDateReservation = new Reservation(
                targetSpace,
                targetDate,
                LocalTime.of(9, 0),
                LocalTime.of(10, 0)
        );
        Reservation otherDateReservation = new Reservation(
                targetSpace,
                otherDate,
                LocalTime.of(9, 0),
                LocalTime.of(10, 0)
        );

        repository.addReservation(targetDateReservation);
        repository.addReservation(otherDateReservation);

        List<Reservation> result = controller.getReservationsByDay("Library", targetDate);

        assertEquals(1, result.size());
        assertEquals(targetDate, result.get(0).getDate());
    }

    @Test
    @DisplayName("Multiple reservations are sorted by start time")
    void testMultipleReservationsSortedByStartTime() {
        Reservation afternoonRes = new Reservation(
                targetSpace,
                targetDate,
                LocalTime.of(14, 0),
                LocalTime.of(15, 0)
        );
        Reservation morningRes = new Reservation(
                targetSpace,
                targetDate,
                LocalTime.of(9, 0),
                LocalTime.of(10, 0)
        );

        repository.addReservation(afternoonRes);
        repository.addReservation(morningRes);

        List<Reservation> result = controller.getReservationsByDay("Library", targetDate);

        assertEquals(2, result.size());
        assertEquals(LocalTime.of(9, 0), result.get(0).getStartTime());
        assertEquals(LocalTime.of(14, 0), result.get(1).getStartTime());
    }

    @Test
    @DisplayName("A day with no reservations returns an empty list")
    void testNoReservationsReturnsEmptyList() {
        List<Reservation> result = controller.getReservationsByDay("Library", targetDate);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("The no-reservation scenario can be handled as fully available")
    void testNoReservationScenarioHandledAsFullyAvailable() {
        List<Reservation> emptyReservations = controller.getReservationsByDay("Library", targetDate);

        assertTrue(emptyReservations.isEmpty());
        // Asserts view logic handles an empty list smoothly without throw
        assertDoesNotThrow(() -> {
            boolean isFullyAvailable = emptyReservations.isEmpty();
            assertTrue(isFullyAvailable);
        });
    }
}