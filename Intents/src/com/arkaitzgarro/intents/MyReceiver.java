package com.arkaitzgarro.intents;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.ServiceState;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context ctx, Intent data) {
		Log.i("RECEIVER", data.getAction());
		Bundle extras = data.getExtras();

		if (extras != null) {
			if (data.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
				String state = data.getStringExtra("state");

				Log.i("RECEIVER", "ACTION_AIRPLANE_MODE_CHANGED");
				Log.i("RECEIVER", "STATUS: " + isAirplaneModeOn(ctx));
			} else if (data.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
				String state = data.getStringExtra(TelephonyManager.EXTRA_STATE);
				String number = data.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

				Log.i("RECEIVER", TelephonyManager.ACTION_PHONE_STATE_CHANGED);
				Log.i("RECEIVER", "STATUS: " + extras.get(TelephonyManager.EXTRA_STATE));

				if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
					Log.i("RECEIVER", "INCOMING NUMBER: " + number);
				}
			} else if (data.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
				Log.i("RECEIVER", extras.toString());

				readSMS(extras);
			} else if (data.getAction().equals("android.intent.action.SERVICE_STATE")) {			
				int state = data.getExtras().getInt("state");
				Log.i("RECEIVER", "State: " + state);
				
				if(state == ServiceState.STATE_POWER_OFF |
						state == ServiceState.STATE_OUT_OF_SERVICE |
						state == ServiceState.STATE_EMERGENCY_ONLY) {
					Log.i("RECEIVER", "State: DISCONNECTED");
				} else {
					Log.i("RECEIVER", "State: CONNECTED");
				}
			} else if(data.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
				ConnectivityManager connMgr = (ConnectivityManager) ctx
		                .getSystemService(Context.CONNECTIVITY_SERVICE);
				
				NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		        if (wifi.isAvailable() || mobile.isAvailable()) {
		            Log.d("RECEIVER", "Network available");
		        } else {
		        	Log.d("RECEIVER", "Network NOT available");
		        }
			}
		}

	}

	private void readSMS(Bundle extras) {
		try {
			if (extras != null) {
				final Object[] pdusObj = (Object[]) extras.get("pdus");

				SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[0]);
				String phoneNumber = currentMessage.getDisplayOriginatingAddress();

				String senderNum = phoneNumber;
				String message = currentMessage.getDisplayMessageBody();

				Log.d("RECEIVER", "SMS Sender: " + senderNum );
				Log.d("RECEIVER", "SMS Message: " + message);
			}
		} catch (Exception e) {
			Log.e("SmsReceiver", "Exception smsReceiver" + e);

		}
	}
	
	private boolean isAirplaneModeOn(Context context) {

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
	        return Settings.System.getInt(context.getContentResolver(), 
	                Settings.System.AIRPLANE_MODE_ON, 0) != 0;          
	    } else {
	        return Settings.Global.getInt(context.getContentResolver(), 
	                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
	    }
	}

}
