package com.googlecode.wmbutil.util;

import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: bobb
 * Date: 16/07/2012
 * Time: 19:56
 * To change this template use File | Settings | File Templates.
 */
public class HexUtils {
    public static final byte[] HEX_CHAR_TABLE = {
            (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7',
            (byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f'
    };

    /**
     * Converts raw byte array into a string representation
     *
     * @param raw byte array
     * @return String representation of the byte array
     */
    public static String asHex(byte[] raw) {
        byte[] hex = new byte[2 * raw.length];
        int index = 0;

        for (byte b : raw) {
            int v = b & 0xFF;
            hex[index++] = HEX_CHAR_TABLE[v >>> 4];
            hex[index++] = HEX_CHAR_TABLE[v & 0xF];
        }
        try {
            return new String(hex, "US-ASCII");
        } catch (UnsupportedEncodingException ex) {
            // Unlikely US-ASCII comes with every JVM
            throw new RuntimeException("US-ASCII is an unsupported encoding, unable to compute hex string");
        }
    }
}
