package test;

import controller.ReservationController;
import model.Reservation;
import model.Space;
import persistence.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CancelReservationControllerTest {

    public static void main(String[] args) {

        ReservationRepository repository = new ReservationRepository();
        ReservationController controller =
                new ReservationController(repository);

        Space library = new Space(
                "S001",
                "Library",
                "Main Building",
                100,
                "Large study and meeting space"
        );

        LocalDate date = LocalDate.now().plusDays(1);

        Reservation reservation = new Reservation(
                library,
                date,
                LocalTime.of(9, 0),
                LocalTime.of(10, 0)
        );

        // Add reservation
        repository.addReservation(reservation);

        // Check reservations before cancellation
        List<Reservation> beforeCancel =
                controller.getReservationsByDay(
                        "Library",
                        date
                );

        System.out.println(
                "Reservations before cancellation: "
                        + beforeCancel.size()
        );

        // Cancel reservation
        controller.cancelReservation(reservation);

        // Check reservations after cancellation
        List<Reservation> afterCancel =
                controller.getReservationsByDay(
                        "Library",
                        date
                );

        System.out.println(
                "Reservations after cancellation: "
                        + afterCancel.size()
        );

        // Check if the canceled time is available again
        boolean timeAvailable =
                controller.isTimeAvailable(
                        "Library",
                        date,
                        LocalTime.of(9, 0),
                        LocalTime.of(10, 0)
                );

        System.out.println(
                "Canceled time available again: "
                        + timeAvailable
        );

        // Verify reservation was removed
        boolean reservationRemoved =
                !afterCancel.contains(reservation);

        System.out.println(
                "Reservation successfully removed: "
                        + reservationRemoved
        );
    }
}