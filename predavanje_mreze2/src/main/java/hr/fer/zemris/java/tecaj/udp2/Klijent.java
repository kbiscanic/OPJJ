package hr.fer.zemris.java.tecaj.udp2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
		if (args.length < 5) {
			System.err.println("Barem 5 argumenata!!");
			System.exit(-1);
		}

		InetAddress adresaPosluzitelja = InetAddress.getByName(args[0]);
		int port = Integer.parseInt(args[1]);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		String operacija = args[2];
		if (operacija.length() != 1 || !"+-*/".contains(operacija)) {
			System.out.println("Operacija ne valja!");
		}
		dos.writeByte(operacija.charAt(0));

		dos.writeShort(args.length - 3);

		for (int i = 3; i < args.length; i++) {
			dos.writeDouble(Double.parseDouble(args[i]));
		}

		byte[] zaPoslati = bos.toByteArray();

		DatagramSocket pristupnaTocka = new DatagramSocket();

		DatagramPacket paket = new DatagramPacket(zaPoslati, zaPoslati.length);
		paket.setAddress(adresaPosluzitelja);
		paket.setPort(port);

		pristupnaTocka.send(paket);

		DatagramPacket primljeniPaket = new DatagramPacket(new byte[1024], 1024);
		pristupnaTocka.receive(primljeniPaket);

		ByteArrayInputStream bis = new ByteArrayInputStream(
				primljeniPaket.getData(), primljeniPaket.getOffset(),
				primljeniPaket.getLength());
		DataInputStream dis = new DataInputStream(bis);

		System.out.println("Odgovor je " + dis.readDouble());

		pristupnaTocka.close();

	}
}
