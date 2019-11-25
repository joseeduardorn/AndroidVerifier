package com.example.androidverifier;

import android.content.Context;
import android.provider.Settings;

import org.json.JSONException;
import org.json.JSONObject;

public class VerifyBuilder {

    private JSONObject jsonObject = null;//new JSONObject();


    public static final String DEVICE_ANDROID_VERSION = "device_android_version";
    public static final String DEVICE_SDK_VERSION = "device_android_sdk";
    public static final String DEVICE_SECURE = "device_secure";
    public static final String BLUETOOTH = "bluetooth";
    public static final String NCF = "ncf";
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


    public JSONObject JSONVerify(Context context){
        jsonObject = new JSONObject();
        try {

            jsonObject.put(VerifyBuilder.DEVICE_ANDROID_VERSION, Verify.getVersion());
            jsonObject.put(VerifyBuilder.DEVICE_SDK_VERSION, Verify.getSDK());
            jsonObject.put(VerifyBuilder.DEVICE_SECURE, Verify.doesDeviceHaveSecuritySetup(context));
            jsonObject.put(VerifyBuilder.BLUETOOTH, Verify.isBluetoothEnabled());
            jsonObject.put(VerifyBuilder.NCF, Verify.isNFCEnabled(context));
            jsonObject.put(VerifyBuilder.GPS, Verify.isGPSEnabled(context));
            jsonObject.put(VerifyBuilder.WIFI_HOSTPOT, Verify.isWifiHotspotEnabled(context));
            jsonObject.put(VerifyBuilder.POWER_SAVE, Verify.isPowerSaveModeEnabled(context));
            jsonObject.put(VerifyBuilder.AIRPLANE_MODE, Verify.isAirplaneModeOn(context));
            jsonObject.put(VerifyBuilder.VOICE_ASSISTANT, Verify.getCurrentAssist(context));
            jsonObject.put(VerifyBuilder.TOUCHED_SOUND, Verify.isTouchSoundEnabled(context));
            jsonObject.put(VerifyBuilder.DTMF_TONE, Verify.isDtmfToneEnabled(context));
            jsonObject.put(VerifyBuilder.HAPTIC_FEEDBACK, Verify.isHapticFeedbackEnabled(context));
            jsonObject.put(VerifyBuilder.LOCK_SCREEN_SOUNDS, Verify.islockScreenSoundsEnabled(context));
            jsonObject.put(VerifyBuilder.SCREEN_OFF_TIMEOUT, Verify.getScreenOffTimeout(context));
            jsonObject.put(VerifyBuilder.TEXT_SHOW_PASSWORD, Verify.isTextShowPasswordEnabled(context));
            jsonObject.put(VerifyBuilder.LOCK_SCREEN_AFTER, Verify.getLockscreenAfter(context));

            jsonObject.put(VerifyBuilder.DEVICE_NAME, Verify.getDeviceName());
            jsonObject.put(VerifyBuilder.BLUETOOTH_NAME, Verify.getLocalBluetoothName());
            jsonObject.put(VerifyBuilder.DHCP_INFO, Verify.getDHCPInfo(context));

        } catch (JSONException | Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
