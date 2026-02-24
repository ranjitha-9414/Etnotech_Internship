import java.util.ArrayList;
import java.util.List;

public class TaxiManager {

    private List<Taxi> taxis = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();
    private int bookingCounter = 1;

    public TaxiManager(int numberOfTaxis) {
        for (int i = 1; i <= numberOfTaxis; i++) {
            taxis.add(new Taxi(i));
        }
    }

    public void bookTaxi(char from, char to) throws TaxiNotAvailableException{
        if (from == to) {
            System.out.println("Pickup and Drop locations cannot be same!");
            return;
        }
        Taxi assignedTaxi = null;
        int minDistance = Integer.MAX_VALUE;

        for (Taxi taxi : taxis) {
            if (taxi.isAvailable()) {
                int distance = Math.abs(taxi.getCurrentLocation() - from);
                if (distance < minDistance) {
                    minDistance = distance;
                    assignedTaxi = taxi;
                }
            }
        }

        if (assignedTaxi == null) {
            throw new TaxiNotAvailableException("All taxis are busy!");
            
           
        }

        int tripDistance = Math.abs(from - to) * 15;

        int fare = 100;
        if (tripDistance > 5) {
            fare += (tripDistance - 5) * 10;
        }

        assignedTaxi.setCurrentLocation(to);
        assignedTaxi.addEarnings(fare);

        Booking booking = new Booking(
                bookingCounter++,
                assignedTaxi.getTaxiId(),
                from,
                to,
                fare
        );

        bookings.add(booking);

        System.out.println("Taxi " + assignedTaxi.getTaxiId() + " booked successfully!");
        System.out.println("Fare: " + fare);
        System.out.println("Thank you for using our service!");
    }

    public void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings yet!");
            return;
        }

        for (Booking b : bookings) {
            b.display();
        }
    }
}
