package test;

import controller.ReservationController;
import model.Reservation;
import model.Space;
import persistence.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ModifyReservationTest {

    public static void main(String[] args) {

        testSuccessfulModification();
        testConflictModification();
        testEndBeforeStart();
        testEndEqualToStart();
        testPastModification();
        testSelfConflictIgnored();

        System.out.println();
        System.out.println("All US-8 modification tests completed.");
    }

    private static void testSuccessfulModification() {

        ReservationRepository repository =
                new ReservationRepository();

        Space library =
                new Space(
                        "S001",
                        "Library",
                        "Main Building",
                        100,
                        "Large study and meeting space"
                );

        Reservation reservation =
                new Reservation(
                        library,
                        LocalDate.of(2026, 11, 10),
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 0)
                );

        repository.addReservation(reservation);

        ReservationController controller =
                new ReservationController(repository);

        String result =
                controller.modifyReservation(
                        reservation,
                        LocalDate.of(2026, 11, 11),
                        LocalTime.of(13, 0),
                        LocalTime.of(14, 0)
                );

        List<Reservation> reservations =
                repository.getAllReservations();

        Reservation updatedReservation =
                reservations.get(0);

        System.out.println("TEST 1 - SUCCESSFUL MODIFICATION");
        System.out.println(result);
        System.out.println(
                updatedReservation.getDate()
                        + " | "
                        + updatedReservation.getStartTime()
                        + " - "
                        + updatedReservation.getEndTime()
        );
        System.out.println();
    }

    private static void testConflictModification() {

        ReservationRepository repository =
                new ReservationRepository();

        Space library =
                new Space(
                        "S001",
                        "Library",
                        "Main Building",
                        100,
                        "Large study and meeting space"
                );

        Reservation reservation1 =
                new Reservation(
                        library,
                        LocalDate.of(2026, 11, 15),
                        LocalTime.of(9, 0),
                        LocalTime.of(10, 0)
                );

        Reservation reservation2 =
                new Reservation(
                        library,
                        LocalDate.of(2026, 11, 15),
                        LocalTime.of(11, 0),
                        LocalTime.of(12, 0)
                );

        repository.addReservation(reservation1);
        repository.addReservation(reservation2);

        ReservationController controller =
                new ReservationController(repository);

        String result =
                controller.modifyReservation(
                        reservation1,
                        LocalDate.of(2026, 11, 15),
                        LocalTime.of(11, 30),
                        LocalTime.of(12, 30)
                );

        System.out.println("TEST 2 - CONFLICT MODIFICATION");
        System.out.println(result);
        System.out.println();
    }

    private static void testEndBeforeStart() {

        ReservationRepository repository =
                new ReservationRepository();

        Space library =
                new Space(
                        "S001",
                        "Library",
                        "Main Building",
                        100,
                        "Large study and meeting space"
                );

        Reservation reservation =
                new Reservation(
                        library,
                        LocalDate.of(2026, 11, 20),
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 0)
                );

        repository.addReservation(reservation);

        ReservationController controller =
                new ReservationController(repository);

        String result =
                controller.modifyReservation(
                        reservation,
                        LocalDate.of(2026, 11, 20),
                        LocalTime.of(14, 0),
                        LocalTime.of(13, 0)
                );

        System.out.println("TEST 3 - END BEFORE START");
        System.out.println(result);
        System.out.println();
    }

    private static void testEndEqualToStart() {

        ReservationRepository repository =
                new ReservationRepository();

        Space library =
                new Space(
                        "S001",
                        "Library",
                        "Main Building",
                        100,
                        "Large study and meeting space"
                );

        Reservation reservation =
                new Reservation(
                        library,
                        LocalDate.of(2026, 11, 21),
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 0)
                );

        repository.addReservation(reservation);

        ReservationController controller =
                new ReservationController(repository);

        String result =
                controller.modifyReservation(
                        reservation,
                        LocalDate.of(2026, 11, 21),
                        LocalTime.of(14, 0),
                        LocalTime.of(14, 0)
                );

        System.out.println("TEST 4 - END EQUAL TO START");
        System.out.println(result);
        System.out.println();
    }

    private static void testPastModification() {

        ReservationRepository repository =
                new ReservationRepository();

        Space library =
                new Space(
                        "S001",
                        "Library",
                        "Main Building",
                        100,
                        "Large study and meeting space"
                );

        Reservation reservation =
                new Reservation(
                        library,
                        LocalDate.of(2026, 11, 25),
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 0)
                );

        repository.addReservation(reservation);

        ReservationController controller =
                new ReservationController(repository);

        String result =
                controller.modifyReservation(
                        reservation,
                        LocalDate.of(2025, 1, 1),
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 0)
                );

        System.out.println("TEST 5 - PAST MODIFICATION");
        System.out.println(result);
        System.out.println();
    }

    private static void testSelfConflictIgnored() {

        ReservationRepository repository =
                new ReservationRepository();

        Space library =
                new Space(
                        "S001",
                        "Library",
                        "Main Building",
                        100,
                        "Large study and meeting space"
                );

        Reservation reservation =
                new Reservation(
                        library,
                        LocalDate.of(2026, 12, 1),
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 0)
                );

        repository.addReservation(reservation);

        ReservationController controller =
                new ReservationController(repository);

        String result =
                controller.modifyReservation(
                        reservation,
                        LocalDate.of(2026, 12, 1),
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 0)
                );

        System.out.println("TEST 6 - SELF CONFLICT IGNORED");
        System.out.println(result);
        System.out.println();
    }
}