package com.demo.provider.message;

import android.net.Uri;
import android.provider.BaseColumns;

import com.demo.provider.HKPProvider;
import com.demo.provider.message.MessageColumns;

/**
 * Columns for the {@code message} table.
 */
public class MessageColumns implements BaseColumns {
    public static final String TABLE_NAME = "message";
    public static final Uri CONTENT_URI = Uri.parse(HKPProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String MESSAGE_ID = "message_id";

    public static final String MESSAGE_TEXT = "message_text";

    public static final String IS_SENDER_IN_PHONE_CONTACT = "is_sender_in_phone_contact";

    public static final String SENDER = "sender";

    public static final String RECEIVER = "receiver";

    public static final String IS_RECEIVED = "is_received";

    public static final String TIME = "time";

    public static final String IS_READ = "is_read";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            MESSAGE_ID,
            MESSAGE_TEXT,
            IS_SENDER_IN_PHONE_CONTACT,
            SENDER,
            RECEIVER,
            IS_RECEIVED,
            TIME,
            IS_READ
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(MESSAGE_ID) || c.contains("." + MESSAGE_ID)) return true;
            if (c.equals(MESSAGE_TEXT) || c.contains("." + MESSAGE_TEXT)) return true;
            if (c.equals(IS_SENDER_IN_PHONE_CONTACT) || c.contains("." + IS_SENDER_IN_PHONE_CONTACT)) return true;
            if (c.equals(SENDER) || c.contains("." + SENDER)) return true;
            if (c.equals(RECEIVER) || c.contains("." + RECEIVER)) return true;
            if (c.equals(IS_RECEIVED) || c.contains("." + IS_RECEIVED)) return true;
            if (c.equals(TIME) || c.contains("." + TIME)) return true;
            if (c.equals(IS_READ) || c.contains("." + IS_READ)) return true;
        }
        return false;
    }

}
