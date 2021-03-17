package ru.otus.atm.coins;

import ru.otus.atm.Coin;
import ru.otus.atm.Nominals;

import static ru.otus.atm.Nominals.FIVE_HUNDRED;

public class Coins500 implements Coin {
    @Override
    public Nominals getNominal() {
        return FIVE_HUNDRED;
    }
}
