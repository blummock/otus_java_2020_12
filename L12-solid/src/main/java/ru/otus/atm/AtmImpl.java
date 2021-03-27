package ru.otus.atm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class AtmImpl implements Atm {

    AtmImpl(List<Cell> cells) {
        this.cells = new TreeSet<>((o1, o2) -> o2.getNominal() - o1.getNominal());
        this.cells.addAll(cells);
    }

    @Override
    public List<Coin> getCoins(int amount) {
        var min = cells.last().getNominal();
        if (amount % min != 0) {
            throw new AtmException("Amount must Be divisible " + min);
        }
        Map<Integer, Integer> toGet = new HashMap<>();
        for (var cell : cells) {
            int count = 0;
            for (int i = 0; i < cell.getRemains(); i++) {
                if ((amount -= cell.getNominal()) < 0) {
                    amount += cell.getNominal();
                    break;
                } else {
                    count++;
                }
            }
            toGet.put(cell.getNominal(), count);
        }
        if (amount != 0) {
            throw new AtmException("Not Found Money");
        }
        return cells.stream().flatMap(c -> c.getCoins(toGet.get(c.getNominal())).stream()).collect(Collectors.toList());
    }

    @Override
    public Map<Integer, Integer> getRemains() {
        return cells.stream().collect(Collectors.toMap(Cell::getNominal, Cell::getRemains));
    }

    private final TreeSet<Cell> cells;
}
