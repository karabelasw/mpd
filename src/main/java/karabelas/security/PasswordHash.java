package karabelas.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash{

	
  public static String getMD5Hash(String data, String key) {

        try {

            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(data.getBytes());
            byte result[] = md5.digest(key.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < result.length; i++) {
                String s = Integer.toHexString(result[i]);
                int length = s.length();
                if (length >= 2) {
                    sb.append(s.substring(length - 2, length));
                } else {
                    sb.append("0");
                    sb.append(s);
                }
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }


	public static String quickHash(String s) throws Exception{

    	MessageDigest m = MessageDigest.getInstance("SHA");

	    m.update(s.getBytes(),0,s.length());
    	String value = new BigInteger(1,m.digest()).toString(16);

    	return value;
   	}

	
	public static void main(String[] args)throws Exception{
	
		System.out.println(quickHash("Welcome1"));
		System.out.println(getMD5Hash("Welcome1","karabelas.com"));
		
	}


}
