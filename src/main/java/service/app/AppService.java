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
                .setText("Добро пожаловать")
                .setReplyMarkup(keyboardService.startKeyboard());
    }

    public EditMessageText selectCurrency() {
        return new EditMessageText()
                .setText("Выберите валюту")
                .setReplyMarkup(keyboardService.currenciesKeyboard());
    }

    public EditMessageText menu() {
        return new EditMessageText()
                .setText("Главное меню")
                .setReplyMarkup(keyboardService.startKeyboard());
    }

    public EditMessageText afterSelectingCurrency(User user, String currency) {
        user.setCurrencyTo(CurrencyType.getInstance(currency));
        return new EditMessageText()
                .setText("Теперь вы можете конвертировать")
                .setReplyMarkup(keyboardService.startKeyboard());
    }

    public EditMessageText conversion(User user, String amount) {
        user.setAction(NOTHING);
        var amountInt = Integer.parseInt(amount);
        var builder = new StringBuilder();
        builder
                .append("Уважаемый "+user.getFirstname()+" "+user.getLastname()+"!")
                .append("\n")
                .append("Ваша сумма "+amount+" "+(user.getCurrencyFrom().getEmoji() == null ? user.getCurrencyFrom().getCurrency().toUpperCase() : user.getCurrencyFrom().getEmoji()))
                .append(" составляет "+amountInt/user.getCurrencyTo().getValue()+" "+(user.getCurrencyTo().getEmoji() == null ? user.getCurrencyTo().getCurrency().toUpperCase() : user.getCurrencyTo().getEmoji()));
        return new EditMessageText()
                .setText(builder.toString())
                .setReplyMarkup(keyboardService.startKeyboard());
    }

    public EditMessageText currencyConversion(User user) {
        user.setAction(INPUT_VALUE);
        return new EditMessageText()
                .setText("Введите сумму")
                .setReplyMarkup(keyboardService.backKeyboard());
    }

    public SendMessage onFail() {
        return new SendMessage()
                .setText("Начните работу с команды '"+START.getCommand()+"'")
                .setReplyMarkup(keyboardService.deleteMsg())
                ;
    }
}
