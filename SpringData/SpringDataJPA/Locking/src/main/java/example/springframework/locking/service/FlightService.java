package example.springframework.locking.service;

import example.springframework.locking.domain.Flight;
import example.springframework.locking.repository.FlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository){
        this.flightRepository = flightRepository;
    }

    public Flight save(Flight flight){
        return flightRepository.save(flight);
    }

    public Flight findById(Long flightId) { return flightRepository.findById(flightId).orElseThrow(); }

    @Transactional
    public  void allocateSeats(long flightId, int numOfSeats){

        Flight flight = flightRepository.findById( flightId).orElseThrow( () -> new RuntimeException("Flight not found"));

        if( flight.getAvailableSeats() < numOfSeats) throw new IllegalStateException("Number of seats requested, should be less than total available seats");

        flight.setAvailableSeats( flight.getAvailableSeats() - numOfSeats );

        flightRepository.save( flight );
    }

    @Transactional
    public  void allocateSeatsWithLock(long flightId, int numOfSeats){

        // SELECT ... FOR UPDATE
        Flight flight = flightRepository.findByIdWithLock( flightId).orElseThrow( () -> new RuntimeException("Flight not found"));

        if( flight.getAvailableSeats() < numOfSeats) throw new IllegalStateException("Number of seats requested, should be less than total available seats");

        flight.setAvailableSeats( flight.getAvailableSeats() - numOfSeats );

        flightRepository.save( flight );
    }

    public void deleteFlight( long flightId ){
        flightRepository.deleteById(flightId);
    }

}
