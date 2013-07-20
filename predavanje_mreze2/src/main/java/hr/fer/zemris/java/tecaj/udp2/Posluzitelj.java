package hr.fer.zemris.java.tecaj.udp2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Posluzitelj {

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("1 argument!!");
			System.exit(-1);
		}

		int port = Integer.parseInt(args[0]);

		@SuppressWarnings("resource")
		DatagramSocket pristupnaTocka = new DatagramSocket(port);

		while (true) {
			DatagramPacket paket = new DatagramPacket(new byte[1024], 1024);
			pristupnaTocka.receive(paket);

			ByteArrayInputStream bis = new ByteArrayInputStream(
					paket.getData(), paket.getOffset(), paket.getLength());
			DataInputStream dis = new DataInputStream(bis);

			byte operacija = dis.readByte();
			int brojOperanada = dis.readShort();
			double rezultat = dis.readDouble();

			for (int i = 1; i < brojOperanada; i++) {
				switch (operacija) {
				case '+':
					rezultat += dis.readDouble();
					break;
				case '-':
					rezultat -= dis.readDouble();
					break;
				case '*':
					rezultat *= dis.readDouble();
					break;
				case '/':
					rezultat /= dis.readDouble();
					break;
				default:
					break;
				}
			}

			System.out.println("Rezultat je: " + rezultat + "\n");

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			dos.writeDouble(rezultat);
			byte[] odgovor = bos.toByteArray();
			DatagramPacket paket2 = new DatagramPacket(odgovor, odgovor.length);
			paket2.setAddress(paket.getAddress());
			paket2.setPort(paket.getPort());
			pristupnaTocka.send(paket2);
		}
	}
}
