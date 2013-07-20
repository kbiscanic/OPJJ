package hr.infinum.fer.models;

import java.io.Serializable;

/**
 * Model for contact.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class Contact implements Serializable {

	private static final long serialVersionUID = -8130314064170846019L;
	private String name;
	private String phone;
	private String email;
	private String image;
	private String note;
	private String facebook_profile;

	public Contact(String name, String phone, String email, String image,
			String note, String facebook_profile) {
		super();
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.image = image;
		this.note = note;
		this.facebook_profile = facebook_profile;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getFacebook_profile() {
		return facebook_profile;
	}

	public void setFacebook_profile(String facebook_profile) {
		this.facebook_profile = facebook_profile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
