package br.com.dio.model;

import lombok.Getter;
import lombok.ToString;


import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@ToString(onlyExplicitlyIncluded = true)
public abstract class Wallet {

    @ToString.Include
    @Getter
    private final BankService service;

    protected final List<Money> money;

    public Wallet(BankService serviceType) {
        this.service = serviceType;
        this.money = new ArrayList<>();
    }

    protected List<Money> generateMoney(final long amount, final String description) {
        var history = new MoneyAudit(UUID.randomUUID(), service, description, OffsetDateTime.now());
        return Stream.generate(() -> new Money(history)).limit(amount).toList();
    }

    @ToString.Include
    public long getFunds() {
        return money.size();
    }

    public void addMoney(final List<Money> money) {
        this.money.addAll(money);
    }

    public void addMoney(final long amount, final String description) {
        var newMoney = generateMoney(amount, description);
        this.money.addAll(newMoney);
    }

    public List<Money> reduceMoney(final long amount) {
        List<Money> toRemove = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            toRemove.add(this.money.removeFirst());
        }
        return toRemove;
    }

    public List<Money> getAllMoney() {
        return this.money;
    }
}