package ru.otus.atm;

import java.util.Map;

public interface CellsInterface {

    Map<Nominals, Integer> getRemains();

    <T extends Coin> boolean putCoin(T coin);

    Coin getCoin(Nominals nominal);
}
