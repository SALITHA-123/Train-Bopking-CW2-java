public class PassengerQueue {

    private static Passenger[] passengerQueue = new Passenger[TrainStation.PASSENGER_CAPACITY];

    public static void addToQueue(Passenger passenger, int index){
        System.out.println("Customer "+passenger.getName()+" to the Seat Number "+(index+1));
        passengerQueue[index]=passenger;
    }

    public static Passenger[] getPassengerQueue() {
        return passengerQueue;
    }

    public static void delete(int seat) {
        passengerQueue[seat] = null;
    }


    public static boolean isFull() {
        for (Passenger passenger : passengerQueue){
            if (passenger == null) return false;
        }
        return true;
    }
    public static boolean isEmpty() {
        for (Passenger passenger : passengerQueue){
            if (passenger != null) return false;
        }
        return true;
    }


}

