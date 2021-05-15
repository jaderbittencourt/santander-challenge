package bittencourt.jader.santanderchallenge.subscriber;

import bittencourt.jader.santanderchallenge.entities.Price;
import bittencourt.jader.santanderchallenge.exceptions.InstrumentPriceNotFoundException;
import bittencourt.jader.santanderchallenge.persistence.PseudoPersistence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class FxPriceUpdateSubscriberTest {
    FxPriceUpdateSubscriber subscriber;

    @BeforeEach
    public void setUp() {
        subscriber = new FxPriceUpdateSubscriber();
    }

    @Test
    public void getTimestampFromStringTest() {
        String expectedTimestamp = "2021-05-15 11:36:28.402";
        String timestamp = "15-05-2021 11:36:28:402";
        Assertions.assertEquals(expectedTimestamp, subscriber.getTimestampFromString(timestamp).toString());
    }

    @Test
    public void calculateCommissionTest() {
        String value = "5000";
        BigDecimal expectedValue = new BigDecimal("4995.0");
        BigDecimal result = subscriber.calculateCommission(new BigDecimal(value), new BigDecimal("-0.1"));
        Assertions.assertEquals(expectedValue, result);

        value = "1.5000";
        expectedValue = new BigDecimal("1.49850");
        result = subscriber.calculateCommission(new BigDecimal(value), new BigDecimal("-0.1"));
        Assertions.assertEquals(expectedValue, result);

        value = "5000";
        expectedValue = new BigDecimal("5005.0");
        result = subscriber.calculateCommission(new BigDecimal(value), new BigDecimal("0.1"));
        Assertions.assertEquals(expectedValue, result);

        value = "1.5000";
        expectedValue = new BigDecimal("1.50150");
        result = subscriber.calculateCommission(new BigDecimal(value), new BigDecimal("0.1"));
        Assertions.assertEquals(expectedValue, result);
    }

    @Test
    public void buildPriceTest() {
        Price expectedPrice = new Price(
                106,
                "EUR/USD",
                new BigDecimal("1.09890"),
                new BigDecimal("1.20120"),
                Timestamp.valueOf("2020-06-01 12:01:01.001")
        );

        Price result = subscriber.buildPrice("106,EUR/USD,1.1000,1.2000,01-06-2020 12:01:01:001");
        Assertions.assertEquals(expectedPrice.uniqueId, result.uniqueId);
        Assertions.assertEquals(expectedPrice.instrumentName, result.instrumentName);
        Assertions.assertEquals(expectedPrice.ask, result.ask);
        Assertions.assertEquals(expectedPrice.bid, result.bid);
        Assertions.assertEquals(expectedPrice.timestamp, result.timestamp);
    }

    @Test
    public void processTest() {
        Price expectedPrice = new Price(
                106,
                "EUR/USD",
                new BigDecimal("1.09890"),
                new BigDecimal("1.20120"),
                Timestamp.valueOf("2020-06-01 12:01:01.003")
        );
        subscriber.process("104,EUR/USD,1.8000,1.9000,01-06-2020 12:01:01:001");
        subscriber.process("105,EUR/USD,1.7000,1.8000,01-06-2020 12:01:01:002");
        subscriber.process("106,EUR/USD,1.1000,1.2000,01-06-2020 12:01:01:003");
        try {
            Price result = PseudoPersistence.getInstance().getPriceByInstrumentName("EUR/USD");
            Assertions.assertEquals(expectedPrice.uniqueId, result.uniqueId);
            Assertions.assertEquals(expectedPrice.instrumentName, result.instrumentName);
            Assertions.assertEquals(expectedPrice.ask, result.ask);
            Assertions.assertEquals(expectedPrice.bid, result.bid);
            Assertions.assertEquals(expectedPrice.timestamp, result.timestamp);
        } catch (InstrumentPriceNotFoundException e) {
            e.printStackTrace();
        }
    }

}
