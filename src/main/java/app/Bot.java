package app;

import common.ActionType;
import common.CurrencyType;
import common.RequestMapping;
import common.User;
import org.fluentd.logger.FluentLogger;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import service.app.AppService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    private static final String TAG = "tgbot";
    private static FluentLogger logger = FluentLogger.getLogger(TAG, "localhost", 8080);

    public Bot() { }

    public Bot(DefaultBotOptions options) {
        super(options);
    }

    private static final String TOKEN = "<TOKEN>";
    private static final String USERNAME = "<USERNAME>";

    private Long currentChatId;
    private Integer msgId;
    private HashMap<Long, User> users = new HashMap<>();

    private AppService appService = AppService.getInstance();

    public void onUpdateReceived(Update update) {
        logger.log(TAG, "info", "Update was received");
        if(update.hasMessage())
            msgHandler(update.getMessage());
        if(update.hasCallbackQuery())
            callbackHandler(update.getCallbackQuery());
    }

    private void callbackHandler(CallbackQuery callbackQuery) {
        logger.log(TAG, "info", "Callback data was received");
        currentChatId = callbackQuery.getMessage().getChatId();
        msgId = callbackQuery.getMessage().getMessageId();

        var cmd = RequestMapping.getInstance(callbackQuery.getData());
        if(!users.containsKey(currentChatId)){
            if(cmd != RequestMapping.DELETE)
                _execute(appService.onFail());
            deleteMsg(callbackQuery.getMessage().getMessageId());
            return;
        }
        users.get(currentChatId).setMsgId(callbackQuery.getMessage().getMessageId());
        switch (cmd){
            case SELECT_CURRENCY:_execute(appService.selectCurrency());return;
            case CURRENCY:_execute(appService.afterSelectingCurrency(users.get(currentChatId), cmd.getMetaInfo()));return;
            case CURRENCY_CONVERSION:_execute(appService.currencyConversion(users.get(currentChatId)));return;
            case BACK: _execute(appService.menu());return;
        }
    }

    private void msgHandler(Message message) {
        logger.log(TAG, "info", "Message was received");
        currentChatId = message.getChatId();
        msgId = message.getMessageId();
        if(!users.containsKey(currentChatId))
            users.put(currentChatId, new User()
                                    .setAction(ActionType.NOTHING)
                    .setCurrencyFrom(CurrencyType.UAH)
                    .setFirstname(message.getChat().getFirstName())
                    .setLastname(message.getChat().getLastName())
            );
        var cmd = RequestMapping.getInstance(message.getText());
        switch (cmd){
            case START: _execute(appService.startMsg());return;
        }
        var user = users.get(currentChatId);
        switch (user.getAction()){
            case INPUT_VALUE:
                deleteMsg(message.getMessageId());
                _execute(appService.conversion(user, message.getText()));return;
        }

    }

    private void deleteMsg(Integer msgId){
        try {
            execute(new DeleteMessage().setChatId(currentChatId).setMessageId(msgId));
        } catch (TelegramApiException e) {
            e.printStackTrace();
            logger.log(TAG, "exception", e);
        }
    }

    private void _execute(EditMessageText msg){
        msg.setChatId(currentChatId);
        msg.setMessageId(users.get(currentChatId).getMsgId());
        try {
            execute(msg);
            logger.log(TAG, "response", msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            logger.log(TAG, "exception", e);
        }
    }

    private void _execute(SendMessage sendMessage){
        sendMessage.setChatId(currentChatId);
        try {
            execute(sendMessage);
            logger.log(TAG, "response", sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            logger.log(TAG, "exception", e);
        }
    }

    public void onUpdatesReceived(List<Update> updates) {
        updates.forEach(u->onUpdateReceived(u));
    }

    public String getBotUsername() {
        return USERNAME;
    }

    public String getBotToken() {
        return TOKEN;
    }
}
