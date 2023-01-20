package view.swing;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import model.Currency;
import model.Money;
import view.MoneyDialog;

public class SwingMoneyDialog extends JPanel implements MoneyDialog{

    private Currency currencyFrom;
    private Currency currencyTo;
    private String amount;
    private final Currency[] currencies;

    public SwingMoneyDialog(Currency[] currencies) {
        this.currencies = currencies;
        this.add(amount());
        this.add(currencyFrom());
        this.add(currencyTo());
    }

       
    @Override
    public Money get() {
        return new Money(Double.parseDouble(this.amount), this.currencyFrom);
    }

    private Component amount() {
        JTextField textField = new JTextField();
        textField.setColumns(10);
        textField.getDocument().addDocumentListener(amountChanged());
        this.amount = textField.getText();
        return textField;
    }

    private Component currencyFrom() {
        JComboBox combo = new JComboBox(currencies);
        combo.addItemListener(currencyFromChanged());
        this.currencyFrom = (Currency) combo.getSelectedItem();
        return combo;
    }
    
    private Component currencyTo() {
        JComboBox combo = new JComboBox(currencies);
        combo.addItemListener(currencyToChanged());
        this.currencyTo = (Currency) combo.getSelectedItem();
        return combo;
    }

    private ItemListener currencyFromChanged() {
        return new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED) return;
                currencyFrom = (Currency) e.getItem();
            }
        };
    }
    
    private ItemListener currencyToChanged() {
        return new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED) return;
                currencyTo = (Currency) e.getItem();
            }
        };
    }

    private DocumentListener amountChanged() {
        return new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                amountChanged(e.getDocument());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                amountChanged(e.getDocument());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                amountChanged(e.getDocument());
            }

            private void amountChanged(Document document) {
                try {
                    amount = document.getText(0, document.getLength());
                } catch (BadLocationException ex) {
                    
                }
            }
            
            
        };
    }

    @Override
    public Currency getCurrencyTo() {
        return currencyTo;
    }
    
}
