

import controller.ReservationController;
import persistence.ReservationFilePersistence;
import persistence.ReservationRepository;
import view.ReservationView;

public class Reservation {

    public static void main(String[] args) {
        // US-10: load previously saved reservations when the app starts
        ReservationFilePersistence persistence =
                new ReservationFilePersistence("reservations.txt");
        ReservationRepository repository =
                new ReservationRepository(persistence);
        ReservationController controller =
                new ReservationController(repository);
        ReservationView view = new ReservationView(controller);

        System.out.println("Sprint project started");
    }
}