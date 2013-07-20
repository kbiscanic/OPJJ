package hr.fer.zemris.java.tecaj.udp1;

import java.nio.charset.StandardCharsets;

public class StringUtil {

	public static byte[] serijalizira(String text) {
		return text.getBytes(StandardCharsets.UTF_8);
	}

	public static String deserijaliziraj(byte[] bytes) {
		return new String(bytes, StandardCharsets.UTF_8);
	}

	public static byte[] pakiraj(String text) {
		byte[] ser = serijalizira(text);
		int velicina = ser.length;

		byte[] ukupni = new byte[velicina + 2];

		byte vazniji = (byte) ((velicina >> 8) & 0xFF);
		byte nevazniji = (byte) (velicina & 0xFF);

		ukupni[0] = vazniji;
		ukupni[1] = nevazniji;
		for (int i = 0; i < velicina; i++) {
			ukupni[i + 2] = ser[i];
		}

		return ukupni;
	}

	public static String otpakiraj(byte[] podaci) {
		byte vazniji = podaci[0];
		byte nevazniji = podaci[1];

		int velicina = (vazniji << 8) | nevazniji;

		byte[] ser = new byte[velicina];
		for (int i = 0; i < velicina; i++) {
			ser[i] = podaci[i + 2];
		}

		return deserijaliziraj(ser);
	}
}
