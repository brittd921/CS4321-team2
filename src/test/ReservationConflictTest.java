package test;

import controller.ReservationController;
import model.Reservation;
import model.Space;
import persistence.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationConflictTest {

    public static void main(String[] args) {

        ReservationRepository repository = new ReservationRepository();
        ReservationController controller = new ReservationController(repository);

        Space library = new Space(
                "S001",
                "Library",
                "Main Building",
                100,
                "Large study and meeting space"
        );

        Space computerLab = new Space(
                "S002",
                "Computer Lab",
                "Science Building",
                30,
                "Computer workspace for students"
        );

        LocalDate date = LocalDate.now().plusDays(1);
        LocalDate otherDate = LocalDate.now().plusDays(2);

        repository.addReservation(
                new Reservation(
                        library,
                        date,
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 0)
                )
        );

        boolean overlappingConflict = controller.hasReservationConflict(
                "Library",
                date,
                LocalTime.of(10, 30),
                LocalTime.of(11, 30)
        );

        boolean backToBackConflict = controller.hasReservationConflict(
                "Library",
                date,
                LocalTime.of(11, 0),
                LocalTime.of(12, 0)
        );

        boolean otherSpaceConflict = controller.hasReservationConflict(
                "Computer Lab",
                date,
                LocalTime.of(10, 30),
                LocalTime.of(11, 30)
        );

        boolean otherDateConflict = controller.hasReservationConflict(
                "Library",
                otherDate,
                LocalTime.of(10, 30),
                LocalTime.of(11, 30)
        );

        String conflictingValidation = controller.validateReservation(
                library,
                date,
                LocalTime.of(10, 30),
                LocalTime.of(11, 30)
        );

        String backToBackValidation = controller.validateReservation(
                library,
                date,
                LocalTime.of(11, 0),
                LocalTime.of(12, 0)
        );

        System.out.println("Overlapping reservation conflict: " + overlappingConflict);
        System.out.println("Back-to-back reservation conflict: " + backToBackConflict);
        System.out.println("Other space conflict: " + otherSpaceConflict);
        System.out.println("Other date conflict: " + otherDateConflict);
        System.out.println("Conflicting reservation validation: " + conflictingValidation);
        System.out.println("Back-to-back reservation validation: " + backToBackValidation);
    }
}