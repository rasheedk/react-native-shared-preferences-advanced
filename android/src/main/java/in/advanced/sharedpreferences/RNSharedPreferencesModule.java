package in.advanced.sharedpreferences;


import android.widget.Toast;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.*;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.common.MapBuilder;
import android.bluetooth.BluetoothAdapter;
import com.facebook.react.bridge.Callback;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import in.advanced.sharedpreferences.SharedDataProvider;
import in.advanced.sharedpreferences.SharedHandler;

import android.content.Intent;
import android.content.Context;

import java.util.Map;
import java.util.HashMap;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;



public class RNSharedPreferencesModule extends ReactContextBaseJavaModule {


	final int BT_ACTION_REQUEST_ENABLE = 1;

	private BluetoothAdapter bt_adapter = null;
	private ListView bt_list_view;
	private ArrayList<BluetoothDevice> bt_device_list = null;
	private boolean bt_scanning = false;
	private boolean is_watch = false;
	private String shared_name = "wit_player_shared_preferences";

	private BroadcastReceiver bt_info_receiver = null;




	public void onCreate(Bundle savedInstanceState) {


	}


	private void initSharedHandler() {
		SharedHandler.init(getReactApplicationContext(), shared_name);		
	}

	public RNSharedPreferencesModule(ReactApplicationContext reactContext) {
		super(reactContext);
	}

	@Override
	public String getName() {
		return "SharedPreferences";
	}

	@ReactMethod
	public void setName(String name) {
		shared_name = name;
	}


	@ReactMethod
		public void setItem(String key, String value) {

			initSharedHandler();
			SharedDataProvider.putSharedValue(key,value);

		}
		
		@ReactMethod
		public void setBoolean(String key, boolean value) {

			initSharedHandler();
			SharedDataProvider.putSharedBooleanValue(key,value);

		}

	@ReactMethod
		public void getItem(String key, Callback successCallback){

			initSharedHandler();
			String value = SharedDataProvider.getSharedValue(key);
			successCallback.invoke(value);

		}
		
		@ReactMethod
		public void getBoolean(String key, Callback successCallback){

			initSharedHandler();
			Boolean value = SharedDataProvider.getSharedBooleanValue(key);
			successCallback.invoke(value);

		}


	/***
	 * getItems(): returns Native Array of Preferences for the given values
	 * */
	@ReactMethod
		    public void getItems(ReadableArray keys, Callback successCallback){
			    initSharedHandler();
			    String[] keysArray= new String[keys.size()];
			    for (int i=0;i<keys.size();i++){
				    keysArray[i]=keys.getString(i);
			    }
			    String[] [] values = SharedDataProvider.getMultiSharedValues(keysArray);
			    WritableNativeArray data = new WritableNativeArray();
			    for(int i=0;i<keys.size();i++){
				    data.pushString(values[i][1]);
			    }
			    successCallback.invoke(data);
		    }

	@ReactMethod
		public void getAll(Callback successCallback){
			initSharedHandler();
			String[][] values = SharedDataProvider.getAllSharedValues();
			WritableNativeArray data = new WritableNativeArray();
			for(int i=0; i<values.length; i++){
				WritableArray arr = new WritableNativeArray();
				arr.pushString(values[i][0]);
				arr.pushString(values[i][1]);
				data.pushArray(arr);
			}
			successCallback.invoke(data);
		}

	/*
	   @ReactMethod
	   public void multiGet(String[] keys, Callback successCallback){

	   SharedHandler.init(getReactApplicationContext());
	   String[][] value = SharedDataProvider.getMultiSharedValues(keys);
	   successCallback.invoke(value);

	   }	

	 */

	@ReactMethod
		public void getAllKeys(Callback successCallback){
			initSharedHandler();
			String[] keys = SharedDataProvider.getAllKeys();
			WritableNativeArray data = new WritableNativeArray();
			for(int i=0; i<keys.length; i++){
				data.pushString(keys[i]);
			}
			successCallback.invoke(data);
		}



	@ReactMethod
		public void clear(){
			initSharedHandler();
			SharedDataProvider.clear();
		}


	@ReactMethod
		public void removeItem(String key) {
			initSharedHandler();
			SharedDataProvider.deleteSharedValue(key);
		}


}
