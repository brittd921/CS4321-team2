package test;

import controller.SpaceController;
import model.Reservation;
import model.Space;
import persistence.SpaceRepository;
import view.SpaceView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AvailabilityViewTest {

    public static void main(String[] args) {

        SpaceRepository repository = new SpaceRepository();
        SpaceController controller = new SpaceController(repository);
        SpaceView view = new SpaceView(controller);

        Space space = new Space(
                "S001",
                "Library",
                "Main Building",
                100,
                "Large study and meeting space"
        );

        LocalDate date = LocalDate.of(2026, 10, 3);

        List<Reservation> reservations = new ArrayList<>();

        reservations.add(new Reservation(
                space,
                date,
                LocalTime.of(14, 0),
                LocalTime.of(15, 0)
        ));

        reservations.add(new Reservation(
                space,
                date,
                LocalTime.of(9, 0),
                LocalTime.of(10, 0)
        ));

        view.displayAvailability(space, date, reservations);

        System.out.println();

        List<Reservation> noReservations = new ArrayList<>();

        view.displayAvailability(space, date, noReservations);
    }
}