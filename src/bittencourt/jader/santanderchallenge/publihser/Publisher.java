package bittencourt.jader.santanderchallenge.publihser;

public class Publisher {
    private static Publisher instance;
    public final EventManager events;

    private Publisher() {
        this.events = new EventManager();
    }

    public static Publisher getInstance() {
        if (instance == null) {
            instance = new Publisher();
        }
        return instance;
    }

    public void onMessage(String csv) {
        events.notify(csv);
    }
}