package ru.otus.atm;

import java.util.ArrayList;
import java.util.List;

public class CellImpl implements Cell {

    @Override
    public Integer getNominal() {
        return nominal;
    }

    @Override
    public Integer getRemains() {
        return store.size();
    }

    @Override
    public boolean putCoin(Coin coin) {
        nominal = coin.getNominal();
        return store.add(coin);
    }

    @Override
    public List<Coin> getCoins(int count) {
        var coins = new ArrayList<Coin>();
        for (int i = 0; i < count; i++) {
            coins.add(store.remove(store.size() - 1));
        }
        return coins;
    }

    private final ArrayList<Coin> store = new ArrayList<>();
    private Integer nominal;
}
