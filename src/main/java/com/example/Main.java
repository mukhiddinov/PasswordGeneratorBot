package com.example;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                return;
            }
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String botUsername = properties.getProperty("bot.username");
        String botToken = properties.getProperty("bot.token");

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TelegramBot(botUsername, botToken));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
