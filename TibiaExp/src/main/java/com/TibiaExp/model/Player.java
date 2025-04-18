package com.TibiaExp.model;

public class Player implements Comparable<Player>{

	String name;
	String exp;

	public Player(String name, String exp) {
		this.name = name;
		this.exp = exp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	@Override
	public String toString() {
		return name!= null && exp != null ? name + " va fer: " + exp + "." : "No hi ha dades per: " + name;
	}

	@Override
	public int compareTo(Player o) {
		return this.getExp() != null && o.getExp() != null ? this.getExp().compareTo(o.getExp()) : 0;
	}

	
}
