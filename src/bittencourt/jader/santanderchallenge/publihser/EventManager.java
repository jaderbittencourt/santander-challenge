package bittencourt.jader.santanderchallenge.publihser;

import bittencourt.jader.santanderchallenge.subscriber.Subscriber;

import java.util.HashSet;
import java.util.Set;

public class EventManager {
    final Set<Subscriber> subscribers = new HashSet<>();

    public void subscribe(Subscriber listener) {
        subscribers.add(listener);
    }

    public void notify(String csv) {
        subscribers.forEach(listener -> listener.process(csv));
    }

}
