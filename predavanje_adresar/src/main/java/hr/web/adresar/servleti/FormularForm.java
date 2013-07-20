package hr.web.adresar.servleti;

import hr.web.adresar.Record;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class FormularForm {

	private String id;
	private String prezime;
	private String ime;
	private String email;

	private Map<String, String> greske = new HashMap<>();

	public boolean imaGresaka() {
		return !greske.isEmpty();
	}

	public boolean imaGresku(String prop) {
		return greske.containsKey(prop);
	}

	public String dohvatiPogresku(String prop) {
		return greske.get(prop);
	}

	public void popuniIzHttpRequesta(HttpServletRequest req) {
		this.id = pripremi(req.getParameter("id"));
		this.ime = pripremi(req.getParameter("ime"));
		this.prezime = pripremi(req.getParameter("prezime"));
		this.email = pripremi(req.getParameter("email"));

	}

	public void popuniIzRecorda(Record r) {
		this.id = r.getId() == null ? "" : r.getId().toString();
		this.ime = pripremi(r.getIme());
		this.prezime = pripremi(r.getPrezime());
		this.email = pripremi(r.getEmail());
	}

	public void popuniURecord(Record r) {
		r.setId(this.id.isEmpty() ? null : Long.valueOf(this.id));
		r.setIme(this.ime);
		r.setPrezime(this.prezime);
		r.setEmail(this.email);
	}

	public void validiraj() {
		if (!id.isEmpty()) {
			try {
				Long.parseLong(id);
			} catch (NumberFormatException nfe) {
				greske.put("id", "Identifikator je pogrešnog formata.");
			}
		}
		if (prezime.isEmpty()) {
			greske.put("prezime", "Prezime je obavezno.");
		}
		if (ime.isEmpty()) {
			greske.put("ime", "Ime je obavezno.");
		}
		if (email.isEmpty()) {
			greske.put("email", "Email je obavezan.");
		} else {
			int p = email.indexOf('@');
			int d = email.lastIndexOf('.');
			if (p < 1 || p > d || email.lastIndexOf('@') != p) {
				greske.put("email", "E-mail je pogrešnog formata.");
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private String pripremi(String s) {
		if (s == null) {
			return "";
		}
		return s.trim();
	}

}
