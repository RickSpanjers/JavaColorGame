package logging;

public class LogMessage {

    private String messageText;
    private LogLevel logLevel;

    public LogMessage(String messageText, LogLevel logLevel)
    {
        this.messageText = messageText;
        this.logLevel = logLevel;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public String getMessageText() {
        return messageText;
    }

}
