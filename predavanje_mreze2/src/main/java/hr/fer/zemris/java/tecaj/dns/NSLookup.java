package hr.fer.zemris.java.tecaj.dns;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NSLookup {

	public static void main(String[] args) throws UnknownHostException {
		if (args.length != 1) {
			System.err.println("Ocekivao 1 argument!");
			System.exit(-1);
		}

		String simbolickoIme = args[0];

		InetAddress adresa = InetAddress.getByName(simbolickoIme);

		System.out.println("IP adresa je: " + adresa.getHostAddress());
	}

}
