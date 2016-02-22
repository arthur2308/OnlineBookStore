package CSS490;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class User implements Serializable{
	
	final private int numberOf_iterations = 65536;
	final private int sizeOf_key = 512;		// in bits; key size in char = size/8
	final private int sizeOf_salt = 16;
	final private int sizeOf_longhash = 80;
	final private String hash_Algorithm = "PBKDF2WithHmacSHA512";
	
	private String username; 
	private char[] pass; 
	private byte[] hash;
	private byte[] salt;
	private String name;
	private String email;
	private String signUpDate;
	private String lastLogin;
	
	public User(){		
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setPass(char[] pass){
		this.pass = pass;
	}
	
	public void clearPass() {
		Arrays.fill(pass, '0');
		pass = null;
	}
	
	public byte[] getHash() {
		byte[] temp = new byte[salt.length + hash.length];
		System.arraycopy(salt, 0, temp, 0, salt.length);
		System.arraycopy(hash, 0, temp, salt.length, hash.length);
		return temp;
	}
	
	public boolean checkPass(byte[] longhash){
		boolean result = false;
		if (longhash != null && longhash.length == sizeOf_longhash) {
			salt = Arrays.copyOfRange(longhash, 0, sizeOf_salt);
			if (hash()) {
				byte[] tempHash = Arrays.copyOfRange(longhash, sizeOf_salt, sizeOf_longhash);
				result = Arrays.equals(hash, tempHash);
			}
		}
		return result;
	}
	
	public void setSalt(byte[] salt) {
		this.salt = salt;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setSignUpDate(String signUpDate){
		this.signUpDate = signUpDate;
	}
	
	public String getSignUpDate(){
		return signUpDate;
	}
	
	public void setLastLogin(String lastLogin){
		this.lastLogin = lastLogin;
	}
	
	public String getLastLogin(){
		return lastLogin;
	}
	
	public boolean createHash() {
		SecureRandom random = new SecureRandom();
		salt = new byte[sizeOf_salt];
		random.nextBytes(salt);
		return hash();
	}
	
	private boolean hash() {
		try {
			KeySpec spec = new PBEKeySpec(pass, salt, numberOf_iterations, sizeOf_key);
			SecretKeyFactory f;
			f = SecretKeyFactory.getInstance(hash_Algorithm);
			hash = f.generateSecret(spec).getEncoded();
			return true;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return false;
	}
}