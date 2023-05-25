package org.example.crypto;

import java.io.IOException;
import java.time.LocalDate;

public class ShtolcLibApi {
    public String encryptMessage (String str, String sub1Ident, String sub2Ident, LocalDate localDate) throws IOException {
        Encrypt encrypt = new Encrypt();
        return encrypt.encryptMessage(str, sub1Ident, sub2Ident, localDate);
    }
    public String decryptMessage (String str, String sub1Ident, String sub2Ident, LocalDate localDate) throws IOException {
        Decrypt decrypt = new Decrypt();
        return decrypt.decryptMessage(str, sub1Ident, sub2Ident, localDate);
    }
}

