package hr.fer.zemris.java.tecaj.udp1;

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

			byte[] primljeno = new byte[paket.getLength()];
			byte[] buf = paket.getData();
			for (int i = 0, offset = paket.getOffset(); i < primljeno.length; i++) {
				primljeno[i] = buf[offset++];
			}
			System.out.println("Javio mi se klijent: " + paket.getAddress()
					+ ":" + paket.getPort());
			try {
				System.out.println("Poruka je: "
						+ StringUtil.otpakiraj(primljeno) + "\\n");
			} catch (Exception e) {
				System.out.println("Malicious!");
			}

			byte[] odgovor = StringUtil.pakiraj("OK");
			DatagramPacket paket2 = new DatagramPacket(odgovor, odgovor.length);
			paket2.setAddress(paket.getAddress());
			paket2.setPort(paket.getPort());
			pristupnaTocka.send(paket2);
		}
	}

}
