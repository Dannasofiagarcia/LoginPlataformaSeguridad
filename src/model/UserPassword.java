package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class UserPassword {

	public static String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		int iterations = 1000;
		char[] chars = password.toCharArray();
		byte[] salt = getRandomSalt();
		int keyLength = 512;

		PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, keyLength);

		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] hash = skf.generateSecret(spec).getEncoded();
		return iterations + ":" + toHex(salt) + ":" + toHex(hash);
	}

	private static byte[] getRandomSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

	private static String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}

	public static boolean validatePassword(String passwordIngresada, String usuario)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		try {
			boolean encontrado = false;
			String contraBD = "";
			FileReader fr = new FileReader(new File(".//src//bd//bd.txt"));
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null && !encontrado) {
				System.out.println(line);
				if (line.split(",")[0].equals(usuario)) {
					contraBD = line.split(",")[1];
					encontrado = true;
				}
			}
			System.out.println(line);
			fr.close();
			br.close();

			if (encontrado == false)
				return encontrado;

			if (contraBD.equals(" ")) {
				return true;
			}

			String[] parts = contraBD.split(":");
			int iterations = Integer.parseInt(parts[0]);
			byte[] salt = fromHex(parts[1]);
			byte[] hash = fromHex(parts[2]);

			PBEKeySpec spec = new PBEKeySpec(passwordIngresada.toCharArray(), salt, iterations, hash.length * 8);

			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

			byte[] testHash = skf.generateSecret(spec).getEncoded();

			int diff = hash.length ^ testHash.length;
			for (int i = 0; i < hash.length && i < testHash.length; i++) {
				diff |= hash[i] ^ testHash[i];
			}

			System.out.println(diff);

			return diff == 0;

		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
			System.out.println("NumberFormatException validando contraseña");
			return false;
		}

	}

	private static byte[] fromHex(String hex) {

		byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}

		return bytes;
	}

}
