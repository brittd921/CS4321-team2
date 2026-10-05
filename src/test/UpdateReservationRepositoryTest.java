package test;

import model.Reservation;
import model.Space;
import persistence.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class UpdateReservationRepositoryTest {

    public static void main(String[] args) {

        ReservationRepository repository = new ReservationRepository();

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

        LocalDate originalDate = LocalDate.now().plusDays(1);
        LocalDate newDate = LocalDate.now().plusDays(2);

        Reservation reservation1 = new Reservation(
                space1,
                originalDate,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );

        Reservation reservation2 = new Reservation(
                space2,
                originalDate,
                LocalTime.of(1, 0),
                LocalTime.of(2, 0)
        );

        repository.addReservation(reservation1);
        repository.addReservation(reservation2);

        boolean updated = repository.updateReservation(
                reservation1,
                newDate,
                LocalTime.of(11, 0),
                LocalTime.of(12, 0)
        );

        List<Reservation> reservations =
                repository.getAllReservations();

        Reservation updatedReservation = reservations.get(1);

        System.out.println("Reservation updated: " + updated);
        System.out.println("Updated date: " + updatedReservation.getDate());
        System.out.println("Updated start time: " + updatedReservation.getStartTime());
        System.out.println("Updated end time: " + updatedReservation.getEndTime());
        System.out.println("Space preserved: "
                + updatedReservation.getSpace().getName().equals("Library"));
        System.out.println("Total reservations: " + reservations.size());
        System.out.println("Other reservation unchanged: "
                + reservations.get(0).getSpace().getName().equals("Computer Lab"));
    }
}