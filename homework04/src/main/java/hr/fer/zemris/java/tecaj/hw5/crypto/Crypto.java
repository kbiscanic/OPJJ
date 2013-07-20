package hr.fer.zemris.java.tecaj.hw5.crypto;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
	
	private static final int BUFFERSIZE = 4096;

	public static void main(String[] args) throws NoSuchAlgorithmException,
			IOException, InvalidKeyException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException {

		if (args.length != 2 && args.length != 3) {
			System.err.println("Program must be called with 2 or 3 arguments!");
			System.exit(-1);
		}

		args[0] = args[0].trim();

		if (args[0].equals("checksha")) {
			if (args.length == 2) {
				digest(args[1].trim());
			} else {
				System.err.println("Checksha must have 1 more argument!");
				System.exit(-1);
			}
		} else if (args[0].equals("crypt") || args[0].equals("decrypt")) {
			if (args.length == 3) {
				crypt(args[1].trim(), args[2].trim(), args[0].equals("crypt"));
			} else {
				System.err.println("Crypt/Decrypt must have 2 more arguments!");
				System.exit(-1);
			}
		} else {
			System.err.println("Invalid program arguments!");
		}

	}

	private static void crypt(String source, String dest, boolean crypt)
			throws IOException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException {
		System.out.println("Please provide password as hex-encoded text:");
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in, StandardCharsets.UTF_8));
		String line = reader.readLine();
		if (line == null) {
			throw new IOException();
		}
		String keyText = line.trim();

		SecretKeySpec keySpec = new SecretKeySpec(hextobyte(keyText), "AES");

		System.out
				.println("Please provide initialization vector as hex-encoded text:");
		line = reader.readLine();
		if (line == null) {
			throw new IOException();
		}
		String ivText = line.trim();
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(
				hextobyte(ivText));

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(crypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec,
				paramSpec);

		Path pathSource = Paths.get(source);
		InputStream fileInput = Files.newInputStream(pathSource,
				StandardOpenOption.READ);
		File outputFileCreator = new File(dest);
		Path pathDest = outputFileCreator.toPath();
		if (!outputFileCreator.exists()) {
			Files.createFile(pathDest);
		}
		OutputStream fileOutput = Files.newOutputStream(pathDest,
				StandardOpenOption.WRITE);
		byte[] read = new byte[BUFFERSIZE];
		byte[] output = new byte[BUFFERSIZE];
		int count = 0;
		while ((count = fileInput.read(read)) > 0) {
			int stored = cipher.update(read, 0, count, output);
			fileOutput.write(output, 0, stored);
		}

		int stored = cipher.doFinal(output, 0);
		fileOutput.write(output, 0, stored);
		fileOutput.flush();

		if (crypt) {
			System.out.println("Encryption completed. Generated file " + dest
					+ " based on file " + source + ".");
		} else {
			System.out.println("Decryption completed. Generated file " + dest
					+ " based on file " + source + ".");
		}

		fileInput.close();
		fileOutput.close();
	}

	private static void digest(String file) throws NoSuchAlgorithmException,
			IOException {

		MessageDigest mDigest = MessageDigest.getInstance("SHA-1");

		System.out.println("Please provide expected sha signature for " + file
				+ ":");
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in, StandardCharsets.UTF_8));

		String key = reader.readLine();

		Path path = Paths.get(file);
		InputStream fileInput = Files.newInputStream(path,
				StandardOpenOption.READ);
		byte[] read = new byte[BUFFERSIZE];
		int count = 0;
		while ((count = fileInput.read(read)) > 0) {
			mDigest.update(read, 0, count);
		}

		byte[] digest = mDigest.digest();
		if (bytetohex(digest).equals(key)) {
			System.out.println("Digesting completed. Digest of file " + file
					+ " matches expected digest.");
		} else {
			System.out.println("Digesting completed. Digest of file " + file
					+ " does not match the expected digest. Digest was: "
					+ bytetohex(digest));
		}

		fileInput.close();
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

	private static byte[] hextobyte(String hexEncoded) {
		byte[] byteArray = new byte[hexEncoded.length() / 2];
		int size = hexEncoded.length() / 2;

		for (int i = 0; i < size; i++) {
			byteArray[i] = (byte) ((Integer.decode("0X"
					+ hexEncoded.substring(2 * i, 2 * i + 2))) & 0xFF);
		}
		return byteArray;
	}
}
