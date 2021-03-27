package ru.otus.atm;

import java.util.List;
import java.util.Map;

public interface Atm {

    List<Coin> getCoins(int amount);

    Map<Integer, Integer> getRemains();

}
