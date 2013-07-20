package hr.fer.zemris.java.tecaj_14.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * Model for blog users containing automatically generated ID, first and last
 * name of user, his nick, email and SHA-1 hash of his password. Also contains
 * list containing references to all entries created by this user.
 * 
 */
@Entity
@Table(name = "blog_users")
public class BlogUser {

	private Long id;
	private String firstName;
	private String lastName;
	private String nick;
	private String email;
	private String passwordHash;
	private List<BlogEntry> usersEntries = new ArrayList<>();

	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("lastModifiedAt")
	public List<BlogEntry> getUsersEntries() {
		return usersEntries;
	}

	public void setUsersEntries(List<BlogEntry> usersEntries) {
		this.usersEntries = usersEntries;
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(length = 35, nullable = false)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(length = 35, nullable = false)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(length = 20, nullable = false, unique = true)
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	@Column(length = 255, nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(length = 40, nullable = false)
	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public void setPassword(String password) {

		try {
			MessageDigest mDigest = MessageDigest.getInstance("SHA-1");
			byte[] digest = mDigest.digest(password
					.getBytes(StandardCharsets.UTF_8));
			passwordHash = bytetohex(digest);
		} catch (NoSuchAlgorithmException e) {
			// SHA-1 exists, this line will never be executed.
		}
	}

	public boolean checkPassword(String password) {
		String enteredPwdHash = null;
		try {
			MessageDigest mDigest = MessageDigest.getInstance("SHA-1");
			byte[] digest = mDigest.digest(password
					.getBytes(StandardCharsets.UTF_8));
			enteredPwdHash = bytetohex(digest);
		} catch (NoSuchAlgorithmException e) {
			// SHA-1 exists, this line will never be executed.
		}

		return passwordHash.equalsIgnoreCase(enteredPwdHash);
	}

	private static String bytetohex(byte[] digest) {
		int size = digest.length;
		StringBuilder hexencoded = new StringBuilder();
		for (int i = 0; i < size; i++) {
			int down = digest[i] & 0xf;
			int up = digest[i] >> 4;
			up = up & 0xf;
			hexencoded.append(Integer.toHexString(up)).append(
					Integer.toHexString(down));

		}
		return hexencoded.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
