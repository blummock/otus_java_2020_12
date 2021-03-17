package ru.otus.atm.coins;

import ru.otus.atm.Coin;
import ru.otus.atm.Nominals;

import static ru.otus.atm.Nominals.ONE_HUNDRED;

public class Coins100 implements Coin {
    @Override
    public Nominals getNominal() {
        return ONE_HUNDRED;
    }
}
