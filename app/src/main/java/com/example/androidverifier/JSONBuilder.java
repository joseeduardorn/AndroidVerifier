package com.example.androidverifier;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONBuilder {

    private JSONObject jsonObject = new JSONObject();


    public static final String DEVICE_ANDROID_VERSION = "device_android_version";
    public static final String DEVICE_SDK_VERSION = "device_android_sdk";
    public static final String DEVICE_SECURE = "device_secure";
    public static final String BLUETOOTH = "bluetooth";
    public static final String NFC = "nfc";
    public static final String GPS = "gps";
    public static final String NOTIFICATIONS = "notifications";
    public static final String WIFI_HOSTPOT = "wifi_hostpot";
    public static final String POWER_SAVE = "power_save";
    public static final String AIRPLANE_MODE = "airplane_mode";
    public static final String VOICE_ASSISTANT = "voice_assistant";
    public static final String TOUCHED_SOUND = "touched_sound";
    public static final String DTMF_TONE = "dtmf_tone"; //Dial pad touch tones
    public static final String HAPTIC_FEEDBACK = "haptic_feedback";
    public static final String LOCK_SCREEN_SOUNDS = "lock_screen_sounds";
    public static final String SCREEN_OFF_TIMEOUT = "screen_off_timeout";
    public static final String TEXT_SHOW_PASSWORD = "text_show_password";
    public static final String LOCK_SCREEN_AFTER = "lock_screen_after";

    // validation only in the webservice
    public static final String DEVICE_NAME = "device_name";
    public static final String BLUETOOTH_NAME = "bluetooth_name";
    public static final String DHCP_INFO = "dhcp_info";


    /**
     *
     * @param context
     * @return return an  JSONObject config, inside JSONArray with all attributes of the configuration
     * @throws JSONException
     */
    public JSONObject makeJSONVerifyBody(Context context, String email_user) throws JSONException {

        JSONObject localObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {

            Log.d("email before", email_user);
            if(email_user != null && !email_user.isEmpty()){
                localObject.put("email_user", email_user);
                Log.d("email", email_user);
            }
            localObject.put(JSONBuilder.DEVICE_ANDROID_VERSION, Verify.getVersion());
            localObject.put(JSONBuilder.DEVICE_SDK_VERSION, Verify.getSDK());

            localObject.put(JSONBuilder.DEVICE_SECURE, Verify.doesDeviceHaveSecuritySetup(context));
            localObject.put(JSONBuilder.BLUETOOTH, Verify.isBluetoothEnabled());
            localObject.put(JSONBuilder.NFC, Verify.isNFCEnabled(context));
            localObject.put(JSONBuilder.GPS, Verify.isGPSEnabled(context));
            localObject.put(JSONBuilder.WIFI_HOSTPOT, Verify.isWifiHotspotEnabled(context));
            localObject.put(JSONBuilder.POWER_SAVE, Verify.isPowerSaveModeEnabled(context));
            localObject.put(JSONBuilder.AIRPLANE_MODE, Verify.isAirplaneModeOn(context));
            localObject.put(JSONBuilder.VOICE_ASSISTANT, Verify.getCurrentAssist(context));
            localObject.put(JSONBuilder.TOUCHED_SOUND, Verify.isTouchSoundEnabled(context));
            localObject.put(JSONBuilder.DTMF_TONE, Verify.isDtmfToneEnabled(context));
            localObject.put(JSONBuilder.HAPTIC_FEEDBACK, Verify.isHapticFeedbackEnabled(context));
            localObject.put(JSONBuilder.LOCK_SCREEN_SOUNDS, Verify.islockScreenSoundsEnabled(context));
            localObject.put(JSONBuilder.SCREEN_OFF_TIMEOUT, Verify.getScreenOffTimeout(context));
            localObject.put(JSONBuilder.TEXT_SHOW_PASSWORD, Verify.isTextShowPasswordEnabled(context));
            localObject.put(JSONBuilder.LOCK_SCREEN_AFTER, Verify.getLockscreenAfter(context));
            //Log.d("isTextShowPassword", String.valueOf(Verify.isTextShowPasswordEnabled(context)));
            localObject.put(JSONBuilder.DEVICE_NAME, Verify.getDeviceName());
            localObject.put(JSONBuilder.BLUETOOTH_NAME, Verify.getLocalBluetoothName());
            localObject.put(JSONBuilder.DHCP_INFO, Verify.getDHCPInfo(context));

            jsonArray.put(localObject);
            jsonObject.put("config",jsonArray);

        } catch (JSONException | Settings.SettingNotFoundException e) {
            e.printStackTrace();
            localObject.put("error", e.toString());
            jsonArray.put(localObject);
            jsonObject.put("config",jsonArray);
        }


        return jsonObject;
    }

    public JSONObject getJsonObject(){
        return this.jsonObject;
    }

    public void addJsonObject(String name, String value){
        try {
            this.jsonObject.put(name, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
