package com.demo.provider.message;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.demo.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code message} table.
 */
public class MessageCursor extends AbstractCursor implements MessageModel {
    public MessageCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(MessageColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code message_id} value.
     */
    public long getMessageId() {
        Long res = getLongOrNull(MessageColumns.MESSAGE_ID);
        if (res == null)
            throw new NullPointerException("The value of 'message_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code message_text} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMessageText() {
        String res = getStringOrNull(MessageColumns.MESSAGE_TEXT);
        return res;
    }

    /**
     * Get the {@code is_sender_in_phone_contact} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getIsSenderInPhoneContact() {
        String res = getStringOrNull(MessageColumns.IS_SENDER_IN_PHONE_CONTACT);
        return res;
    }

    /**
     * Get the {@code sender} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getSender() {
        String res = getStringOrNull(MessageColumns.SENDER);
        return res;
    }

    /**
     * Get the {@code receiver} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getReceiver() {
        String res = getStringOrNull(MessageColumns.RECEIVER);
        return res;
    }

    /**
     * Get the {@code is_received} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getIsReceived() {
        String res = getStringOrNull(MessageColumns.IS_RECEIVED);
        return res;
    }

    /**
     * Get the {@code time} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getTime() {
        String res = getStringOrNull(MessageColumns.TIME);
        return res;
    }

    /**
     * Get the {@code is_read} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getIsRead() {
        String res = getStringOrNull(MessageColumns.IS_READ);
        return res;
    }
}
