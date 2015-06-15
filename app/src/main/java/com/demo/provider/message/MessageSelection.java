package com.demo.provider.message;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.demo.provider.base.AbstractSelection;

/**
 * Selection for the {@code message} table.
 */
public class MessageSelection extends AbstractSelection<MessageSelection> {
    @Override
    protected Uri baseUri() {
        return MessageColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code MessageCursor} object, which is positioned before the first entry, or null.
     */
    public MessageCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new MessageCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public MessageCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public MessageCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public MessageSelection id(long... value) {
        addEquals("message." + MessageColumns._ID, toObjectArray(value));
        return this;
    }

    public MessageSelection messageId(long... value) {
        addEquals(MessageColumns.MESSAGE_ID, toObjectArray(value));
        return this;
    }

    public MessageSelection messageIdNot(long... value) {
        addNotEquals(MessageColumns.MESSAGE_ID, toObjectArray(value));
        return this;
    }

    public MessageSelection messageIdGt(long value) {
        addGreaterThan(MessageColumns.MESSAGE_ID, value);
        return this;
    }

    public MessageSelection messageIdGtEq(long value) {
        addGreaterThanOrEquals(MessageColumns.MESSAGE_ID, value);
        return this;
    }

    public MessageSelection messageIdLt(long value) {
        addLessThan(MessageColumns.MESSAGE_ID, value);
        return this;
    }

    public MessageSelection messageIdLtEq(long value) {
        addLessThanOrEquals(MessageColumns.MESSAGE_ID, value);
        return this;
    }

    public MessageSelection messageText(String... value) {
        addEquals(MessageColumns.MESSAGE_TEXT, value);
        return this;
    }

    public MessageSelection messageTextNot(String... value) {
        addNotEquals(MessageColumns.MESSAGE_TEXT, value);
        return this;
    }

    public MessageSelection messageTextLike(String... value) {
        addLike(MessageColumns.MESSAGE_TEXT, value);
        return this;
    }

    public MessageSelection messageTextContains(String... value) {
        addContains(MessageColumns.MESSAGE_TEXT, value);
        return this;
    }

    public MessageSelection messageTextStartsWith(String... value) {
        addStartsWith(MessageColumns.MESSAGE_TEXT, value);
        return this;
    }

    public MessageSelection messageTextEndsWith(String... value) {
        addEndsWith(MessageColumns.MESSAGE_TEXT, value);
        return this;
    }

    public MessageSelection isSenderInPhoneContact(String... value) {
        addEquals(MessageColumns.IS_SENDER_IN_PHONE_CONTACT, value);
        return this;
    }

    public MessageSelection isSenderInPhoneContactNot(String... value) {
        addNotEquals(MessageColumns.IS_SENDER_IN_PHONE_CONTACT, value);
        return this;
    }

    public MessageSelection isSenderInPhoneContactLike(String... value) {
        addLike(MessageColumns.IS_SENDER_IN_PHONE_CONTACT, value);
        return this;
    }

    public MessageSelection isSenderInPhoneContactContains(String... value) {
        addContains(MessageColumns.IS_SENDER_IN_PHONE_CONTACT, value);
        return this;
    }

    public MessageSelection isSenderInPhoneContactStartsWith(String... value) {
        addStartsWith(MessageColumns.IS_SENDER_IN_PHONE_CONTACT, value);
        return this;
    }

    public MessageSelection isSenderInPhoneContactEndsWith(String... value) {
        addEndsWith(MessageColumns.IS_SENDER_IN_PHONE_CONTACT, value);
        return this;
    }

    public MessageSelection sender(String... value) {
        addEquals(MessageColumns.SENDER, value);
        return this;
    }

    public MessageSelection senderNot(String... value) {
        addNotEquals(MessageColumns.SENDER, value);
        return this;
    }

    public MessageSelection senderLike(String... value) {
        addLike(MessageColumns.SENDER, value);
        return this;
    }

    public MessageSelection senderContains(String... value) {
        addContains(MessageColumns.SENDER, value);
        return this;
    }

    public MessageSelection senderStartsWith(String... value) {
        addStartsWith(MessageColumns.SENDER, value);
        return this;
    }

    public MessageSelection senderEndsWith(String... value) {
        addEndsWith(MessageColumns.SENDER, value);
        return this;
    }

    public MessageSelection receiver(String... value) {
        addEquals(MessageColumns.RECEIVER, value);
        return this;
    }

    public MessageSelection receiverNot(String... value) {
        addNotEquals(MessageColumns.RECEIVER, value);
        return this;
    }

    public MessageSelection receiverLike(String... value) {
        addLike(MessageColumns.RECEIVER, value);
        return this;
    }

    public MessageSelection receiverContains(String... value) {
        addContains(MessageColumns.RECEIVER, value);
        return this;
    }

    public MessageSelection receiverStartsWith(String... value) {
        addStartsWith(MessageColumns.RECEIVER, value);
        return this;
    }

    public MessageSelection receiverEndsWith(String... value) {
        addEndsWith(MessageColumns.RECEIVER, value);
        return this;
    }

    public MessageSelection isReceived(String... value) {
        addEquals(MessageColumns.IS_RECEIVED, value);
        return this;
    }

    public MessageSelection isReceivedNot(String... value) {
        addNotEquals(MessageColumns.IS_RECEIVED, value);
        return this;
    }

    public MessageSelection isReceivedLike(String... value) {
        addLike(MessageColumns.IS_RECEIVED, value);
        return this;
    }

    public MessageSelection isReceivedContains(String... value) {
        addContains(MessageColumns.IS_RECEIVED, value);
        return this;
    }

    public MessageSelection isReceivedStartsWith(String... value) {
        addStartsWith(MessageColumns.IS_RECEIVED, value);
        return this;
    }

    public MessageSelection isReceivedEndsWith(String... value) {
        addEndsWith(MessageColumns.IS_RECEIVED, value);
        return this;
    }

    public MessageSelection time(String... value) {
        addEquals(MessageColumns.TIME, value);
        return this;
    }

    public MessageSelection timeNot(String... value) {
        addNotEquals(MessageColumns.TIME, value);
        return this;
    }

    public MessageSelection timeLike(String... value) {
        addLike(MessageColumns.TIME, value);
        return this;
    }

    public MessageSelection timeContains(String... value) {
        addContains(MessageColumns.TIME, value);
        return this;
    }

    public MessageSelection timeStartsWith(String... value) {
        addStartsWith(MessageColumns.TIME, value);
        return this;
    }

    public MessageSelection timeEndsWith(String... value) {
        addEndsWith(MessageColumns.TIME, value);
        return this;
    }

    public MessageSelection isRead(String... value) {
        addEquals(MessageColumns.IS_READ, value);
        return this;
    }

    public MessageSelection isReadNot(String... value) {
        addNotEquals(MessageColumns.IS_READ, value);
        return this;
    }

    public MessageSelection isReadLike(String... value) {
        addLike(MessageColumns.IS_READ, value);
        return this;
    }

    public MessageSelection isReadContains(String... value) {
        addContains(MessageColumns.IS_READ, value);
        return this;
    }

    public MessageSelection isReadStartsWith(String... value) {
        addStartsWith(MessageColumns.IS_READ, value);
        return this;
    }

    public MessageSelection isReadEndsWith(String... value) {
        addEndsWith(MessageColumns.IS_READ, value);
        return this;
    }
}
