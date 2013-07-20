package hr.fer.zemris.java.tecaj.udp1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Klijent {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 3) {
			System.err.println("3 argumenta!!");
			System.exit(-1);
		}

		InetAddress adresaPosluzitelja = InetAddress.getByName(args[0]);
		int port = Integer.parseInt(args[1]);
		String tekstPoruke = args[2];

		DatagramSocket pristupnaTocka = new DatagramSocket();

		byte[] zaPoslati = StringUtil.pakiraj(tekstPoruke);
		DatagramPacket paket = new DatagramPacket(zaPoslati, zaPoslati.length);
		paket.setAddress(adresaPosluzitelja);
		paket.setPort(port);

		pristupnaTocka.send(paket);

		DatagramPacket primljeniPaket = new DatagramPacket(new byte[1024], 1024);
		pristupnaTocka.receive(primljeniPaket);

		byte[] primljeno = new byte[primljeniPaket.getLength()];
		byte[] buf = primljeniPaket.getData();
		for (int i = 0, offset = primljeniPaket.getOffset(); i < primljeno.length; i++) {
			primljeno[i] = buf[offset++];
		}

		System.out.println("Odgovor je " + StringUtil.otpakiraj(primljeno));

		pristupnaTocka.close();

	}
}
