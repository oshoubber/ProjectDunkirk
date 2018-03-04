//package com.example.lolru.projectdunkirk;
//
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothServerSocket;
//import android.bluetooth.BluetoothSocket;
//import android.util.Log;
//
//import org.json.JSONObject;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.Set;
//import java.util.UUID;
//
//import static android.content.ContentValues.TAG;
//
///**
// * Created by gchandler on 3/3/18.
// */
//
//public class DunkirkBT2
//{
//    final BluetoothAdapter mBluetoothAdapter;
//    Set<BluetoothDevice> pairedDevices;
//    ArrayList<JSONObject> currData;
//
//    public void DunkirkBT2(BluetoothAdapter bta)
//    {
//        //Setting up bluetooth intent filter
//        mBluetoothAdapter = bta;
//        currData = new ArrayList<>();
//
//        pairedDevices = mBluetoothAdapter.getBondedDevices();
//        if (pairedDevices.size() > 0) {
//            // There are paired devices. Get the name and address of each paired device.
//            for (BluetoothDevice device : pairedDevices) {
//                String deviceName = device.getName();
//                String deviceHardwareAddress = device.getAddress(); // MAC address
//                Log.e("Found device", "" + deviceHardwareAddress);
//            }
//        }
//    }
//
//    public void startServerThread()
//    {
//        AcceptThread th0 = new AcceptThread();
//        th0.start();
//        Log.e("started server thread!", "async");
//    }
//
//    public void startClientThread(ArrayList<JSONObject> temp_objects)
//    {
//        Log.e("Num Devices: ", "");
//        if (pairedDevices.size() > 0) {
//            // There are paired devices. Get the name and address of each paired device.
//            for (BluetoothDevice device : pairedDevices) {
//                String deviceName = device.getName();
//                String deviceHardwareAddress = device.getAddress(); // MAC address
//                Log.e("Connecting to device", deviceName  + " : " + deviceHardwareAddress);
//
//                //This may not be multi thread compatible, and may not wait long enough
//                //This means we would not be able to connect multiple threads
//                //Try to find a way to block before connecting again
//                ConnectThread th1 = new ConnectThread(device, temp_objects);
//                th1.run(); //run
//                Log.e("Finished waiting"," for thread!");
//            }
//        }
//    }
//
//    private class AcceptThread extends Thread {
//        private final BluetoothServerSocket mmServerSocket;
//
//        public AcceptThread() {
//            // Use a temporary object that is later assigned to mmServerSocket
//            // because mmServerSocket is final.
//            BluetoothServerSocket tmp = null;
//            try {
//                // MY_UUID is the app's UUID string, also used by the client code.
//                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("Glen", UUID.fromString("83d5f5a0-1f48-11e8-b467-0ed5f89f718b"));
//            } catch (IOException e) {
//                Log.e(TAG, "Socket's listen() method failed", e);
//            }
//            mmServerSocket = tmp;
//        }
//
//        public void run() {
//            BluetoothSocket socket = null;
//            // Keep listening until exception occurs or a socket is returned.
//            while (true) {
//                try {
//                    socket = mmServerSocket.accept();
//                } catch (IOException e) {
//                    Log.e(TAG, "Socket's accept() method failed", e);
//                    break;
//                }
//
//                if (socket != null) {
//                    // A connection was accepted. Perform work associated with
//                    // the connection in a separate thread.
//                    Log.e("SUCCESS!!!", "Device connected to me");
//                    manageMyConnectedSocket(socket);
//                    cancel();
//                    break;
//                }
//            }
//        }
//
//        // Closes the connect socket and causes the thread to finish.
//        public void cancel() {
//            try {
//                mmServerSocket.close();
//            } catch (IOException e) {
//                Log.e(TAG, "Could not close the connect socket", e);
//            }
//        }
//
//        private int manageMyConnectedSocket(BluetoothSocket socket) {
//            //Do something here
//
//            byte[] buf = new byte[1024]; // mmBuffer store for the stream
//
//            InputStream tmpIn = null;
//
//            // Get the input and output streams; using temp objects because
//            // member streams are final.
//            try {
//                tmpIn = socket.getInputStream();
//            } catch (IOException e) {
//                Log.e(TAG, "Error occurred when creating input stream", e);
//            }
//
//            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//            int numBytes;
//            ArrayList<JSONObject> temp = new ArrayList<>();
//            int i = 0;
//            while (true) {
//                try {
//                    Log.e("Got messsage-0", "!");
//                    // Read from the InputStream.
////                    while ( -1 != ( numBytes = tmpIn.read(buf) ) ) {
////                        buffer.write( buf, 0, numBytes );
////                    }
////                    buffer.toString()
//                    numBytes = tmpIn.read(buf);
//                    // Send the obtained bytes to the UI activity.
//                    if(numBytes > 0) {
//                        temp.add(new JSONObject(new String(buf, "US-ASCII")) );
//                        Log.e("RM: ", "Sz:" + numBytes + ":" + temp.get(i));
//                        Log.e("Name: ", "" + temp.get(i).get("firstName"));
//                    }
//                    Log.e("Got messsage", "!");
//
//                } catch (Exception e) {
//                    Log.d(TAG, "Input stream was disconnected", e);
//                    break;
//                }
//            }
//
//            ArrayList<JSONObject> unique_entries = filterData(temp);
//
////            Log.d("Unique entries ", "" + unique_entries);
//            //Send unique entries to neighbors
//            Log.e("About to echo!", "..");
//
//            if(unique_entries.size() == 0)
//                return 0;
//            startClientThread(unique_entries);
//
//            return 0;
//        }
//
//        private ArrayList<JSONObject> filterData(ArrayList<JSONObject> new_entries)
//        {
//            ArrayList<JSONObject> ret_arr = new ArrayList<>();
//            int orig_size = currData.size();
//            for(int i = 0; i < new_entries.size(); i++)
//            {
//                boolean unique = true;
//                for(int j = 0; j < orig_size; j++)
//                {
//                    try {
//                        //Check mac addresses
//                        if (currData.get(j).get("mac").toString().equals(new_entries.get(i).get("mac").toString())) {
//                            unique = false;
//
//                            //> Newer than existing data
//                            if (Integer.parseInt(new_entries.get(i).get("time").toString()) > Integer.parseInt(currData.get(i).get("time").toString())) {
//                                currData.set(j, new_entries.get(i));
//                                ret_arr.add(new_entries.get(i));
//                                break;
//                            }
//                        }
//                    }catch(Exception e)
//                    {
//                        Log.e("Error reading", "JSON object");
//                    }
//
//                }
//                if(unique)
//                {
//                    ret_arr.add(new_entries.get(i));
////                    currData.add(new_entries.get(i));
//                }
//            }
//            for(int i = 0; i < ret_arr.size(); i++)
//            {
//                currData.add(ret_arr.get(i));
//            }
//            Log.e("Done filtering", " data");
//
//            return ret_arr;
//        }
//    }
//
//    private class ConnectThread extends Thread {
//        private final BluetoothSocket mmSocket;
//        private final BluetoothDevice mmDevice;
//        ArrayList<JSONObject> connect_objects;
//
//        public ConnectThread(BluetoothDevice device, ArrayList<JSONObject> temp_objects) {
//            // Use a temporary object that is later assigned to mmSocket
//            // because mmSocket is final.
//            BluetoothSocket tmp = null;
//            OutputStream tmpOut = null;
//            mmDevice = device;
//
//            try {
//                // Get a BluetoothSocket to connect with the given BluetoothDevice.
//                // MY_UUID is the app's UUID string, also used in the server code.
//                tmp = device.createRfcommSocketToServiceRecord(UUID.fromString("83d5f5a0-1f48-11e8-b467-0ed5f89f718b"));
//            } catch (IOException e) {
//                Log.e(TAG, "Socket's create() method failed", e);
//            }
//
//            connect_objects = temp_objects;
//            mmSocket = tmp;
//        }
//
//        public void run() {
//            // Cancel discovery because it otherwise slows down the connection.
//            mBluetoothAdapter.cancelDiscovery();
//
//            while(true) {
//                try {
//                    // Connect to the remote device through the socket. This call blocks
//                    // until it succeeds or throws an exception.
//
//                    //This blocks, so if the function times out, UI might freeze
//                    mmSocket.connect();
//                } catch (IOException connectException) {
//                    // Unable to connect; close the socket and return.
//                    try {
//                        mmSocket.close();
//                    } catch (IOException closeException) {
//                        Log.e(TAG, "Could not close the client socket", closeException);
//                    }
//                    return;
//                }
//                Log.e("SUCCESS!!!", "succesfully connected to device");
//
//                // The connection attempt succeeded. Perform work associated with
//                // the connection in a separate thread.
//                if (manageMyConnectedSocket(mmSocket) < 0) //There was an error
//                {
//                    //Retry connection, didn't send bytes correctly
////                    continue;
//                } else {
////                    cancel();
////                    break;
//                }
//                cancel();
//            }
//        }
//
//        // Closes the client socket and causes the thread to finish.
//        public void cancel() {
//            try {
//                mmSocket.close();
//            } catch (IOException e) {
//                Log.e(TAG, "Could not close the client socket", e);
//            }
//        }
//
//        private int manageMyConnectedSocket(BluetoothSocket socket)
//        {
//            //Do something here
//            OutputStream tmpOut = null;
//            byte[] buf = new byte[1024];
//
//            try {
//                tmpOut = socket.getOutputStream();
//            } catch (IOException e) {
//                Log.e(TAG, "Error occurred when creating output stream", e);
//                return -1;
//            }
//
//            String temp = "";
//            Log.e("WriteMyJson: ", "" + connect_objects.size());
//            for(int i = 0; i < connect_objects.size(); i++) {
//                try {
//                    temp = connect_objects.get(i).toString();
//                    Log.e("Sending Bytes ", "" + temp);
//                    buf = temp.getBytes();
//                    // Read from the InputStream.
//                    tmpOut.write(buf);
//
//                } catch (IOException e) {
//                    Log.d(TAG, "Input stream was disconnected", e);
//                    break;
//                }
//            }
//
//            return 0;
//        }
//    }
//}
