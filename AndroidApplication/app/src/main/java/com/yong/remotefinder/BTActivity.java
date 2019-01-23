package com.yong.remotefinder;

import android.app.*;
import android.bluetooth.*;
import android.content.*;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;

public class BTActivity extends Activity 
{
	String mStrDelimiter = "\n";
	
	static final int REQUEST_ENABLE_BT = 10;
    int mPairedDeviceCount = 0;
    Set<BluetoothDevice> mDevices;
    BluetoothAdapter mBluetoothAdapter;
	BluetoothDevice mRemoteDevice;
	BluetoothSocket mSocket;

	OutputStream mOutputStream;
    InputStream mInputStream;

	//검색된 블루투스 기기로부터 기기명을 받아옴
	//Get device name from searched  bluetooth devices
	BluetoothDevice getDeviceFromBondedList(String name) {
        BluetoothDevice selectedDevice = null;
        for(BluetoothDevice deivce : mDevices) {
            if(name.equals(deivce.getName())) {
                selectedDevice = deivce;
                break;
            }
        }
        return selectedDevice;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt);
		checkBluetooth();
    }
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode) {
			case REQUEST_ENABLE_BT:
				if(resultCode == RESULT_OK) {
					selectDevice();
				}
				else if(resultCode == RESULT_CANCELED) {
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.bt_unavailable), Toast.LENGTH_LONG).show();
					finish();
				}
				break;
		}
        super.onActivityResult(requestCode, resultCode, data);
	}

	public void ringLED(View v){
		sendData("LED");
	}

	public void ringVIB(View v){
		sendData("VIB");
	}
	
	//추후  연결의 안정성을 위해 통신 종료
	//Exit connnection for the stability of next connection
	public void exit(View v){
		sendData("EXIT");
        try{
            mInputStream.close();
			mOutputStream.close(); 
            mSocket.close();
        }catch(Exception e){
        	Log.e("Exception", e.toString());
		}
		finish();
	}
	
	//아두이노 기기로 데이터를 전송 (진동모터를 작동할것인지, LED를 작동할것인지)
	//Send data to Arduino device (Blink LED or make VIBRATION)
    void sendData(String msg) {
        msg += mStrDelimiter;
        try{
            mOutputStream.write(msg.getBytes());
        }catch(Exception e) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.bt_data_failed), Toast.LENGTH_LONG).show();
            finish();
        }
    }

	//기기가 블루투스 통신을 지원하는지 확인
	//Check if device supports bluetooth feature or not
	void checkBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null ) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.bt_unsupported), Toast.LENGTH_LONG).show();
            finish();
        }
        else {
            if(!mBluetoothAdapter.isEnabled()) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.bt_disabled), Toast.LENGTH_LONG).show();
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); 
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
            else
                selectDevice();
        }
    }

	//기기에 페어링되어있는 블루투스 기기 목록 표시 및 사용자의 리모컨 선택
	//Show paired bluetooth devices and  user's remote  controller selection
	void selectDevice() {
        mDevices = mBluetoothAdapter.getBondedDevices();
        mPairedDeviceCount = mDevices.size();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.bt_selection));
		List<String> listItems = new ArrayList<String>();
        for(BluetoothDevice device : mDevices) {
            listItems.add(device.getName());
        }
		listItems.add(getResources().getString(R.string.bt_list_no_device));
        listItems.add(getResources().getString(R.string.cancel));
		final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);
		listItems.toArray(new CharSequence[listItems.size()]);
		builder.setItems(items, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int item) {
					if(item == mPairedDeviceCount+1) {
						Toast.makeText(getApplicationContext(), getResources().getString(R.string.canceled), Toast.LENGTH_LONG).show();
						finish();
					}else if(item == mPairedDeviceCount){
						AlertDialog.Builder builder = new AlertDialog.Builder(BTActivity.this);
						builder.setTitle(getResources().getString(R.string.bt_need_pair));
						builder.setMessage(getResources().getString(R.string.bt_need_pair_info));
						builder.setCancelable(false);
						builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener(){
								public void onClick(DialogInterface dialog, int id){
									startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
								}
							});
						builder.show();
					}else {
						connectToSelectedDevice(items[item].toString());
					}
				}
			});
		builder.setCancelable(false);
		AlertDialog alert = builder.create();
		alert.show();
    }

	//사용자가 선택한 리모컨의로의 연결 시도
	//Try connection to remote controller which user selected
	void connectToSelectedDevice(String selectedDeviceName) {
        mRemoteDevice = getDeviceFromBondedList(selectedDeviceName);
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        try {
            mSocket = mRemoteDevice.createRfcommSocketToServiceRecord(uuid);
            mSocket.connect();
			mOutputStream = mSocket.getOutputStream();
            mInputStream = mSocket.getInputStream();
        }catch(Exception e) {
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.bt_fail_connection), Toast.LENGTH_LONG).show();
            finish();
        }
    }
}

