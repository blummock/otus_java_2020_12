package ru.otus.atm;

import java.util.List;

public interface Cell {

    Integer getNominal();

    Integer getRemains();

    boolean putCoin(Coin coin);

    List<Coin> getCoins(int count);
}
