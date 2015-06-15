package com.demo.model;

/**
 * Created by SHASHANK on 14-06-2015.
 */
public class Message {
    private String messageId;
    private String messageText;
    private String isSenderInPhoneContact;
    private String sender;
    private String receiver;
    private String isReceived;
    private String time;
    private String is_read;

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getIsSenderInPhoneContact() {
        return isSenderInPhoneContact;
    }

    public void setIsSenderInPhoneContact(String isSenderInPhoneContact) {
        this.isSenderInPhoneContact = isSenderInPhoneContact;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getIsReceived() {
        return isReceived;
    }

    public void setIsReceived(String isReceived) {
        this.isReceived = isReceived;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
