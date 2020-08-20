package io.colborne.music.player.bluetooth;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class MusicPlayerBluetoothServer {

    private static final Logger LOG = Logger.getLogger(MusicPlayerBluetoothServer.class.getName());

    private static LocalDevice localDevice;

    private static final String myServiceName = "Colborne_Music_Player";

    private static final String myServiceUUID = "2d26618601fb47c28d9f10b8ec891363";

    private UUID MY_SERVICE_UUID = new UUID(myServiceUUID, false);

    String connURL = "btspp://localhost: " + MY_SERVICE_UUID.toString() + "; + name=" + myServiceName;

    private UUID[] uuids = {MY_SERVICE_UUID};

    private StreamConnectionNotifier streamConnectionNotifier;

    public static LocalDevice getLocalDevice() {
        return localDevice;
    }

    public synchronized void initialise() {
        // Reset the local bluetooth device:
        localDevice = null;
        try {
            localDevice = LocalDevice.getLocalDevice();
        } catch (BluetoothStateException e) {
            LOG.warning("Bluetooth hardware not found! Please ensure that the devices bluetooth device is connected and configured correctly.");
        }
    }

    public synchronized void makeBluetoothDeviceTemporarilyVisible() throws BluetoothStateException{
        if (localDevice == null) {
            throw new BluetoothStateException("Bluetooth device has not been initialised!");
        }

        localDevice.setDiscoverable(DiscoveryAgent.LIAC);

    }

    public synchronized void start() throws Exception {
        // reset it before you create another one
        streamConnectionNotifier = null;
        try {
            streamConnectionNotifier = (StreamConnectionNotifier) Connector.open(connURL);
        } catch (IOException e) {
            // TODO: Make a proper exception here:
            throw new Exception(e);
        }
    }

    public synchronized void waitForClient() throws InterruptedException {

        try {
            // Wait for client connection
            StreamConnection conn = streamConnectionNotifier.acceptAndOpen();

            // New client connection accepted; get a handle on it
            RemoteDevice rd = RemoteDevice.getRemoteDevice(conn);

            System.out.println("New client connection... " + rd.getFriendlyName(false));
//
//            // Read input message, in this example a String
//            DataInputStream dataIn = conn.openDataInputStream();
//            String s = dataIn.readUTF();
//            // Pass received message to incoming message listener

        } catch (IOException e) {
            LOG.warning("Something is not working!!!!!!");
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        }
    }
}
