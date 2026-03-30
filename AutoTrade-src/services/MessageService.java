package services;

import models.Message;
import models.User;
import storage.DataStore;
import java.util.*;

public class MessageService {

    private DataStore ds = DataStore.getInstance();

    public Message sendMessage(int senderId, int receiverId, String senderName, String text) {

        if (ds.findUserById(receiverId) == null)
            return null;

        Message msg = new Message(ds.nextMessageId(), senderId, receiverId, senderName, text);

        ds.addMessage(msg);

        return msg;
    }

    public List<Message> getConversation(int userId1, int userId2) {

        return ds.findMessagesBetween(userId1, userId2);
    }

    public void displayConversation(int userId1, int userId2) {

        List<Message> msgs = getConversation(userId1, userId2);

        if (msgs.isEmpty()) {
            System.out.println("  No messages yet.");
            return;
        }

        System.out.println("\n  ─────────── Conversation ───────────");

        for (Message m : msgs) {
            m.display();
        }

        System.out.println("  ─────────────────────────────────────");
    }

    // ✅ FIXED METHOD (returns List<User> instead of void)
    public List<User> getContacts(int userId) {

        Set<Integer> contactIds = new HashSet<>();
        List<User> contacts = new ArrayList<>();

        for (Message m : ds.getMessages()) {

            if (m.getSenderId() == userId) {
                contactIds.add(m.getReceiverId());
            }

            if (m.getReceiverId() == userId) {
                contactIds.add(m.getSenderId());
            }
        }

        for (int id : contactIds) {

            User u = ds.findUserById(id);

            if (u != null) {
                contacts.add(u);
            }
        }

        return contacts;
    }
}