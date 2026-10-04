package test;

import controller.ReservationController;
import model.Reservation;
import model.Space;
import persistence.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class DistinguishReservedTimesTest {

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

        repository.addReservation(
                new Reservation(
                        space,
                        date,
                        LocalTime.of(14, 0),
                        LocalTime.of(15, 0)
                )
        );

        boolean reservedTime = controller.isTimeReserved(
                "Library",
                date,
                LocalTime.of(9, 0),
                LocalTime.of(10, 0)
        );

        boolean availableTime = controller.isTimeAvailable(
                "Library",
                date,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );

        List<Reservation> dailySchedule =
                controller.getReservationsByDay("Library", date);

        LocalDate emptyDate = LocalDate.of(2026, 10, 4);

        List<Reservation> emptySchedule =
                controller.getReservationsByDay("Library", emptyDate);

        boolean fullyAvailable = controller.isTimeAvailable(
                "Library",
                emptyDate,
                LocalTime.of(9, 0),
                LocalTime.of(10, 0)
        );

        System.out.println("Reserved time identified: " + reservedTime);
        System.out.println("Available time identified: " + availableTime);
        System.out.println("Reservations in daily schedule: " + dailySchedule.size());
        System.out.println("No reservations on empty day: " + emptySchedule.isEmpty());
        System.out.println("Empty day is fully available: " + fullyAvailable);
        System.out.println("US-4 reservations still retrieved: " + dailySchedule.size());
    }
}