package br.com.dio.repository;

import br.com.dio.expcetion.AccountNotFoundException;
import br.com.dio.expcetion.PixInUseException;
import br.com.dio.model.AccountWallet;
import br.com.dio.model.Money;
import br.com.dio.model.MoneyAudit;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static br.com.dio.repository.CommonsRepository.checkFundsForTransaction;


public class AccountRepository {

    private final List<AccountWallet> accounts = new ArrayList<>();

    public List<AccountWallet> list() {
        return this.accounts;
    }

    public AccountWallet create(final List<String> pix, final long initialFunds) {
        if (!accounts.isEmpty()) {
            var pixInUse = accounts.stream().flatMap(a -> a.getPix().stream()).toList();
            for (var p : pix) {
                if (pixInUse.contains(p)) {
                    throw new PixInUseException("O Pix'" + p + "'já esta em uso");
                }
            }
        }
        var newAccount = new AccountWallet(initialFunds, pix);
        accounts.add(newAccount);
        return newAccount;
    }

    public void deposit(final String pix, final long fundsAmount) {
        var target = findByPix(pix);

        target.addMoney(fundsAmount, "depósito");
    }

    public long withdraw(final String pix, final long amount) {
        var source = findByPix(pix);
        checkFundsForTransaction(source, amount);
        source.reduceMoney(amount); // Aqui a lista de moedas é retornada, mas não é usada.
        return amount;
    }

    public void transferMoney(final String sourcePix, final String targetPix, final long amount) {
        var source = findByPix(sourcePix);
        checkFundsForTransaction(source, amount);
        var target = findByPix(targetPix);

        // 1. Remove o dinheiro da conta de origem e armazena na variável 'transferedMoney'
        var transferedMoney = source.reduceMoney(amount);

        // 2. Adiciona o dinheiro (a lista de moedas) na conta de destino
        target.addMoney(transferedMoney);
    }

    public AccountWallet findByPix(final String pix) {
        return accounts.stream()
                .filter(a -> a.getPix().contains(pix))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("A conta com a chave pix '" + pix + "' não existe ou foi encerrada "));
    }

    public Map<MoneyAudit, Long> getHistory(String pix) {
        var account = findByPix(pix);
        return account.getAllMoney().stream()
                .collect(Collectors.groupingBy(Money::getHistory, Collectors.counting()));
    }
}
