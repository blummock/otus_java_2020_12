package ru.otus.atm;

public enum Nominals {

    ONE_HUNDRED(100),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000);

    Nominals(int weight) {
        this.weight = weight;
    }

    private int weight;

    public int getWeight() {
        return weight;
    }
}
