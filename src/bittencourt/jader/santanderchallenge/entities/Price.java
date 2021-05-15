package bittencourt.jader.santanderchallenge.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Price {

    public final int uniqueId;
    public final String instrumentName;
    public final BigDecimal bid;
    public final BigDecimal ask;
    public final Timestamp timestamp;

    public Price(int uniqueId, String instrumentName, BigDecimal bid, BigDecimal ask, Timestamp timestamp) {
        this.uniqueId = uniqueId;
        this.instrumentName = instrumentName;
        this.bid = bid;
        this.ask = ask;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Price{" +
                "uniqueId=" + uniqueId +
                ", instrumentName='" + instrumentName + '\'' +
                ", bid=" + bid +
                ", ask=" + ask +
                ", timestamp=" + timestamp +
                '}';
    }
}
