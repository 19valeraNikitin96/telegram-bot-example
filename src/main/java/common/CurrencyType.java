package common;

public enum CurrencyType {
    UAH("uah", 1.0, null),
    USD("usd", 24.9, "\uD83D\uDCB5"),
    EURO("euro", 27.5, "\uD83D\uDCB6"),
    GBP("gbp", 30.0, "\uD83D\uDCB7");

    private String currency;
    private Double value;
    private String emoji;

    public String getCurrency() {
        return currency;
    }

    public Double getValue() {
        return value;
    }

    public String getEmoji() {
        return emoji;
    }

    CurrencyType(String currency, Double value, String emoji) {
        this.currency = currency;
        this.value = value;
        this.emoji = emoji;
    }

    public static CurrencyType getInstance(String currency) {
        for (var item : CurrencyType.values())
            if (item.currency.equals(currency))
                return item;
        return USD;
    }
}
