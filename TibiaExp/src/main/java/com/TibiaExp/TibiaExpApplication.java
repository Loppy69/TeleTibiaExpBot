package com.TibiaExp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.TibiaExp.service.TibiaExpService;

@SpringBootApplication
public class TibiaExpApplication {
	
	public static void main(String[] args)  throws TelegramApiException{
		
		 TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
		 botsApi.registerBot(new TibiaExpService("TeleTibiaExpBot","7625745567:AAGE9mhr9bdzsBobFxGq-xLHYJ2evVj1G5A"));
	}

}
