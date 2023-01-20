package view;

import model.Currency;
import model.Money;

public interface MoneyDialog {
    Money get();

    public Currency getCurrencyTo();
}
