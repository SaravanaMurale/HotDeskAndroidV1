/*
// STANDSPOT, LLC CONFIDENTIAL
// Unpublished Copyright (c) 2021 STANDSPOT, LLC, All Rights Reserved.
//
// NOTICE:  All information contained herein is, and remains the property of Standspot, LLC. The intellectual and technical concepts contained
// herein are proprietary to Standspot, LLC and may be covered by U.S. and Foreign Patents, patents in process, and are protected by trade secret or copyright law.
// Dissemination of this information or reproduction of this material is strictly forbidden unless prior written permission is obtained
// from Standspot, LLC.  Access to the source code contained herein is hereby forbidden to anyone except current Standspot, LLC employees, managers or contractors who have executed 
// Confidentiality and Non-disclosure agreements explicitly covering such access.
//
// The copyright notice above does not evidence any actual or intended publication or disclosure  of  this source code, which includes  
// information that is confidential and/or proprietary, and is a trade secret, of  Standspot, LLC.   ANY REPRODUCTION, MODIFICATION, DISTRIBUTION, PUBLIC  PERFORMANCE, 
// OR PUBLIC DISPLAY OF OR THROUGH USE  OF THIS  SOURCE CODE  WITHOUT  THE EXPRESS WRITTEN CONSENT OF Standspot, LLC IS STRICTLY PROHIBITED, AND IN VIOLATION OF APPLICABLE 
// LAWS AND INTERNATIONAL TREATIES.  THE RECEIPT OR POSSESSION OF  THIS SOURCE CODE AND/OR RELATED INFORMATION DOES NOT CONVEY OR IMPLY ANY RIGHTS  
// TO REPRODUCE, DISCLOSE OR DISTRIBUTE ITS CONTENTS, OR TO MANUFACTURE, USE, OR SELL ANYTHING THAT IT  MAY DESCRIBE, IN WHOLE OR IN PART.                
//
*/


package com.brickendon.hdplus.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.libsodium.jni.crypto.SecretBox;
import org.libsodium.jni.encoders.Encoder;
import org.libsodium.jni.encoders.Hex;

/**
 * Created by user on 17-06-2022.
 */
public class SessionHandler {

    public static final String PREF_NAME = "HYBRIDHERO";

    static Hex hex;
    static SecretBox secretBox;
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private static final SessionHandler ourInstance = new SessionHandler();

    public static SessionHandler getInstance() {
        return ourInstance;
    }

    private SessionHandler() {
    }


    public SharedPreferences sharedPreferencesInstance(Context mContext) {
        return mContext.getSharedPreferences("HYBRIDHERO", Context.MODE_PRIVATE);
    }


    public void save(Context mContext, String key, String value) {
        try {
            key = encryptTextGeneration(key.getBytes(),
                    AppConstants.SODIUM_NONCE.getBytes());

            if (value != null && !value.isEmpty())
                value = encryptTextGeneration(value.getBytes(),
                        AppConstants.SODIUM_NONCE.getBytes());

            mContext.getSharedPreferences("HYBRIDHERO", Context.MODE_PRIVATE).edit().putString(key,
                    value).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void saveInt(Context mContext, String key, int value) {
        try {
            key = encryptTextGeneration(key.getBytes(),
                    AppConstants.SODIUM_NONCE.getBytes());
            String val = String.valueOf(value);
            val = encryptTextGeneration(val.getBytes(),
                    AppConstants.SODIUM_NONCE.getBytes());
            mContext.getSharedPreferences("HYBRIDHERO", Context.MODE_PRIVATE).edit().putString(key,
                    val).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveBoolean(Context mContext, String key, boolean value) {
        try {
            key = encryptTextGeneration(key.getBytes(),
                    AppConstants.SODIUM_NONCE.getBytes());

            String val = encryptTextGeneration(String.valueOf(value).getBytes(),
                    AppConstants.SODIUM_NONCE.getBytes());

            mContext.getSharedPreferences("HYBRIDHERO", Context.MODE_PRIVATE).edit().putString(key,
                    val).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getBoolean(Context mContext, String key) {
        try {
            key = encryptTextGeneration(key.getBytes(),
                    AppConstants.SODIUM_NONCE.getBytes());

            return Boolean.parseBoolean(decryptTextGeneration(mContext.getSharedPreferences("HYBRIDHERO",
                    Context.MODE_PRIVATE).getString(key, null)));
        } catch (Exception e) {
            return false;
        }
    }

    public void remove(Context mContext, String key) {
        key = encryptTextGeneration(key.getBytes(),
                AppConstants.SODIUM_NONCE.getBytes());
        mContext.getSharedPreferences("HYBRIDHERO", Context.MODE_PRIVATE).edit().remove(key)
                .commit();
    }

    public void removeAll(Context mContext) {
        mContext.getSharedPreferences("HYBRIDHERO", Context.MODE_PRIVATE).edit().clear()
                .commit();
    }

    public String get(Context mContext, String key) {
        try {
            key = encryptTextGeneration(key.getBytes(),
                    AppConstants.SODIUM_NONCE.getBytes());
            return decryptTextGeneration(mContext.getSharedPreferences("HYBRIDHERO",
                    Context.MODE_PRIVATE).getString(key, null));
        } catch (Exception e) {
            return "";
        }

    }

    public int getInt(Context mContext, String key) {
        try {
            key = encryptTextGeneration(key.getBytes(),
                    AppConstants.SODIUM_NONCE.getBytes());

            int val;
            if (decryptTextGeneration(mContext.getSharedPreferences("HYBRIDHERO",
                    Context.MODE_PRIVATE).getString(key, null)).isEmpty())
                val = 0;
            else
                val = Integer.parseInt(decryptTextGeneration(mContext.getSharedPreferences("HYBRIDHERO",
                        Context.MODE_PRIVATE).getString(key, null)));

            //  return mContext.getSharedPreferences("HYBRIDHERO", Context.MODE_PRIVATE).getInt(key, 0);
            return val;
        } catch (Exception e) {
            return 0;
        }
    }


    public static String encryptTextGeneration(byte[] message, byte[] nonce) {
        secretBox = new SecretBox(AppConstants.SODIUM_SECRET_CODE, Encoder.HEX);
        hex = new Hex();
        return bytesToHex(secretBox.encrypt(hex.decode(new String(nonce)), message));
    }

    public static String decryptTextGeneration(String cipherText) {

        if (cipherText != null && !(cipherText.equalsIgnoreCase(""))) {
            secretBox = new SecretBox(AppConstants.SODIUM_SECRET_CODE, Encoder.HEX);
            hex = new Hex();
            return new String(secretBox.decrypt(hex.decode(AppConstants.SODIUM_NONCE),
                    hex.decode(cipherText.trim())));
        } else {
            return "";
        }
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
