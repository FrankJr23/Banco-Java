package br.com.dio.repository;


import br.com.dio.expcetion.NotFundsEnoughException;
import br.com.dio.model.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static br.com.dio.model.BankService.ACCOUNT;
import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
public final class CommonsRepository {

    public static void checkFundsForTransaction(final Wallet source, final long amount) {
        if (source.getFunds() < amount) {
            throw new NotFundsEnoughException("Sua conta não tem dinheiro suficiente para essa transação");
        }
    }

    public static List<Money> generateMoney(final UUID transactionId, final long funds, final String description) {
        var history = new MoneyAudit(transactionId, ACCOUNT, description, OffsetDateTime.now());

        List<Money> moneyList = new ArrayList<>();
        for (int i = 0; i < funds; i++) {
            moneyList.add(new Money(history));
        }

        return moneyList;
    }

}
