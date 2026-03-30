package models;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private int messageId;
    private int senderId;
    private int receiverId;
    private String senderName;
    private String messageText;
    private LocalDateTime timestamp;

    public Message(int messageId, int senderId, int receiverId, String senderName, String messageText) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.senderName = senderName;
        this.messageText = messageText;
        this.timestamp = LocalDateTime.now();
    }

    public int getMessageId() { return messageId; }
    public int getSenderId() { return senderId; }
    public int getReceiverId() { return receiverId; }
    public String getSenderName() { return senderName; }
    public String getMessageText() { return messageText; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public void display() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        System.out.printf("  [%s] %s: %s%n", timestamp.format(fmt), senderName, messageText);
    }
}
