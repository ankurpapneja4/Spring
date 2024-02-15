package example.springframework.locking.repository;

import example.springframework.locking.domain.Flight;
import example.springframework.locking.service.FlightService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ActiveProfiles("h2")
@DataJpaTest
@ContextConfiguration( classes = { FlightRepositoryTest.JPATestConfiguration.class, FlightService.class })
@AutoConfigureTestDatabase( replace = Replace.NONE)
class FlightRepositoryTest {

    @TestConfiguration
    @EnableJpaRepositories( basePackageClasses = { FlightRepository.class })
    @EntityScan( basePackageClasses = { Flight.class })
    static class JPATestConfiguration {

    }

    @Autowired
    FlightService flightService;

    final long FLIGHT_ID = 1L;
    final int FLIGHT_CAPACITY = 50;

    @BeforeEach
    void addFlightData() throws InterruptedException {
        Thread thread = new Thread( () -> flightService.save( new Flight(FLIGHT_ID, FLIGHT_CAPACITY)));
        thread.start(); thread.join();
    }

    @AfterEach
    void deleteFlightData() throws InterruptedException {
        Thread thread = new Thread( () -> flightService.deleteFlight( FLIGHT_ID));
        thread.start(); thread.join();
    }

    @Test
    void givenNoLocking_whenConcurrentTransactionUpdatingSameRecord_shouldCauseInconsistency() throws InterruptedException {


        int currentAvailableSeats = flightService.findById( FLIGHT_ID ).getAvailableSeats();

        // Given: 50 Seats Are Available
        assertEquals( FLIGHT_CAPACITY , currentAvailableSeats );


        int numberOfThreads = 50;
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch( numberOfThreads );

        for(int i = 0; i < numberOfThreads; i++)
            executorService.execute( () -> {
                            try {
                                // When : For 50 Concurrent Requests, Allocate 1 Seat Per Request
                                flightService.allocateSeats( FLIGHT_ID , 1);
                            }
                            catch ( RuntimeException exp) { System.out.println( exp.getMessage() ); }
                            finally { countDownLatch.countDown(); }
                    });

        countDownLatch.await(10, TimeUnit.SECONDS);


        // Then: Number Of Available Seats, After Allocation, Should Be Zero
        Thread thread =new Thread( () -> {
            int availableSeatsAfterAllocation = flightService.findById( FLIGHT_ID ).getAvailableSeats();
            assertEquals( 0, availableSeatsAfterAllocation, "No Seat Should Be Available, After Allocating 50 Seats" );

        });
        thread.start(); thread.join();

    }



}