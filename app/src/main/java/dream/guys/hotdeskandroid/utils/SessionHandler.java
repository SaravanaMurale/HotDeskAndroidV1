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


package dream.guys.hotdeskandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 17-06-2022.
 */
public class SessionHandler {

    public static final String PREF_NAME="HOTDESK";


    private static final SessionHandler ourInstance = new SessionHandler();

    public static SessionHandler getInstance() {
        return ourInstance;
    }

    private SessionHandler() {
    }


    public SharedPreferences sharedPreferencesInstance(Context mContext) {
        return mContext.getSharedPreferences("HOTDESK", Context.MODE_PRIVATE);
    }


    public void save(Context mContext, String key, String value) {
        mContext.getSharedPreferences("HOTDESK", Context.MODE_PRIVATE).edit().putString(key, value).apply();
    }


    public void saveInt(Context mContext, String key, int value) {
        mContext.getSharedPreferences("HOTDESK", Context.MODE_PRIVATE).edit().putInt(key, value).commit();
    }

    public void saveBoolean(Context mContext, String key, boolean value) {
        mContext.getSharedPreferences("HOTDESK", Context.MODE_PRIVATE).edit().putBoolean(key, value).commit();
    }

    public boolean getBoolean(Context mContext, String key) {
        return mContext.getSharedPreferences("HOTDESK", Context.MODE_PRIVATE).getBoolean(key, false);
    }

    public void remove(Context mContext, String key) {
        mContext.getSharedPreferences("HOTDESK", Context.MODE_PRIVATE).edit().remove(key).commit();
    }

    public void removeAll(Context mContext) {
            mContext.getSharedPreferences("HOTDESK", Context.MODE_PRIVATE).edit().clear().commit();
    }

    public String get(Context mContext, String key) {
        return mContext.getSharedPreferences("HOTDESK", Context.MODE_PRIVATE).getString(key, null);
    }

    public int getInt(Context mContext, String key) {
        return mContext.getSharedPreferences("HOTDESK", Context.MODE_PRIVATE).getInt(key, 0);
    }
}
