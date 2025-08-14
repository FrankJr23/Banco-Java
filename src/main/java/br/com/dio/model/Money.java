package br.com.dio.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class Money {
    private final MoneyAudit history;

    public Money(MoneyAudit history) {
        this.history = history;
    }
}