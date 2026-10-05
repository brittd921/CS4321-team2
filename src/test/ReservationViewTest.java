package test;

import controller.ReservationController;
import model.Reservation;
import model.Space;
import persistence.ReservationRepository;
import view.ReservationView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservationViewTest {

    public static void main(String[] args) {

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

        Space computerLab =
                new Space(
                        "S002",
                        "Computer Lab",
                        "Science Building",
                        30,
                        "Computer workspace for students"
                );

        repository.addReservation(
                new Reservation(
                        library,
                        LocalDate.of(2026, 10, 20),
                        LocalTime.of(13, 0),
                        LocalTime.of(14, 0)
                )
        );

        repository.addReservation(
                new Reservation(
                        computerLab,
                        LocalDate.of(2026, 10, 15),
                        LocalTime.of(14, 0),
                        LocalTime.of(15, 0)
                )
        );

        repository.addReservation(
                new Reservation(
                        library,
                        LocalDate.of(2026, 10, 15),
                        LocalTime.of(9, 0),
                        LocalTime.of(10, 0)
                )
        );

        ReservationController controller =
                new ReservationController(repository);

        ReservationView view =
                new ReservationView(controller);

        System.out.println("US-7 TEST - RESERVATIONS");
        view.displayMyReservations();

        System.out.println();

        ReservationRepository emptyRepository =
                new ReservationRepository();

        ReservationController emptyController =
                new ReservationController(emptyRepository);

        ReservationView emptyView =
                new ReservationView(emptyController);

        System.out.println("US-7 TEST - NO RESERVATIONS");
        emptyView.displayMyReservations();
    }
}