package common;

public enum RequestMapping {
    START("/start"),
    CURRENCY_CONVERSION("/currency_conversion"),
    SELECT_CURRENCY("/select_currency"),
    CURRENCY("/currency_"),
    NOTHING(""),
    BACK("/back"),
    DELETE("/delete")
    ;

    private String command;
    private String metaInfo;

    public String getMetaInfo() {
        return metaInfo;
    }

    public String getCommand() {
        return command;
    }

    RequestMapping(String command) {
        this.command = command;
    }

    public static RequestMapping getInstance(String command) {
        for (var item : RequestMapping.values())
            if (item.command.equals(command))
                return item;
        if (command.contains(CURRENCY.command)) {
            var ret = CURRENCY;
            ret.metaInfo = command.replace(CURRENCY.command, "");
            return ret;
        }
        return NOTHING;
    }
}
