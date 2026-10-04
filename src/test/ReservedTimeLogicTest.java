package test;

import controller.ReservationController;
import model.Reservation;
import model.Space;
import persistence.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservedTimeLogicTest {

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

        LocalDate date = LocalDate.of(2026, 10, 3);

        repository.addReservation(
                new Reservation(
                        space,
                        date,
                        LocalTime.of(9, 0),
                        LocalTime.of(10, 0)
                )
        );

        boolean reserved = controller.isTimeReserved(
                "Library",
                date,
                LocalTime.of(9, 30),
                LocalTime.of(10, 30)
        );

        boolean available = controller.isTimeAvailable(
                "Library",
                date,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );

        System.out.println("9:30 AM - 10:30 AM reserved: " + reserved);
        System.out.println("10:00 AM - 11:00 AM available: " + available);

        System.out.println(
                "US-4 reservations found: "
                        + controller.getReservationsByDay("Library", date).size()
        );
    }
}