package com.TibiaExp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.TibiaExp.controller.Controller;

@SpringBootApplication
public class TibiaExpApplication {

	public static void main(String[] args) throws TelegramApiException {
		ApplicationContext context;

		TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
		context = SpringApplication.run(TibiaExpApplication.class, args);
		botsApi.registerBot(new Controller("TeleTibiaExpBot", "7625745567:AAGE9mhr9bdzsBobFxGq-xLHYJ2evVj1G5A", context));
	}

}
