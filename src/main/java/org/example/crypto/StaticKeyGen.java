package org.example.crypto;

import java.io.IOException;
import java.time.LocalDate;

class StaticKeyGen {
    public static final int ERROR = -1;
    public static final byte [] ERROR_BYTE_ARRAY = new byte[] {ERROR};
    public static final String LVL_PRIORITY_ERROR =      "<<ERROR__>>";
    public static final String LVL_PRIORITY_INFO =       "<<INFO___>>";
    public static final String LVL_PRIORITY_OK =         "<<OK_____>>";
    public static final String LVL_PRIORITY_WARN =       "<<WARN___>>";
    public static final String LVL_PRIORITY_DEBUG =      "<<DEBUG__>>";
    public static final String LVL_PRIORITY_VERBOSE =    "<<VERBOSE>>";
    public static final int SIZE = 16;
    public static  final byte[][] ERROR_ARRAY = {{-1},{-1}};
    public static final String EMPTY_IDENTIFIER = "Empty subscriber identifier";
    public byte[][] getStaticKeyFor (String sub1Ident, String sub2Ident) {
        byte [][] arrAnswer = new byte[SIZE][SIZE];
        if (sub1Ident.isEmpty() || sub2Ident.isEmpty()) {
            System.out.println(EMPTY_IDENTIFIER);
            return ERROR_ARRAY;
        }
        int lenFirst = sub1Ident.length();
        int lenSecond = sub2Ident.length();
        int minLength = 0;
        String tmp = "";
        minLength = Math.min(lenFirst, lenSecond);
        int tmp1 = 0;
        int tmp2 = 0;
        for (int i = 0; i < minLength; i++) {
            tmp1 = sub1Ident.toCharArray()[i];
            tmp2 = sub2Ident.toCharArray()[i];
            if (tmp1 > tmp2) {
                tmp = sub1Ident;
                sub1Ident = sub2Ident;
                sub2Ident = tmp;
                break;
            } else if (tmp1 < tmp2) {
                break;
            }
        }
        char [] arr1Tmp = (sub1Ident + sub1Ident + sub1Ident + sub1Ident + sub1Ident
                + sub1Ident + sub1Ident + sub1Ident + sub1Ident + sub1Ident
                + sub1Ident + sub1Ident + sub1Ident + sub1Ident + sub1Ident + sub1Ident).toCharArray();
        char [] arr2Tmp = (sub2Ident + sub2Ident + sub2Ident + sub2Ident + sub2Ident
                + sub2Ident + sub2Ident + sub2Ident + sub2Ident + sub2Ident
                + sub2Ident + sub2Ident + sub2Ident + sub2Ident + sub2Ident + sub2Ident).toCharArray();
        byte [] idFirstSubscriber = new byte[SIZE];
        byte [] idSecondSubscriber = new byte[SIZE];
        for (int i = 0; i < SIZE; i++) {
            idFirstSubscriber[i] = ((byte) arr1Tmp[i]);
            idSecondSubscriber[i] = ((byte) arr2Tmp[i]);
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if ((i + j) % 2 == 0) {
                    arrAnswer[i][j] = (byte) ((-1) * (((idFirstSubscriber[i]) ^ (idSecondSubscriber[j])) + (j * j) - (i * i) + (i ^ j) + (j ^ (i - SIZE) + (i ^ (j - SIZE)))));
                } else {
                    arrAnswer[i][j] = (byte) (((idFirstSubscriber[i]) ^ (idSecondSubscriber[j])) + (j * j) - (i * i) + (i ^ j) + (j ^ (i - SIZE) + (i ^ (j - SIZE))));
                }
            }
        }
        return arrAnswer;
    }
    public byte[] getByteArrayFromStr(String s) throws IOException {
        if (s == null || s.length() == 0) {
            System.out.println(LVL_PRIORITY_ERROR + "Empty array");
            return ERROR_BYTE_ARRAY;
        }
        byte[] keyAsByteArr = new byte[]{0};
        try {
            int s_len = s.length();
            char[] charArrFromStr = s.toCharArray();
            int count = 0;
            for (int i = 0; i < s_len; i++) {
                if (charArrFromStr[i] == ',') {
                    count++;
                }
            }
            count += 1;
            keyAsByteArr = new byte[count];
            int k = 0;
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 1; i < s_len; i++) {
                if (charArrFromStr[i] == '[' || charArrFromStr[i] == ' ') {
                    continue;
                }
                if (charArrFromStr[i] != ',' && charArrFromStr[i] != ']') {
                    stringBuilder.append(charArrFromStr[i]);
                } else if (charArrFromStr[i] == ',' || charArrFromStr[i] == ']') {
                    keyAsByteArr[k] = Byte.parseByte(stringBuilder.toString());
                    k++;
                    stringBuilder = new StringBuilder();
                }
            }
        } catch (Exception e) {
            System.out.println(LVL_PRIORITY_ERROR + e.getMessage());
            return ERROR_BYTE_ARRAY;
        }
        return keyAsByteArr;
    }
    public void printSquareMatrix (byte [][] arr) {
        for (byte[] bytes : arr) {
            for (int j = 0; j < arr.length; j++) {
                System.out.print((bytes[j]) + " ");
            }
            System.out.println();
        }
    }
}

