package com.demo.provider.message;

import com.demo.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data model for the {@code message} table.
 */
public interface MessageModel extends BaseModel {

    /**
     * Get the {@code message_id} value.
     */
    long getMessageId();

    /**
     * Get the {@code message_text} value.
     * Can be {@code null}.
     */
    @Nullable
    String getMessageText();

    /**
     * Get the {@code is_sender_in_phone_contact} value.
     * Can be {@code null}.
     */
    @Nullable
    String getIsSenderInPhoneContact();

    /**
     * Get the {@code sender} value.
     * Can be {@code null}.
     */
    @Nullable
    String getSender();

    /**
     * Get the {@code receiver} value.
     * Can be {@code null}.
     */
    @Nullable
    String getReceiver();

    /**
     * Get the {@code is_received} value.
     * Can be {@code null}.
     */
    @Nullable
    String getIsReceived();

    /**
     * Get the {@code time} value.
     * Can be {@code null}.
     */
    @Nullable
    String getTime();

    /**
     * Get the {@code is_read} value.
     * Can be {@code null}.
     */
    @Nullable
    String getIsRead();
}
