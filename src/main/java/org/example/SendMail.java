package org.example;

import org.example.crypto.ShtolcLibApi;

import java.io.IOException;
import java.time.LocalDate;

public class SendMail {
    public static void main(String[]args) throws IOException {
        String from = "";
//        String to1 = "";
//        String to2 = "";
//        String pass = "";
//        String subject = "Test";
//        String message = "Привет, мир!)";
//        sendCryptoMail(from,to2,pass,subject,message);

    }
    public static int sendCryptoMail(String from, String to, String pass, String subject, String message) throws IOException {
        try {
            ShtolcLibApi shtolcLibApi = new ShtolcLibApi();
            LocalDate ldNow = LocalDate.now();
            message = shtolcLibApi.encryptMessage(message, from, to, ldNow);
            String decr = shtolcLibApi.decryptMessage(message, from, to, ldNow);
            Sender sender = new Sender(from, pass);
            sender.send(subject, message, from, to);
            //System.out.println(message + '\n' + decr);
        } catch (Exception e) {
            return -1;
        }
        return 0;
    }
}
