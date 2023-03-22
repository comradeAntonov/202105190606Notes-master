/*
 * Copyright (c) 2010-2011, The MiCode Open Source Community (www.micode.net)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.micode.notes.data;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.telephony.PhoneNumberUtils;
import android.util.Log;

import java.util.HashMap;

public class Contact {
    private static HashMap<String, String> sContactCache;
    //存储联系人信息
    private static final String TAG = "Contact";

    private static final String CALLER_ID_SELECTION = "PHONE_NUMBERS_EQUAL(" + Phone.NUMBER
    + ",?) AND " + Data.MIMETYPE + "='" + Phone.CONTENT_ITEM_TYPE + "'"
    + " AND " + Data.RAW_CONTACT_ID + " IN "
            + "(SELECT raw_contact_id "
            + " FROM phone_lookup"
            + " WHERE min_match = '+')";
    //获取联系人
    public static String getContact(Context context, String phoneNumber) {
        if(sContactCache == null) {
            sContactCache = new HashMap<String, String>();
        }
    //查找map中是否有phoneNumber信息
        if(sContactCache.containsKey(phoneNumber)) {
            return sContactCache.get(phoneNumber);
        }

        String selection = CALLER_ID_SELECTION.replace("+",
                PhoneNumberUtils.toCallerIDMinMatch(phoneNumber));
        //查找数据库中phoneNumber的信息
        Cursor cursor = context.getContentResolver().query(
                Data.CONTENT_URI,
                new String [] { Phone.DISPLAY_NAME },
                selection,
                new String[] { phoneNumber },
                null);

        if (cursor != null && cursor.moveToFirst()) {
            try {
                //找到信息
                String name = cursor.getString(0);
                sContactCache.put(phoneNumber, name);
                return name;
            } catch (IndexOutOfBoundsException e) {
                //发生异常
                Log.e(TAG, " Cursor get string error " + e.toString());
                return null;
            } finally {
                cursor.close();
            }
        } else {
            //未找到信息
            Log.d(TAG, "No contact matched with number:" + phoneNumber);
            return null;
        }
    }
}
