package com.contactpicker;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

public class ContactPickerModule extends ReactContextBaseJavaModule {

    private static final int PICK_CONTACT = 2025;
    private Promise contactPromise;

    public ContactPickerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addActivityEventListener(activityEventListener);
    }

    @Override
    public String getName() {
        return "ContactPickerModule";
    }

    @ReactMethod
    public void pickContact(Promise promise) {
        Activity currentActivity = getCurrentActivity();
        if (currentActivity == null) {
            promise.reject("NO_ACTIVITY", "No activity available");
            return;
        }

        contactPromise = promise;

        try {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            currentActivity.startActivityForResult(intent, PICK_CONTACT);
        } catch (Exception e) {
            contactPromise.reject("ERROR_OPENING_CONTACTS", e.getMessage());
            contactPromise = null;
        }
    }

    private final ActivityEventListener activityEventListener = new BaseActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, @Nullable Intent data) {
            if (requestCode == PICK_CONTACT && contactPromise != null) {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    Uri contactUri = data.getData();
                    Cursor cursor = activity.getContentResolver().query(contactUri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                        int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                        String name = cursor.getString(nameIndex);
                        String phone = cursor.getString(numberIndex);

                        cursor.close();

                        contactPromise.resolve(createContactMap(name, phone));
                    } else {
                        contactPromise.reject("NO_CONTACT", "Unable to read contact");
                    }
                } else {
                    contactPromise.reject("CANCELLED", "User cancelled contact picker");
                }

                contactPromise = null;
            }
        }
    };

    private WritableMap createContactMap(String name, String phone) {
        WritableMap map = new com.facebook.react.bridge.WritableNativeMap();
        map.putString("name", name);
        map.putString("phone", phone);
        return map;
    }
}
