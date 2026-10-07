package test;
// KISS MY ASS!!!
import model.Reservation;
import model.Space;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.ReservationFilePersistence;
import persistence.ReservationRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationPersistenceTest {

    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("reservations-test", ".txt");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    private ReservationRepository newRepository() {
        return new ReservationRepository(
                new ReservationFilePersistence(tempFile.toString())
        );
    }

    private Reservation sampleReservation(String spaceId, LocalDate date) {
        Space space = new Space(
                spaceId, "Library", "Main Building", 100, "Study space"
        );
        return new Reservation(
                space, date, LocalTime.of(9, 0), LocalTime.of(10, 0)
        );
    }

    @Test
    void testSaveAndLoadSingleReservation() {
        ReservationRepository repository = newRepository();
        repository.addReservation(
                sampleReservation("S001", LocalDate.of(2026, 10, 20))
        );

        List<Reservation> loaded =
                new ReservationFilePersistence(tempFile.toString())
                        .loadReservations();

        assertEquals(1, loaded.size());

        Reservation reservation = loaded.get(0);
        assertEquals("S001", reservation.getSpace().getId());
        assertEquals("Library", reservation.getSpace().getName());
        assertEquals("Main Building", reservation.getSpace().getBuilding());
        assertEquals(100, reservation.getSpace().getCapacity());
        assertEquals("Study space", reservation.getSpace().getDescription());
        assertEquals(LocalDate.of(2026, 10, 20), reservation.getDate());
        assertEquals(LocalTime.of(9, 0), reservation.getStartTime());
        assertEquals(LocalTime.of(10, 0), reservation.getEndTime());
    }

    @Test
    void testRestartRestoresReservation() {
        ReservationRepository firstRun = newRepository();
        firstRun.addReservation(
                sampleReservation("S001", LocalDate.of(2026, 10, 20))
        );

        // Simulate an application restart: a new repository
        // over the same file must restore the saved reservation.
        ReservationRepository secondRun = newRepository();
        List<Reservation> restored = secondRun.getAllReservations();

        assertEquals(1, restored.size());
        assertEquals("S001", restored.get(0).getSpace().getId());
        assertEquals(LocalDate.of(2026, 10, 20), restored.get(0).getDate());
    }

    @Test
    void testMultipleReservationsSavedAndRestored() {
        ReservationRepository repository = newRepository();
        repository.addReservation(
                sampleReservation("S001", LocalDate.of(2026, 10, 20)));
        repository.addReservation(
                sampleReservation("S002", LocalDate.of(2026, 10, 21)));
        repository.addReservation(
                sampleReservation("S003", LocalDate.of(2026, 10, 22)));

        ReservationRepository restarted = newRepository();

        assertEquals(3, restarted.getAllReservations().size());
    }

    @Test
    void testUpdatedReservationPersists() {
        ReservationRepository repository = newRepository();
        Reservation reservation =
                sampleReservation("S001", LocalDate.of(2026, 10, 20));
        repository.addReservation(reservation);

        repository.updateReservation(
                reservation,
                LocalDate.of(2026, 11, 5),
                LocalTime.of(14, 0),
                LocalTime.of(15, 0)
        );

        ReservationRepository restarted = newRepository();
        List<Reservation> restored = restarted.getAllReservations();

        assertEquals(1, restored.size());
        assertEquals(LocalDate.of(2026, 11, 5), restored.get(0).getDate());
        assertEquals(LocalTime.of(14, 0), restored.get(0).getStartTime());
        assertEquals(LocalTime.of(15, 0), restored.get(0).getEndTime());
    }

    @Test
    void testRemovedReservationIsNotRestored() {
        ReservationRepository repository = newRepository();
        Reservation reservation =
                sampleReservation("S001", LocalDate.of(2026, 10, 20));
        repository.addReservation(reservation);
        repository.removeReservation(reservation);

        ReservationRepository restarted = newRepository();

        assertTrue(restarted.getAllReservations().isEmpty());
    }

    @Test
    void testMissingFileRestoresEmptyList() throws IOException {
        Files.deleteIfExists(tempFile);

        ReservationRepository repository = newRepository();

        assertTrue(repository.getAllReservations().isEmpty());
    }

    @Test
    void testEmptyFileRestoresEmptyList() {
        // setUp() already created an empty temp file.
        ReservationRepository repository = newRepository();

        assertTrue(repository.getAllReservations().isEmpty());
    }
}