public class Taxi {

    private int taxiId;
    private char currentLocation;
    private boolean isAvailable;
    private int totalEarnings;

    public Taxi(int taxiId) {
        this.taxiId = taxiId;
        this.currentLocation = 'A';
        this.isAvailable = true;
        this.totalEarnings = 0;
    }

    public int getTaxiId() {
        return taxiId;
    }

    public char getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(char location) {
        this.currentLocation = location;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public int getTotalEarnings() {
        return totalEarnings;
    }

    public void addEarnings(int amount) {
        this.totalEarnings += amount;
    }
}
