package common;

public class User {
    private String firstname;
    private String lastname;
    private ActionType action;
    private CurrencyType currencyTo;
    private CurrencyType currencyFrom;
    private Integer msgId;

    public String getFirstname() {
        return firstname;
    }

    public User setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public User setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public ActionType getAction() {
        return action;
    }

    public User setAction(ActionType action) {
        this.action = action;
        return this;
    }

    public CurrencyType getCurrencyTo() {
        return currencyTo;
    }

    public User setCurrencyTo(CurrencyType currencyTo) {
        this.currencyTo = currencyTo;
        return this;
    }

    public CurrencyType getCurrencyFrom() {
        return currencyFrom;
    }

    public User setCurrencyFrom(CurrencyType currencyFrom) {
        this.currencyFrom = currencyFrom;
        return this;
    }

    public Integer getMsgId() {
        return msgId;
    }

    public User setMsgId(Integer msgId) {
        this.msgId = msgId;
        return this;
    }
}
