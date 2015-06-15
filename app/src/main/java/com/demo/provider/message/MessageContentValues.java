package com.demo.provider.message;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.demo.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code message} table.
 */
public class MessageContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return MessageColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable MessageSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public MessageContentValues putMessageId(long value) {
        mContentValues.put(MessageColumns.MESSAGE_ID, value);
        return this;
    }


    public MessageContentValues putMessageText(@Nullable String value) {
        mContentValues.put(MessageColumns.MESSAGE_TEXT, value);
        return this;
    }

    public MessageContentValues putMessageTextNull() {
        mContentValues.putNull(MessageColumns.MESSAGE_TEXT);
        return this;
    }

    public MessageContentValues putIsSenderInPhoneContact(@Nullable String value) {
        mContentValues.put(MessageColumns.IS_SENDER_IN_PHONE_CONTACT, value);
        return this;
    }

    public MessageContentValues putIsSenderInPhoneContactNull() {
        mContentValues.putNull(MessageColumns.IS_SENDER_IN_PHONE_CONTACT);
        return this;
    }

    public MessageContentValues putSender(@Nullable String value) {
        mContentValues.put(MessageColumns.SENDER, value);
        return this;
    }

    public MessageContentValues putSenderNull() {
        mContentValues.putNull(MessageColumns.SENDER);
        return this;
    }

    public MessageContentValues putReceiver(@Nullable String value) {
        mContentValues.put(MessageColumns.RECEIVER, value);
        return this;
    }

    public MessageContentValues putReceiverNull() {
        mContentValues.putNull(MessageColumns.RECEIVER);
        return this;
    }

    public MessageContentValues putIsReceived(@Nullable String value) {
        mContentValues.put(MessageColumns.IS_RECEIVED, value);
        return this;
    }

    public MessageContentValues putIsReceivedNull() {
        mContentValues.putNull(MessageColumns.IS_RECEIVED);
        return this;
    }

    public MessageContentValues putTime(@Nullable String value) {
        mContentValues.put(MessageColumns.TIME, value);
        return this;
    }

    public MessageContentValues putTimeNull() {
        mContentValues.putNull(MessageColumns.TIME);
        return this;
    }

    public MessageContentValues putIsRead(@Nullable String value) {
        mContentValues.put(MessageColumns.IS_READ, value);
        return this;
    }

    public MessageContentValues putIsReadNull() {
        mContentValues.putNull(MessageColumns.IS_READ);
        return this;
    }
}
