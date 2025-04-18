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

import com.TibiaExp.model.Player;

@Service("ExpService")
public class ExpService {
	
	private final List<String> PELUIXETS = Arrays.asList("Peluixet Destroyer", "Peluixet", "Toxic Peluixas",
			"Lord Portime");

	public Player getPlayer(String text) {
		String player = text.substring(text.indexOf(" ")).trim();
		Player p = new Player(player, getExpByCharName(player));
		return p;
	}

	public List<Player> getAllPeluixets() {
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

}
