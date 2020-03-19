//$Id$
package com.manik.project.Encryption;

public class HexEncryption {

    public static final char[] HEX = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
 
    public static int INT(char c) {
       return Integer.decode("0x" + c);
    }
 
    public static String BASE16_ENCODE(byte[] input) {
       char[] b16 = new char[input.length * 2];
       int i = 0;
       byte[] var3 = input;
       int var4 = input.length;
       for(int var5 = 0; var5 < var4; ++var5){         
    	   byte c = var3[var5];
    	   int low = c & 15;
    	   int high = (c & 240) >> 4;
       		b16[i++] = HEX[high];
       		b16[i++] = HEX[low];
       }
       return new String(b16);
    }

    public static byte[] BASE16_DECODE(String b16str) {
       int len = b16str.length();
       byte[] out = new byte[len / 2];
       int j = 0;
       for(int i = 0; i < len; i += 2) {
          int c1 = INT(b16str.charAt(i));
          int c2 = INT(b16str.charAt(i + 1));
          int bt = c1 << 4 | c2;
          out[j++] = (byte)bt;
       }
       return out;
    }
 
}
