package com.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class TelegramBot extends TelegramLongPollingBot {

    private final List<Integer> sentMessageIds = new ArrayList<>();
    private static String SALT;
    private final String botUsername;
    private final String botToken;

    public TelegramBot(String botUsername, String botToken) {
        this.botUsername = botUsername;
        this.botToken = botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            String text = message.getText();

            Integer messageId = message.getMessageId();
            sentMessageIds.add(messageId);

            if (text.equalsIgnoreCase("/start")) {
                handleStartCommand(chatId);
                SALT = chatId.toString();
            }else if (text.equalsIgnoreCase("/clear")) {
                clearBotMessages(message.getChatId());
            }else if (text.startsWith("setSALT")) {
                String newSALT = text.substring("setSALT".length()).trim();
                updateSALT(newSALT);
            }
            else {
                String domain = message.getText().trim();
                String password = com.example.PasswordGenerator.generatePassword(domain, SALT);

                String responseText = "Generated password for domain \"" + domain + "\": " + password;
                sendResponse(chatId, responseText);
            }
        }
    }

    private void updateSALT(String newSALT) {
        SALT += newSALT;
    }

    private void sendResponse(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            Message executedMessage = execute(message);
            sentMessageIds.add(executedMessage.getMessageId());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
    private void sendResponseStart(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void clearBotMessages(Long chatId) {
        for (Integer messageId : sentMessageIds) {
            DeleteMessage deleteMessage = new DeleteMessage();
            deleteMessage.setChatId(chatId.toString());
            deleteMessage.setMessageId(messageId);
            try {
                execute(deleteMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        sentMessageIds.clear();
    }


    private void handleStartCommand(Long chatId) {
        long userId = chatId;
        String username = null;

        try {
            GetChat getChat = new GetChat(String.valueOf(userId));
            Chat chat = execute(getChat);
            username = chat.getUserName();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        sendResponseStart(chatId, String.format("Salom @%s! \nDomain to'liq nomini kiriting va sizga parol yaratiladi" +
                "\nsetSALT ... orqali maxsus kalitni o'rnatishingiz mumkin" +
                "\n/clear - maxfiylik va xavfsizlik uchun chat tarixi tozalanadi", username));
    }


    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
