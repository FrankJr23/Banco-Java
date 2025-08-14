package br.com.dio.model;

import lombok.Getter;
import lombok.ToString;


import static br.com.dio.model.BankService.INVESTMENT;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.List;

@ToString
@Getter
public class InvestmentWallet extends Wallet {

    private final Investment investment;
    private final AccountWallet account;

    public InvestmentWallet(final Investment investment, final AccountWallet account, final long amount) {
        super(INVESTMENT);
        this.investment = investment;
        this.account = account;
        // Pega o dinheiro da conta e o adiciona à carteira de investimento
        addMoney(account.reduceMoney(amount));
    }

    public InvestmentWallet(BankService serviceType, Investment investment, AccountWallet account) {
        super(serviceType);
        this.investment = investment;
        this.account = account;
    }

    public void updateAmount(final long percent) {
        var amount = getFunds() * percent / 100;
        var history = new MoneyAudit(UUID.randomUUID(), getService(), "rendimentos", OffsetDateTime.now());
        var money = Stream.generate(() -> new Money(history)).limit(amount).toList();
        this.money.addAll(money);
    }
}