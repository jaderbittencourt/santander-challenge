package bittencourt.jader.santanderchallenge;

import bittencourt.jader.santanderchallenge.entities.Price;
import bittencourt.jader.santanderchallenge.exceptions.InstrumentPriceNotFoundException;
import bittencourt.jader.santanderchallenge.persistence.PseudoPersistence;
import bittencourt.jader.santanderchallenge.publihser.Publisher;
import bittencourt.jader.santanderchallenge.subscriber.FxPriceUpdateSubscriber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class MainTest {

    @Test
    public void testEntireFlow() {

        final Publisher publisher = Publisher.getInstance();

        try {
            publisher.events.subscribe(new FxPriceUpdateSubscriber());
            publisher.onMessage("101,EUR/USD,1.8000,1.9000,01-06-2020 12:01:01:001");
            publisher.onMessage("106,EUR/USD,1.1000,1.2000,01-06-2020 12:01:01:111");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Price expectedEURUSD = new Price(
                106,
                "EUR/USD",
                new BigDecimal(("1.09890")),
                new BigDecimal(("1.20120")),
                Timestamp.valueOf("2020-06-01 12:01:01.111")
        );

        try {
            Price result = PseudoPersistence.getInstance().getPriceByInstrumentName("EUR/USD");
            Assertions.assertEquals(expectedEURUSD.uniqueId, result.uniqueId);
            Assertions.assertEquals(expectedEURUSD.instrumentName, result.instrumentName);
            Assertions.assertEquals(expectedEURUSD.ask, result.ask);
            Assertions.assertEquals(expectedEURUSD.bid, result.bid);
            Assertions.assertEquals(expectedEURUSD.timestamp, result.timestamp);

            // print result that would be sent to REST endpoint
            System.out.println(result.toString());
        } catch (InstrumentPriceNotFoundException e) {
            e.printStackTrace();
        }


    }
}
