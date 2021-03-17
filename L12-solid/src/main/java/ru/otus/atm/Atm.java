package ru.otus.atm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Atm {

    Atm(CellsInterface cells) {
        this.cells = cells;
    }

    private final CellsInterface cells;

    public Map<Nominals, Integer> getRemains() {
        return cells.getRemains();
    }


    public List<Coin> getCoins(Integer amount) {
        var remainsEntry = cells.getRemains().entrySet()
                .stream().sorted((o1, o2) -> o2.getKey().getWeight() - o1.getKey().getWeight()).collect(Collectors.toList());
        var min = remainsEntry.get(remainsEntry.size() - 1).getKey().getWeight();
        if (amount % min != 0) {
            throw new AtmException("Amount must Be divisible " + min);
        }
        List<Nominals> toGet = new ArrayList<>();
        for (var line : remainsEntry) {
            for (int i = 0; i < line.getValue(); i++) {
                if ((amount -= line.getKey().getWeight()) < 0) {
                    amount += line.getKey().getWeight();
                    break;
                } else {
                    toGet.add(line.getKey());
                }
            }
        }
        if (amount != 0) {
            throw new AtmException("Not Found Money");
        }
        return toGet.stream().map(cells::getCoin).collect(Collectors.toList());
    }
}
