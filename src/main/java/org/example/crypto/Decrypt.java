package org.example.crypto;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;

class Decrypt {
    public String decryptMessage (String str, String sub1Ident, String sub2Ident, LocalDate localDate) throws IOException {
        StaticKeyGen staticKeyGen = new StaticKeyGen();
        byte[] arr = staticKeyGen.getByteArrayFromStr(str);
        String timeStr = Arrays.toString(String.valueOf(localDate).getBytes());
        byte [] timeArrTmp = staticKeyGen.getByteArrayFromStr(timeStr);
        int lenTimeArrTmp = timeArrTmp.length;
        byte [] timeArr = new byte[lenTimeArrTmp];
        for (int i = 0; i < lenTimeArrTmp; i++) {
            timeArr[lenTimeArrTmp - i - 1] = arr[arr.length - i - 1];
        }
        byte [] answer = new byte[arr.length - lenTimeArrTmp];
        StringBuilder sb = new StringBuilder();
        byte[][] staticKey = staticKeyGen.getStaticKeyFor(sub1Ident, sub2Ident);
        byte [] key = new byte[StaticKeyGen.SIZE * StaticKeyGen.SIZE];
        int count = 0;
        for (int i = 0; i < StaticKeyGen.SIZE; i++) {
            for (int j = 0; j < StaticKeyGen.SIZE; j++) {
                key[count] = staticKey[i][j];
                count++;
            }
        }
        for (int i = 0; i < key.length - lenTimeArrTmp; i++) {
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
        for (int i = 0; i < arr.length - lenTimeArrTmp; i++) {
            byte ch;
            byte k;
            if (i < StaticKeyGen.SIZE * StaticKeyGen.SIZE) {
                ch = arr[i];
                k = key[i];
                //System.out.println("i="+i+"; ch="+arr[i]+"; k="+key[i]);
            } else {
                ch = arr[i];
                k = key[i % StaticKeyGen.SIZE * StaticKeyGen.SIZE];
                //System.out.println("i="+i+"; ch="+arr[i]+"; k="+key[i % StaticKeyGen.SIZE * StaticKeyGen.SIZE]);
            }
            answer[i] = (byte) (ch ^ k);
            sb.append(answer[i]);
        }
        return new String(answer, StandardCharsets.UTF_8);
    }
}

