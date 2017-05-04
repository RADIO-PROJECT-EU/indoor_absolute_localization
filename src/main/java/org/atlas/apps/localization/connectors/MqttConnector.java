package org.atlas.apps.localization.connectors;

import org.atlas.apps.localization.beacons.Measurement;
import org.atlas.apps.localization.storage.Storage;
import org.atlas.apps.localization.utils.ble.BluetoothBeaconData;
import org.atlas.apps.localization.utils.ble.BluetoothUtil;
import org.atlas.gateway.components.wsn.messages.WSNMessage.Advertisment;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttConnector implements MqttCallback{

	private static final Logger logger = LoggerFactory.getLogger(MqttConnector.class);
	private MqttClient mqclient;
	
	public MqttConnector(boolean connect){
		if( !connect ){
			logger.info("MQTT Connection disabled by user");
			return;
		}
		try {
			this.mqclient = new MqttClient("<>", "localization-agent-impl", new MemoryPersistence());
			this.mqclient.setCallback(this);
			this.mqclient.connect(this.getConnectioOptions());
			logger.info("Successfully Connected to main gateway");
			this.mqclient.subscribe("wsn/ble/devices/advertisments");
			logger.info("Suscribed to topic get advertisments: wsn/ble/devices/advertisments");
		} catch (MqttException e) {
			logger.error("Error while trying to connect to MQTT provide",e); 
		}
	}

	private MqttConnectOptions getConnectioOptions() {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setUserName("<>");
		options.setPassword("<>".toCharArray());
		options.setAutomaticReconnect(true);
		options.setCleanSession(true);
		return options;
	}

	public MqttClient getClient(){
		return this.mqclient;
	}
	
	@Override
	public void connectionLost(Throwable cause) {}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		Advertisment advertisment = Advertisment.parseFrom(message.getPayload());
		BluetoothBeaconData beacon = BluetoothUtil.parseLEAdvertisingReport(advertisment.getData().toByteArray(), "000D").get(0);
		logger.debug("Message Arrived: " + beacon.address + " : " + beacon.rssi);
		Storage.addMeasurement(new Measurement(beacon.address, beacon.rssi));
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {}
	
}
