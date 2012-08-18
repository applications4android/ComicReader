package com.blogspot.applications4android.comicreader.core;

import com.blogspot.applications4android.comicreader.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


/**
 * Class responsible for checking the network connectivity
 */
// TODO: add unit-tests
public class ConnectivityCheck extends BroadcastReceiver {
	/** ignore the first time if the network is up */
	private boolean m_is_first = true;
	/** last state of this receiver */
	private boolean m_last_state = true;


	/**
	 * Called when a broadcast is made.
	 * @param context the application context
	 * @param intent intent containing the action occurred.
	 */
	@Override
	public void onReceive(final Context context, Intent intent) {
		String action = intent.getAction();
		if(!action.equalsIgnoreCase(ConnectivityManager.CONNECTIVITY_ACTION)){
			return;
		}
		// the first time this is called, this means that it is being called due to the register receiver!
		// if the network is down, then there'll be an alertdialog, if the network is up then no issue.
		// so, first time receive event should be ignored
		if(m_is_first) {
			m_is_first = false;
			return;
		}
		ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifi = mgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mobile = mgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean isNetworkDown = (!wifi.isConnected() && !mobile.isConnected());
		if (isNetworkDown) {
			String msg = context.getResources().getString(R.string.network_disconnected_message);
			Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		}
		else if(m_last_state) {
			String msg = context.getResources().getString(R.string.network_connected_message);
 		    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		}
		m_last_state = isNetworkDown;
	}

}
