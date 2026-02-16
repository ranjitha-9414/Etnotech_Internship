public class Booking {

    private int bookingId;
    private int taxiId;
    private char from;
    private char to;
    private int fare;

    public Booking(int bookingId, int taxiId, char from, char to, int fare) {
        this.bookingId = bookingId;
        this.taxiId = taxiId;
        this.from = from;
        this.to = to;
        this.fare = fare;
    }

    public void display() {
        System.out.println("\nBooking ID: " + bookingId);
        System.out.println("Taxi ID: " + taxiId);
        System.out.println("From: " + from);
        System.out.println("To: " + to);
        System.out.println("Fare: " + fare);
    }
}
