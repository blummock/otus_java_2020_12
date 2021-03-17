package ru.otus.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.atm.coins.Coins100;
import ru.otus.atm.coins.Coins1000;
import ru.otus.atm.coins.Coins500;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.otus.atm.Nominals.*;

class AtmTest {

    private Atm atm;

    @BeforeEach
    void setUp() {
        Cells cells = new Cells();
        int initCount = 10;
        for (int i = 0; i < initCount; i++) {
            cells.putCoin(new Coins100());
            cells.putCoin(new Coins500());
            cells.putCoin(new Coins1000());
        }
        atm = new Atm(cells);
    }

    @Test
    @DisplayName("Positive Get Nominals 1000 and 100")
    public void get1000and100CoinsTest() {
        var coins = atm.getCoins(1100);
        assert (coins.stream().filter(coin -> coin.getNominal().equals(ONE_THOUSAND)).count() == 1);
        assert (coins.stream().filter(coin -> coin.getNominal().equals(ONE_HUNDRED)).count() == 1);
        var remains = atm.getRemains();
        assert remains.get(ONE_HUNDRED) == 9;
        assert remains.get(FIVE_HUNDRED) == 10;
        assert remains.get(ONE_THOUSAND) == 9;
    }

    @Test
    @DisplayName("Positive Get Nominals 1000 and 500 and 100")
    public void get1000and500and100CoinsTest() {
        var coins = atm.getCoins(1600);
        assert (coins.stream().filter(coin -> coin.getNominal().equals(ONE_THOUSAND)).count() == 1);
        assert (coins.stream().filter(coin -> coin.getNominal().equals(FIVE_HUNDRED)).count() == 1);
        assert (coins.stream().filter(coin -> coin.getNominal().equals(ONE_HUNDRED)).count() == 1);
        var remains = atm.getRemains();
        assert remains.get(ONE_HUNDRED) == 9;
        assert remains.get(FIVE_HUNDRED) == 9;
        assert remains.get(ONE_THOUSAND) == 9;
    }

    @Test
    @DisplayName("Throw Amount must Be divisible x")
    public void getCoinsDivisibleTest() {
        assertThrows(AtmException.class, () -> atm.getCoins(10120));
    }

    @Test
    @DisplayName("Throw Not Found Money")
    public void getMuchCoinsTest() {
        assertThrows(AtmException.class, () -> atm.getCoins(50000));
    }

}