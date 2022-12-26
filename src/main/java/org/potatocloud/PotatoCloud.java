package org.potatocloud;

import dev.potato.totp.exceptions.QrGenerationException;
import org.potatocloud.totp.TOTPHelper;

public class PotatoCloud {

    public static void main(String[] args) throws QrGenerationException {
        String base64 = TOTPHelper.generateQRasBase64("test", "123@mail.com", "asdadasdasdasdsadasdas", 6 , 30);
        System.out.print("\n" + base64);
    }
}
