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

        Space space1 = new Space(
                "S001",
                "Library",
                "Main Building",
                100,
                "Large study and meeting space"
        );

        Space space2 = new Space(
                "S002",
                "Computer Lab",
                "Technology Building",
                30,
                "Computer lab"
        );

        LocalDate date = LocalDate.now().plusDays(1);
        LocalDate otherDate = LocalDate.now().plusDays(2);
        LocalDate pastDate = LocalDate.now().minusDays(1);

        String valid = controller.createReservation(
                space1,
                date,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );

        String overlap = controller.createReservation(
                space1,
                date,
                LocalTime.of(10, 30),
                LocalTime.of(11, 30)
        );

        String backToBack = controller.createReservation(
                space1,
                date,
                LocalTime.of(11, 0),
                LocalTime.of(12, 0)
        );

        String endBeforeStart = controller.createReservation(
                space1,
                date,
                LocalTime.of(2, 0),
                LocalTime.of(1, 0)
        );

        String equalTimes = controller.createReservation(
                space1,
                date,
                LocalTime.of(1, 0),
                LocalTime.of(1, 0)
        );

        String past = controller.createReservation(
                space1,
                pastDate,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );

        String missing = controller.createReservation(
                null,
                date,
                LocalTime.of(1, 0),
                LocalTime.of(2, 0)
        );

        String differentSpace = controller.createReservation(
                space2,
                date,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );

        String differentDate = controller.createReservation(
                space1,
                otherDate,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );

        List<Reservation> savedReservations =
                controller.getReservationsByDay("Library", date);

        System.out.println("Valid reservation: " + valid);
        System.out.println("Overlapping reservation: " + overlap);
        System.out.println("Back-to-back reservation: " + backToBack);
        System.out.println("End before start: " + endBeforeStart);
        System.out.println("Equal start and end: " + equalTimes);
        System.out.println("Past reservation: " + past);
        System.out.println("Missing information: " + missing);
        System.out.println("Different space: " + differentSpace);
        System.out.println("Different date: " + differentDate);
        System.out.println("Saved Library reservations for date: "
                + savedReservations.size());
    }
}