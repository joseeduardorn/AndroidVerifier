package com.example.androidverifier;

import android.Manifest;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.PowerManager;
import android.os.RecoverySystem;
import android.provider.Settings;
import android.telephony.AvailableNetworkInfo;
import android.telephony.TelephonyManager;
import androidx.core.app.NotificationManagerCompat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class Verify {

    public static int DATA_DISCONNECTED = 0; //Connected 2, connection 1
    public static int DATA_SUSPENDED = 3;

    public static String getDeviceName() {
        return Build.MODEL;
    }

    /**
     * @return version and sdk : 9 SDK 28
     */
    public static String getVersionAndSKD() {
        return Build.VERSION.RELEASE + " SDK " + Build.VERSION.SDK_INT;
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
     *
     * @return true if Bluetooth is enabled
     */
    public static boolean isBluetoothEnabled() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean enabled = bluetoothAdapter.isEnabled();
        return enabled;
    }

    /**
     * @return true if NFC is enabled
     */
    public static boolean isNCFEnabled(Context context) {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(context);
        boolean enabled = nfcAdapter.isEnabled();
        return enabled;
    }

    /**
     * @param telephonyManager = getSystemService(Context.TELEPHONY_SERVICE)
     * @return true if the mobile data is available
     */
    public static boolean isDataMobileEnabled(TelephonyManager telephonyManager) {// getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
       int connection = telephonyManager.getDataState();
        return connection != Verify.DATA_DISCONNECTED;
    }

    /**
     * @param locationManager = locationManager = getSystemService(Context.LOCATION_SERVICE as LocationManager?
     * @return true if the location is available
     */
    public static boolean isGPSEnabled(LocationManager locationManager){//)
        return locationManager.isLocationEnabled();
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
     * @param wifiManager = getSystemService(Context.WIFI_SERVICE)
     * @return true if the hotspot is enable
     */
    public static boolean isWifiHotspotEnabled(WifiManager wifiManager) {
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
     * @param powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager?
     * @return true if PowerSaveMode is Enable
     */
    public static boolean isPowerSaveModeEnabled(PowerManager powerManager){
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
     * @return default assistant or null if there is none
     */
    public static ComponentName getCurrentAssist(Context context){
        final String setting = Settings.Secure.getString(context.getContentResolver(), "assistant");
        if(setting != null){
            return ComponentName.unflattenFromString(setting);
        }
        return null;
    }

}
