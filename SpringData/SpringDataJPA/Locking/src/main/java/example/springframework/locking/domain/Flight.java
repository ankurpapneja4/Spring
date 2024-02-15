package example.springframework.locking.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Flight {

    @Id
    private Long id;

    private int availableSeats;

    public Flight() {}

    public Flight(Long id, int availableSeats) {
        this.id = id;
        this.availableSeats = availableSeats;
    }

    public long getId() {
        return id;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", availableSeats=" + availableSeats +
                '}';
    }
}
