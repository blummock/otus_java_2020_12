package ru.otus.atm.coins;

import ru.otus.atm.Coin;

public class Coins500 implements Coin {
    @Override
    public Integer getNominal() {
        return 500;
    }
}
