package service.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static common.CurrencyType.*;
import static common.RequestMapping.*;

public class KeyboardService {

    private static KeyboardService service;

    public static KeyboardService getInstance() {
        if (service == null)
            service = new KeyboardService();
        return service;
    }

    private KeyboardService() {
    }

    public InlineKeyboardMarkup startKeyboard() {
        var selectCurrency = new InlineKeyboardButton()
                .setText("Выбрать валюту")
                .setCallbackData(SELECT_CURRENCY.getCommand());
        var currencyConversion = new InlineKeyboardButton()
                .setText("Конвертировать валюту")
                .setCallbackData(CURRENCY_CONVERSION.getCommand());
        var row1 = Arrays.asList(selectCurrency);
        var row2 = Arrays.asList(currencyConversion);
        return new InlineKeyboardMarkup()
                .setKeyboard(Arrays.asList(row1, row2));
    }

    public InlineKeyboardMarkup currenciesKeyboard() {
        var usd = new InlineKeyboardButton()
                .setText("\uD83D\uDCB5")
                .setCallbackData(CURRENCY.getCommand() + USD.getCurrency());
        var euro = new InlineKeyboardButton()
                .setText("\uD83D\uDCB6")
                .setCallbackData(CURRENCY.getCommand() + EURO.getCurrency());
        var gbp = new InlineKeyboardButton()
                .setText("\uD83D\uDCB7")
                .setCallbackData(CURRENCY.getCommand() + GBP.getCurrency());
        var row1 = Arrays.asList(usd);
        var row2 = Arrays.asList(euro);
        var row3 = Arrays.asList(gbp);
        return new InlineKeyboardMarkup()
                .setKeyboard(Arrays.asList(row1, row2, row3, backRow()));
    }

    private List<InlineKeyboardButton> backRow() {
        var back = new InlineKeyboardButton()
                .setText("Главное меню")
                .setCallbackData(BACK.getCommand());
        return Arrays.asList(back);
    }

    public InlineKeyboardMarkup backKeyboard() {
        return new InlineKeyboardMarkup().setKeyboard(Arrays.asList(backRow()));
    }

    public InlineKeyboardMarkup deleteMsg() {
        var delete = new InlineKeyboardButton()
                .setText("Удалить сообщение")
                .setCallbackData(DELETE.getCommand());
        return new InlineKeyboardMarkup().setKeyboard(Arrays.asList(Arrays.asList(delete)));

    }
}
