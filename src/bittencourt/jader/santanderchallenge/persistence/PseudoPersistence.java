package bittencourt.jader.santanderchallenge.persistence;

import bittencourt.jader.santanderchallenge.entities.Price;
import bittencourt.jader.santanderchallenge.exceptions.InstrumentPriceNotFoundException;

import java.util.HashMap;

/**
 * Since the challenge doesn't specify clearly if I should or not implement a database, I assumed that a simple pseudo persistence would be enough.
 * I also assumed, considering this line: "Write a suitable test that gets the latest price," that a history with old fx prices isn't necessary. In a real
 * world certainly we would have a db storage for keep a price history, so we could draw graphs and things like this, but In this challange I just used a simple
 * HashMap to updated the fx price by instrumentName. Of course, with this I also assumed as a premise that messages will be processed in order.
 */
public class PseudoPersistence {

    private static PseudoPersistence instance;
    public final HashMap<String, Price> data;

    private PseudoPersistence() {
        data = new HashMap<>();
    }

    public static PseudoPersistence getInstance() {
        if (instance == null) {
            instance = new PseudoPersistence();
        }
        return instance;
    }

    public void persist(Price price) {
        data.put(price.instrumentName, price);
    }

    public Price getPriceByInstrumentName(String instrumentName) throws InstrumentPriceNotFoundException {
        if (data.containsKey(instrumentName)) {
            return data.get(instrumentName);
        } else {
            throw new InstrumentPriceNotFoundException("Instrument name" + instrumentName + "not found");
        }

    }
}
