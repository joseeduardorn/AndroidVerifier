package com.example.androidverifier;

import android.Manifest;
import android.app.ApplicationErrorReport;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.tech.NfcA;
import android.os.BatteryManager;
import android.os.Build;
import android.os.PowerManager;
import android.os.RecoverySystem;
import android.os.UserManager;
import android.provider.Settings;
import android.telephony.AvailableNetworkInfo;
import android.telephony.TelephonyManager;
import android.text.BoringLayout;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;


public class Verify {

    public static int DATA_DISCONNECTED = 0; //Connected 2, connection 1
    public static int DATA_SUSPENDED = 3;
    public static String LOCKSCREEN_SOUNDS_ENABLED = "lockscreen_sounds_enabled";
    public static String LOCKSCREEN_LOCK_AFTER_TIMEOUT = "lock_screen_lock_after_timeout";

    public static String getDeviceName() {
        return Build.MODEL;
    }


    public  static String getVersion(){
        return Build.VERSION.RELEASE;
    }

    public static Integer getSDK(){
        return Build.VERSION.SDK_INT;
    }

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * @return version and sdk : 9 SDK 28
     */
    public static String getVersionAndSKD() {
        return Verify.getVersion() + " SDK " + Verify.getSDK();
    }

    /**
     * Checks to see if the lock screen is set up with either a PIN / PASS / PATTERN</p>
     * @return true if PIN, PASS or PATTERN set, false otherwise.
     */
    public static boolean doesDeviceHaveSecuritySetup(Context context) {

        return isDeviceSecure(context) || isPassOrPinSet(context);
    }

    /**
     * @param context
     * @return whether the device is secured with a PIN, pattern or password.
     */
    public static Boolean isDeviceSecure(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return keyguardManager.isDeviceSecure();
    }

    /**
     * @param context
     * @return whether the keyguard is secured by a PIN, pattern or password or a SIM card is currently locked
     */
    public static Boolean isPassOrPinSet(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return keyguardManager.isKeyguardSecure();
    }

    /**
     * wheter the bluetooth is enabled or disabled
     * @return true if Bluetooth is enabled
     */
    public static boolean isBluetoothEnabled() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean enabled = bluetoothAdapter.isEnabled();
        return enabled;
    }

    /**
     * @return true if NFC is enabled
     *
     */
    public static boolean isNFCEnabled(Context context) {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(context);
        boolean enabled = nfcAdapter.isEnabled();
        return enabled;
    }

    /**
     * whether the NFC is enabled
     * @param context
     * @return true or false

    public static boolean isNFCEnabled( Context context){
        boolean value = false;
        try {
            NfcManager nfcManager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
            NfcAdapter nfcAdapter = nfcManager.getDefaultAdapter();
            if(nfcAdapter!= null && nfcAdapter.isEnabled()){
                value = true;
            }
        }catch (Exception e){
            value = false;
        }

        return value;
    }*/

    /**
     * @param telephonyManager = getSystemService(Context.TELEPHONY_SERVICE)
     * @return true if the mobile data is available
     */
    public static boolean isDataMobileEnabled(TelephonyManager telephonyManager) {// getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
       int connection = telephonyManager.getDataState();
        return connection != Verify.DATA_DISCONNECTED;
    }

    /**
     *
     * @param context
     * @return true if the location is available
     */
    public static boolean isGPSEnabled(Context context){//)
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean value = false;
        try {
             if( locationManager !=null && locationManager.isLocationEnabled())
                 value = true;

        }catch (Exception e){
        value =false;
        }
        return value;
    }

    /**
     *
     * @param context
     * @return
     */
    public static boolean isNotificationEnabled(Context context){
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
       // return notificationManager.areNotificationsEnabled();
        return notificationManagerCompat.areNotificationsEnabled();
    }

    /**
     *
     * @param context
     * @return true if the hotspot is enable
     */
    public static boolean isWifiHotspotEnabled(Context context) {

        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);

        Method[] methods = wifiManager.getClass().getDeclaredMethods();
        boolean isWifiAPenabled = false;
        for (Method method: methods) {
            if (method.getName().equals("isWifiApEnabled")) {
                try {
                    isWifiAPenabled = (boolean) method.invoke(wifiManager);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return isWifiAPenabled;
    }

    /**
     *
     * @param context
     * @return true if PowerSaveMode is Enable
     */
    public static boolean isPowerSaveModeEnabled(Context context){
        PowerManager powerManager = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        return powerManager.isPowerSaveMode();
    }



    /**
     * Gets the state of Airplane Mode.
     *
     * @param context
     * @return true if enabled.
     */
    public static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

    /**
     *
     * @param context
     * @return default assistant or null if there is none pending
     */
    public static ComponentName getCurrentAssist(Context context){
        final String setting = Settings.Secure.getString(context.getContentResolver(), "assistant");
        if(setting != null){
            return ComponentName.unflattenFromString(setting);
        }
        return null;
    }

    /**
     *
     * @param context
     * @return true if the Touch Sound Enable
     */
    public static boolean isTouchSoundEnabled(Context context){
       // final String sound = Settings.System.getString(context.getContentResolver(), Settings.System.SOUND_EFFECTS_ENABLED);
        return Settings.System.getInt(context.getContentResolver(), Settings.System.SOUND_EFFECTS_ENABLED, 0) != 0;
    }

    /**
     *
     * @param context
     * @return true if Dial pad touch tones
     */
    public static boolean isDtmfToneEnabled(Context context){
        return Settings.System.getInt(context.getContentResolver(), Settings.System.DTMF_TONE_WHEN_DIALING, 0) != 0;
    }

    /*
    public static boolean isVibrateOn(Context context){
        return Settings.System.getInt(context.getContentResolver(), Settings.System.VIBRATE_ON,0) != 0;
    }*/

    /**
     *
     * @param context
     * @return Whether haptic feedback (Vibrate on tap) is enabled.
     * The value is boolean (1 or 0) Vibrate on touch
     */
    public static boolean isHapticFeedbackEnabled(Context context){
        return Settings.System.getInt(context.getContentResolver(), Settings.System.HAPTIC_FEEDBACK_ENABLED, 0) != 0;
    }

    /**
     *
     * @param context
     * @return sound when lockscreen sound enabled
     */
    public static boolean islockScreenSoundsEnabled(Context context){
        return Settings.System.getInt(context.getContentResolver(), Verify.LOCKSCREEN_SOUNDS_ENABLED, 0) != 0;
    }

    /**
     *
     * @param context
     * @return The amount of time in milliseconds before the device goes to sleep or begins to dream after a period of inactivity.
     * @throws Settings.SettingNotFoundException
     * @help 60000 = 1 default
     */
    public static Integer getScreenOffTimeout(Context context) throws Settings.SettingNotFoundException { // getTimeOutOff
        return Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT);
    }

    /**
     *
     * @param context
     * @return Setting to showing password characters in text editors. 1 = On, 0 = Off
     */
    public static boolean isTextShowPasswordEnabled(Context context){
        return Settings.System.getInt(context.getContentResolver(), Settings.System.TEXT_SHOW_PASSWORD, 0 )!= 0;
    }

    /**
     * El valor  para  "Bloquear automáticamente"  se  aconseja  que  sea  deentre 5 y  30 segundos.
     * @param context
     * @return el valor en milisegundos 0, 5000, 15000
     */
    public static Long getLockscreenAfter(Context context){
        return Settings.Secure.getLong(context.getContentResolver(), Verify.LOCKSCREEN_LOCK_AFTER_TIMEOUT,0);
    }

    /**
     * Need Manageusers Persmissions, are only granted to system admin. The MANAGE_USERS has a
     * protectionlevel of signature|system, which means that the application has to be signed with the platform key.
     * @param
     * @return

    public static Integer getUserAccounts(Context context){
        Integer value = 0;
        Log.d("Inside UserManager", "User ");
       try {
           UserManager userManager = (UserManager) context.getSystemService(Context.USER_SERVICE);
           Log.d("Creation UserManager", "User "+userManager);
           if (userManager != null)
               value = userManager.getUserCount();
       }catch (Exception e){
           Log.d("Creation UserManager", e.getMessage() );
       }

        return value;
    } */

    /**
     *
     * @return bluetooth name
     */

    public static String getLocalBluetoothName() {
        String name = "";
        try {
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            name = mBluetoothAdapter.getName();
            if (name == null) {
                name = mBluetoothAdapter.getAddress();
            }
        }catch (Exception e){

        }
        return name;
    }

    /**
     * PENDING
     * @param context
     * @return

    public static boolean isUSBMassStorageEnabled(Context context){
        return Settings.Global.getInt(context.getContentResolver(), Settings.Global.USB_MASS_STORAGE_ENABLED, 0) != 0;
    } /*

    /**
     *
     * @param context
     * @return String
     * ipaddr 192.168.100.4
     * gateway 192.168.100.1
     * netmask 0.0.0.0
     * dns1 200.32.248.1 dns2 8.8.8.8
     * DHCP server 192.168.100.1 lease 86400 seconds
     */
    public static String getDHCPInfo(Context context){
        String value = "None";
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            DhcpInfo info = wifiManager.getDhcpInfo();
            if(info!= null){
                value = info.toString();
            }
        }catch (Exception e){}
    return value;
    }

}
