package ru.otus.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.atm.coins.Coins100;
import ru.otus.atm.coins.Coins1000;
import ru.otus.atm.coins.Coins500;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AtmTest {

    private Atm atm;

    @BeforeEach
    void setUp() {
        var cell100 = new CellImpl();
        var cell500 = new CellImpl();
        var cell1000 = new CellImpl();
        int initCount = 10;
        for (int i = 0; i < initCount; i++) {
            cell100.putCoin(new Coins100());
            cell500.putCoin(new Coins500());
            cell1000.putCoin(new Coins1000());
            atm = new AtmImpl(Arrays.asList(cell100, cell1000, cell500));
        }
    }

    @Test
    @DisplayName("Positive Get Nominals 1000 and 100")
    public void get1000and100CoinsTest() {
        var coins = atm.getCoins(1100);
        assert (coins.stream().filter(coin -> coin.getNominal().equals(1000)).count() == 1);
        assert (coins.stream().filter(coin -> coin.getNominal().equals(100)).count() == 1);
        var remains = atm.getRemains();
        assert remains.get(100) == 9;
        assert remains.get(500) == 10;
        assert remains.get(1000) == 9;
    }

    @Test
    @DisplayName("Positive Get Nominals 1000 and 500 and 100")
    public void get1000and500and100CoinsTest() {
        var coins = atm.getCoins(1600);
        assert (coins.stream().filter(coin -> coin.getNominal().equals(1000)).count() == 1);
        assert (coins.stream().filter(coin -> coin.getNominal().equals(500)).count() == 1);
        assert (coins.stream().filter(coin -> coin.getNominal().equals(100)).count() == 1);
        var remains = atm.getRemains();
        assert remains.get(100) == 9;
        assert remains.get(500) == 9;
        assert remains.get(1000) == 9;
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