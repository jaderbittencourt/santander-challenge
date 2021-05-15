package bittencourt.jader.santanderchallenge.subscriber;

import bittencourt.jader.santanderchallenge.entities.Price;
import bittencourt.jader.santanderchallenge.exceptions.InstrumentPriceNotFoundException;
import bittencourt.jader.santanderchallenge.persistence.PseudoPersistence;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FxPriceUpdateSubscriber implements Subscriber {

    @Override
    public void process(String csv) {
        Price price = buildPrice(csv);
        PseudoPersistence.getInstance().persist(price); // "store" the new price

        // ***ATENTION*** here would come the publish to REST logic
        try {
            System.out.println(PseudoPersistence.getInstance().getPriceByInstrumentName(price.instrumentName));
        } catch (InstrumentPriceNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected Price buildPrice(String csv) {
        /**
         * In a production environment here I would include validations for each csv value.
         * Considering the test purpose and the controlled environment and incoming values, I omitted the validations
         */
        final String[] split = csv.split(",");
        return new Price(
                Integer.parseInt(split[0]),
                split[1],
                calculateCommission(new BigDecimal(split[2]), new BigDecimal("-0.1")),
                calculateCommission(new BigDecimal(split[3]), new BigDecimal("0.1")),
                getTimestampFromString(split[4])
        );
    }

    protected BigDecimal calculateCommission(BigDecimal value, BigDecimal percentage) {
        BigDecimal calculatedPercentage = value
                .multiply(percentage)
                .divide(new BigDecimal(100));
        return value.add(calculatedPercentage);
    }

    protected Timestamp getTimestampFromString(String timestamp) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS");
        final LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(timestamp));
        return Timestamp.valueOf(localDateTime);
    }
}
