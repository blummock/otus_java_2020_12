package ru.otus.atm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cells implements CellsInterface {

    private final Map<Nominals, List<Coin>> store = new HashMap<>();

    @Override
    public Map<Nominals, Integer> getRemains() {
        return store.entrySet()
                .stream().collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingInt(value -> value.getValue().size())));
    }

    @Override
    public boolean putCoin(Coin coin) {
        store.computeIfAbsent(coin.getNominal(), k -> new ArrayList<>());
        return store.get(coin.getNominal()).add(coin);
    }

    @Override
    public Coin getCoin(Nominals nominal) {
        var cell = store.get(nominal);
        return cell.remove(cell.size() - 1);
    }
}
