package service.app;

import common.CurrencyType;
import common.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import service.keyboard.KeyboardService;

import static common.ActionType.*;
import static common.RequestMapping.START;

public class AppService {

    private static AppService service;
    private KeyboardService keyboardService = KeyboardService.getInstance();

    public static AppService getInstance() {
        if (service == null)
            service = new AppService();
        return service;
    }

    private AppService() {
    }

    public SendMessage startMsg() {
        return new SendMessage()
                .setText("Welcome")
                .setReplyMarkup(keyboardService.startKeyboard());
    }

    public EditMessageText selectCurrency() {
        return new EditMessageText()
                .setText("Select currency")
                .setReplyMarkup(keyboardService.currenciesKeyboard());
    }

    public EditMessageText menu() {
        return new EditMessageText()
                .setText("Main menu")
                .setReplyMarkup(keyboardService.startKeyboard());
    }

    public EditMessageText afterSelectingCurrency(User user, String currency) {
        user.setCurrencyTo(CurrencyType.getInstance(currency));
        return new EditMessageText()
                .setText("You can convert now")
                .setReplyMarkup(keyboardService.startKeyboard());
    }

    public EditMessageText conversion(User user, String amount) {
        user.setAction(NOTHING);
        var amountInt = Integer.parseInt(amount);
        var builder = new StringBuilder();
        builder
                .append("Dear "+user.getFirstname()+" "+user.getLastname()+"!")
                .append("\n")
                .append("Your amount "+amount+" "+(user.getCurrencyFrom().getEmoji() == null ? user.getCurrencyFrom().getCurrency().toUpperCase() : user.getCurrencyFrom().getEmoji()))
                .append(" is "+amountInt/user.getCurrencyTo().getValue()+" "+(user.getCurrencyTo().getEmoji() == null ? user.getCurrencyTo().getCurrency().toUpperCase() : user.getCurrencyTo().getEmoji()));
        return new EditMessageText()
                .setText(builder.toString())
                .setReplyMarkup(keyboardService.startKeyboard());
    }

    public EditMessageText currencyConversion(User user) {
        user.setAction(INPUT_VALUE);
        return new EditMessageText()
                .setText("Enter the amount ")
                .setReplyMarkup(keyboardService.backKeyboard());
    }

    public SendMessage onFail() {
        return new SendMessage()
                .setText("Get started with a command '"+START.getCommand()+"'")
                .setReplyMarkup(keyboardService.deleteMsg());
    }
}
