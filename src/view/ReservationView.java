package view;

import controller.ReservationController;
import model.Reservation;
import model.Space;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReservationView {

    private final ReservationController controller;

    public ReservationView(ReservationController controller) {
        this.controller = controller;
    }

    // US-5: Display reserved and available times differently
    public void displayDailySchedule(
            String spaceName,
            LocalDate date
    ) {

        System.out.println(
                "Daily Schedule for " + spaceName
                        + " on " + date
        );

        System.out.println("--------------------------------");

        LocalTime start = LocalTime.of(9, 0);
        LocalTime endOfDay = LocalTime.of(17, 0);

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("h:mm a");

        while (start.isBefore(endOfDay)) {

            LocalTime end = start.plusHours(1);

            boolean reserved =
                    controller.isTimeReserved(
                            spaceName,
                            date,
                            start,
                            end
                    );

            String status = reserved
                    ? "RESERVED"
                    : "AVAILABLE";

            System.out.println(
                    start.format(formatter)
                            + " - "
                            + end.format(formatter)
                            + " | "
                            + status
            );

            start = end;
        }
    }

    // US-6: Create a reservation and display the result
    public void createReservation(
            Space space,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime
    ) {

        String result =
                controller.createReservation(
                        space,
                        date,
                        startTime,
                        endTime
                );

        System.out.println(result);
    }

    // US-7: Display all reservations
    public void displayMyReservations() {

        List<Reservation> reservations =
                controller.getAllReservations();

        if (reservations.isEmpty()) {
            System.out.println("You have no reservations.");
            return;
        }

        DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern("MM/dd/yyyy");

        DateTimeFormatter timeFormatter =
                DateTimeFormatter.ofPattern("h:mm a");

        System.out.println("My Reservations");
        System.out.println("--------------------------------");

        for (Reservation reservation : reservations) {

            System.out.println(
                    reservation.getSpace().getName()
                            + " | "
                            + reservation.getDate().format(dateFormatter)
                            + " | "
                            + reservation.getStartTime().format(timeFormatter)
                            + " - "
                            + reservation.getEndTime().format(timeFormatter)
            );
        }
    }
}