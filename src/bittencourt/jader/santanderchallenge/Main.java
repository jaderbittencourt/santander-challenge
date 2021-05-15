package bittencourt.jader.santanderchallenge;

import bittencourt.jader.santanderchallenge.publihser.Publisher;
import bittencourt.jader.santanderchallenge.subscriber.FxPriceUpdateSubscriber;

public class Main {

    public static void main(String[] args) {
        final Publisher publisher = Publisher.getInstance();

        try {
            publisher.events.subscribe(new FxPriceUpdateSubscriber());

            publisher.onMessage("101,EUR/USD,1.8000,1.9000,01-06-2020 12:01:01:001");
            publisher.onMessage("102,EUR/JPY,115.60,116.90,01-06-2020 12:01:02:002");
            publisher.onMessage("103,GBP/USD,1.3500,1.4560,01-06-2020 12:01:02:003");
            publisher.onMessage("104,GBP/USD,1.8499,1.9561,01-06-2020 12:01:02:004");
            publisher.onMessage("115,EUR/JPY,119.51,119.71,01-06-2020 12:01:02:110");

            publisher.onMessage("106,EUR/USD,1.1000,1.2000,01-06-2020 12:01:01:111");
            publisher.onMessage("107,EUR/JPY,119.60,119.90,01-06-2020 12:01:02:112");
            publisher.onMessage("108,GBP/USD,1.2500,1.2560,01-06-2020 12:01:02:112");
            publisher.onMessage("109,GBP/USD,1.2499,1.2561,01-06-2020 12:01:02:103");
            publisher.onMessage("110,EUR/JPY,119.61,119.91,01-06-2020 12:01:02:115");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
