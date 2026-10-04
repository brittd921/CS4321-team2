package test;

import controller.ReservationController;
import model.Reservation;
import model.Space;
import persistence.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CreateReservationControllerTest {

    public static void main(String[] args) {

        ReservationRepository repository = new ReservationRepository();
        ReservationController controller = new ReservationController(repository);

        Space space = new Space(
                "S001",
                "Library",
                "Main Building",
                100,
                "Large study and meeting space"
        );

        LocalDate date = LocalDate.now().plusDays(1);

        String validResult = controller.createReservation(
                space,
                date,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );

        List<Reservation> reservations =
                controller.getReservationsByDay("Library", date);

        String conflictResult = controller.createReservation(
                space,
                date,
                LocalTime.of(10, 30),
                LocalTime.of(11, 30)
        );

        String backToBackResult = controller.createReservation(
                space,
                date,
                LocalTime.of(11, 0),
                LocalTime.of(12, 0)
        );

        String invalidResult = controller.createReservation(
                space,
                date,
                LocalTime.of(2, 0),
                LocalTime.of(1, 0)
        );

        List<Reservation> finalReservations =
                controller.getReservationsByDay("Library", date);

        System.out.println("Valid reservation: " + validResult);
        System.out.println("Reservations after valid creation: " + reservations.size());
        System.out.println("Conflicting reservation: " + conflictResult);
        System.out.println("Back-to-back reservation: " + backToBackResult);
        System.out.println("Invalid reservation: " + invalidResult);
        System.out.println("Final saved reservations: " + finalReservations.size());
    }
}