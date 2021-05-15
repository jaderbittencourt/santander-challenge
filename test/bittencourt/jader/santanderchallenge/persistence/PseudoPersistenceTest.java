package bittencourt.jader.santanderchallenge.persistence;

import bittencourt.jader.santanderchallenge.entities.Price;
import bittencourt.jader.santanderchallenge.exceptions.InstrumentPriceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PseudoPersistenceTest {

    @Test
    public void shouldUpdatePriceWithLastProcessedInput() {
        PseudoPersistence p = PseudoPersistence.getInstance();

        Price price = new Price(
                104,
                "EUR/USD",
                new BigDecimal("1.1000"),
                new BigDecimal("1.2000"),
                Timestamp.valueOf(LocalDateTime.now())
        );
        p.persist(price);


        price = new Price(
                105,
                "EUR/USD",
                new BigDecimal("1.3000"),
                new BigDecimal("1.4000"),
                Timestamp.valueOf(LocalDateTime.now())
        );
        p.persist(price);


        price = new Price(
                106,
                "EUR/USD",
                new BigDecimal("1.5000"),
                new BigDecimal("1.6000"),
                Timestamp.valueOf(LocalDateTime.now())
        );
        p.persist(price);

        try {
            Price result = p.getPriceByInstrumentName(price.instrumentName);
            Assertions.assertEquals(price.uniqueId, result.uniqueId);
            Assertions.assertEquals(price.instrumentName, result.instrumentName);
            Assertions.assertEquals(price.ask, result.ask);
            Assertions.assertEquals(price.bid, result.bid);
            Assertions.assertEquals(price.timestamp, result.timestamp);
        } catch (InstrumentPriceNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldThrowExceptionWhenInstrumentNameNotFound() {
        Assertions.assertThrows(InstrumentPriceNotFoundException.class, () -> {
            PseudoPersistence.getInstance().getPriceByInstrumentName("XAU/USD");
        });
    }
}
