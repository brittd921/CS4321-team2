package test;

import controller.ReservationController;
import model.Space;
import persistence.ReservationRepository;
import view.ReservationView;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateReservationViewTest {

    public static void main(String[] args) {

        ReservationRepository repository = new ReservationRepository();
        ReservationController controller = new ReservationController(repository);
        ReservationView view = new ReservationView(controller);

        Space space = new Space(
                "S001",
                "Library",
                "Main Building",
                100,
                "Large study and meeting space"
        );

        LocalDate futureDate = LocalDate.now().plusDays(1);
        LocalDate pastDate = LocalDate.now().minusDays(1);

        view.createReservation(
                space,
                futureDate,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );

        view.createReservation(
                space,
                futureDate,
                LocalTime.of(10, 30),
                LocalTime.of(11, 30)
        );

        view.createReservation(
                space,
                futureDate,
                LocalTime.of(2, 0),
                LocalTime.of(1, 0)
        );

        view.createReservation(
                space,
                pastDate,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );

        view.createReservation(
                null,
                futureDate,
                LocalTime.of(12, 0),
                LocalTime.of(1, 0)
        );
    }
}