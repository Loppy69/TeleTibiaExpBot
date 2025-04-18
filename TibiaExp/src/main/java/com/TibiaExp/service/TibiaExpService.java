package com.TibiaExp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.TibiaExp.model.Player;

@Service
public class TibiaExpService extends TelegramLongPollingBot {

	private final List<String> PELUIXETS = Arrays.asList("Peluixet Destroyer", "Peluixet", "Toxic Peluixas",
			"Lord Portime");
	private String botName;

	public TibiaExpService(String botName, String token) {
		super(token);
		this.botName = botName;
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
				lP = getAllPeluixets();
				lP.stream().forEach(i -> prettyMsg.append(i.toString() + "\n"));
			}
			if (text.contains("/EXP")) {
				p = getPlayer(ogText);
				prettyMsg.append(p.toString());
			}
			if (text.contains("/TOP")) {
				lP = getAllPeluixets();
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

	private Player getPlayer(String text) {
		String player = text.substring(text.indexOf(" ")).trim();
		Player p = new Player(player, getExpByCharName(player));
		return p;
	}

	private List<Player> getAllPeluixets() {
		List<Player> l = new ArrayList<>();
		Player p = null;
		for (String item : PELUIXETS) {
			p = new Player(item, getExpByCharName(item));
			l.add(p);
		}
		Collections.sort(l);
		Collections.reverse(l);
		return l;
	}

	public String getExpByCharName(String name) {
		String exp = null;
		if (name != null && !name.isEmpty()) {
			name = name.trim();
			try {
				Document doc = Jsoup.connect("https://guildstats.eu/character?nick=" + name + "&tab=9").get();
				Elements tr = doc.select(".newTable").get(1).select("thead").select("tr");
				Element lastTr = tr.get(tr.size() - 1);
				exp = lastTr.select("td").get(1).text().replace("+", "");
			} catch (Exception e) {
				System.out.println("Error: " + "No s'han trobat dades per " + name);
			}
		}
		return exp;
	}

	@Override
	public String getBotUsername() {
		return botName;
	}

}
