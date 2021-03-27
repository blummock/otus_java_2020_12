package ru.otus.atm.coins;

import ru.otus.atm.Coin;

public class Coins100 implements Coin {
    @Override
    public Integer getNominal() {
        return 100;
    }
}
