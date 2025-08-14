package br.com.dio.model;

import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import static br.com.dio.model.BankService.ACCOUNT;

@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
public class AccountWallet extends Wallet {

    @ToString.Include
    private final List<String> pix;

    private Map<OffsetDateTime, List<MoneyAudit>> history;

    public AccountWallet(final List<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
    }

    public AccountWallet(final long amount, final List<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
        addMoney(amount, "Valor de criação da conta");
    }

    public void addMoney(final long amount, final String description) {
        var money = generateMoney(amount, description);
        this.money.addAll(money);
    }

    public List<String> getPix() {
        return this.pix;
    }

    public Map<OffsetDateTime, List<MoneyAudit>> getHistory() {
        return this.history;
    }
}