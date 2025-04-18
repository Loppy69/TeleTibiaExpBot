package com.TibiaExp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.TibiaExp.model.Player;
import com.TibiaExp.service.ExpService;

@Component
public class Controller extends TelegramLongPollingBot {

	ExpService expService;
	private String botName;

	@SuppressWarnings("deprecation")
	public Controller() {

	}

	public Controller(String botName, String token, ApplicationContext context) {
		super(token);
		this.botName = botName;
		this.expService = (ExpService) context.getBean("ExpService");
	}

	@Override
	public void onUpdateReceived(Update update) {
		final Long chatId = update.getMessage().getChatId();
		final String ogText = update.getMessage().getText();
		final String text = ogText.toUpperCase();
		StringBuilder prettyMsg = new StringBuilder();
		Player p;
		List<Player> lP = new ArrayList<>();
		if (text != null && !text.isEmpty()) {
			if (text.equals("/ALL")) {
				lP = expService.getAllPeluixets();
				lP.stream().forEach(i -> prettyMsg.append(i.toString() + "\n"));
			}
			if (text.contains("/EXP")) {
				p = expService.getPlayer(ogText);
				prettyMsg.append(p.toString());
			}
			if (text.contains("/TOP")) {
				lP = expService.getAllPeluixets();
				if (lP != null && !lP.isEmpty()) {
					prettyMsg.append("El top exp d'ahir és: \n");
					prettyMsg.append(lP.get(0).toString());
				}
			}
			if (prettyMsg != null && !prettyMsg.isEmpty()) {
				enviarResposta(chatId, prettyMsg.toString());
			}
		}

	}

	private void enviarResposta(final Long chatId, String prettyMsg) {
		SendMessage msg = new SendMessage();
		msg.setChatId(chatId);
		msg.setText(prettyMsg);
		try {
			execute(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getBotUsername() {
		return botName;
	}

}
