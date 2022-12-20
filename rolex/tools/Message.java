package rolex.tools;

// mock
public class Message {

    public static MessageType MessageType;
    private String key;
    private String value;

    public void setType(MessageType type) {
        // mock
    }

    public Message setKey(String message) {
        this.key = message;
        return this;
    }

    public Message setValue(String value) {
        this.value = value;
        return this;
    }

    public static rolex.tools.MessageType getMessageType() {
        return MessageType;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}



