package ru.otus.atm.coins;

import ru.otus.atm.Coin;
import ru.otus.atm.Nominals;

import static ru.otus.atm.Nominals.ONE_THOUSAND;

public class Coins1000 implements Coin {
    @Override
    public Nominals getNominal() {
        return ONE_THOUSAND;
    }
}
