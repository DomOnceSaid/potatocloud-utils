package org.potatocloud.validator;

import java.util.regex.Pattern;

public class Email {

	public static Boolean isEmail(String emailAddress) {
		return Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
				+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$").matcher(emailAddress).matches();
	}

	public static String emailMasker(String emailAddress) throws Exception {
		if (isEmail(emailAddress)) {
			return emailAddress.charAt(0) + "*".repeat(emailAddress.length() - 2)
					+ emailAddress.charAt(emailAddress.length() - 1);
		}
		throw new Exception("invalid email address");
	}

}
