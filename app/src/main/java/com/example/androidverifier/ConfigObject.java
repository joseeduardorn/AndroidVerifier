package com.example.androidverifier;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import retrofit2.SkipCallbackExecutor;

public class ConfigObject {

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



    @SerializedName(ConfigObject.DEVICE_ANDROID_VERSION)
    public String device_android_version;

    @SerializedName(ConfigObject.DEVICE_SDK_VERSION)
    public String device_sdk_version;

    @SerializedName(ConfigObject.DEVICE_SECURE)
    public String device_secure;

    @SerializedName(ConfigObject.BLUETOOTH)
    public String bluetooth;

    @SerializedName(ConfigObject.NCF)
    public String ncf;

    @SerializedName(ConfigObject.GPS)
    public String gps;

    @SerializedName(ConfigObject.NOTIFICATIONS)
    public String notifications;

    @SerializedName(ConfigObject.WIFI_HOSTPOT)
    public String wifi_hostpot;

    @SerializedName(ConfigObject.POWER_SAVE)
    public String power_save;

    @SerializedName(ConfigObject.AIRPLANE_MODE)
    public String airplane_mode;

    @SerializedName(ConfigObject.VOICE_ASSISTANT)
    public String voice_assistant;

    @SerializedName(ConfigObject.TOUCHED_SOUND)
    public String touched_soudn;

    @SerializedName(ConfigObject.DTMF_TONE)
    public String dtmf_tone;

    @SerializedName(ConfigObject.HAPTIC_FEEDBACK)
    public String haptic_eedback;

    @SerializedName(ConfigObject.LOCK_SCREEN_SOUNDS)
    public String lock_screen_sounds;

    @SerializedName(ConfigObject.SCREEN_OFF_TIMEOUT)
    public String screen_off_timeout;

    @SerializedName(ConfigObject.TEXT_SHOW_PASSWORD)
    public String text_show_password;

    @SerializedName(ConfigObject.LOCK_SCREEN_AFTER)
    public String lock_screen_after;

    // validation only in the webservice
    @SerializedName(ConfigObject.DEVICE_NAME)
    public String device_name;

    @SerializedName(ConfigObject.BLUETOOTH_NAME)
    public String bluetooth_name;

    @SerializedName(ConfigObject.DHCP_INFO)
    public String dhcp_info;

/*

            localObject.put(JSONBuilder.DEVICE_SECURE, Verify.doesDeviceHaveSecuritySetup(context));
            localObject.put(JSONBuilder.BLUETOOTH, Verify.isBluetoothEnabled());
            localObject.put(JSONBuilder.NCF, Verify.isNFCEnabled(context));
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

            localObject.put(JSONBuilder.DEVICE_NAME, Verify.getDeviceName());
            localObject.put(JSONBuilder.BLUETOOTH_NAME, Verify.getLocalBluetoothName());
            localObject.put(JSONBuilder.DHCP_INFO, Verify.getDHCPInfo(context));*/

    public ConfigObject(Context context){
        getdevice_android_version();
        getdevice_sdk_version();
        getdevice_secure(context);
    }

    public ConfigObject getConfigObject(){
        return this;
    }

    public String getdevice_android_version(){
        device_android_version = Verify.getVersion();
        return this.device_android_version;
    }

    public String getdevice_sdk_version(){
        device_sdk_version = Verify.getSDK().toString();
        return device_android_version;
    }


    public String getdevice_secure(Context context){
        device_secure = Verify.isDeviceSecure(context).toString();
        return device_secure;
    }
    public String getbluetooth(){
        bluetooth = Verify.isBluetoothEnabled().toString();
        return bluetooth;
    }

    public String getncf(Context context){
        ncf = Verify.isNFCEnabled(context).toString();
        return ncf;
    }

    public String getgps(Context context){
        gps = Verify.isGPSEnabled(context).toString();
        return gps;
    }
    public String getnotifications(){
        return notifications;
    }
    public String getwifi_hostpot(Context context){
        wifi_hostpot = Verify.isWifiHotspotEnabled(context).toString();
        return wifi_hostpot;
    }
    /*
    power_save;
    airplane_mode;
    voice_assistant;
    touched_soudn;
    dtmf_tone;
    haptic_eedback;
    lock_screen_sounds;
    screen_off_timeout;
    text_show_password;
    lock_screen_after;
    device_name;
    bluetooth_name;
    dhcp_info; */
}
