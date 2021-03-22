package ru.otus.atm.coins;

import ru.otus.atm.Coin;

public class Coins1000 implements Coin {
    @Override
    public Integer getNominal() {
        return 1000;
    }
}
