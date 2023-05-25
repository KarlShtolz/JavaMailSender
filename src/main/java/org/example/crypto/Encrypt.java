package org.example.crypto;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;

class Encrypt {
    public String encryptMessage (String str, String sub1Ident, String sub2Ident, LocalDate localDate) throws IOException {
        StringBuilder sb = new StringBuilder();
        StaticKeyGen staticKeyGen = new StaticKeyGen();
        byte [] arr = str.getBytes(StandardCharsets.UTF_8);
        byte [] answer = new byte[arr.length];
        String timeStr = Arrays.toString(String.valueOf(localDate).getBytes());
        byte [] timeArr = staticKeyGen.getByteArrayFromStr(timeStr);
        byte[][] staticKey = staticKeyGen.getStaticKeyFor(sub1Ident, sub2Ident);
        byte [] key = new byte[StaticKeyGen.SIZE * StaticKeyGen.SIZE];
        int count = 0;
        for (int i = 0; i < StaticKeyGen.SIZE; i++) {
            for (int j = 0; j < StaticKeyGen.SIZE; j++) {
                key[count] = staticKey[i][j];
                count++;
            }
        }
        for (int i = 0; i < key.length; i++) {
            if (i < timeArr.length) {
                byte kk = timeArr[i];
                byte k =  key[i];
                key[i] = (byte) (kk ^ k);
            } else {
                byte kk =  timeArr[i % (timeArr.length)];
                byte k =  key[i];
                key[i] = (byte) (kk ^ k);
            }
        }
        for (int i = 0; i < arr.length; i++) {
            if (i < StaticKeyGen.SIZE * StaticKeyGen.SIZE) {
                byte ch = arr[i];
                byte k =  key[i];
                answer[i] = (byte) (ch ^ k);
                sb.append((char)(ch ^ k));
            } else {
                byte ch =  arr[i];
                byte k =  key[i % (StaticKeyGen.SIZE * StaticKeyGen.SIZE)];
                answer[i] = (byte) (ch ^ k);
                sb.append((char)(ch ^ k));
            }
        }
        System.out.println("<<sb encrypt>>" + sb);
        byte [] messageArr = new byte[answer.length + timeArr.length];
        int count2 = 0;
        for(int i = 0; i < answer.length; i++) {
            messageArr[i] = answer[i];
            count2++;
        }
        for (byte b : timeArr) {
            messageArr[count2++] = b;
        }
        return Arrays.toString(messageArr);
    }
}
