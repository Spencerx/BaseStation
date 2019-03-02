package com.decibel.civilianc2.radios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DRA818 implements ITransceiver{
    public DRA818(ISerialComms serialComms){
        this.serialComms = serialComms;
    }

    @Override
    public void setFrequency(int rx, int tx) throws IOException {
        String command = String.format(SetFrequenciesCommand, tx, rx, this.squelch);
        String result = this.serialComms.sendCommand(command);

        if(isSuccess(result)) {
            this.txFrequency = tx;
            this.rxFrequency = rx;
            for (IEventListener listener : eventListeners) {
                listener.onFrequencyChanged(rx, tx, this.squelch);
            }
        }
    }

    @Override
    public String getName() {
        return DeviceName;
    }

    @Override
    public void setReceiveFreq(int receiveFreq) throws IOException {

    }

    @Override
    public int getReceiveFreq() {
        return 0;
    }

    @Override
    public int getVolume() {
        return 0;
    }

    @Override
    public void setVolume(int v) throws IOException {

    }

    @Override
    public void setSquelch(int s) throws IOException {

    }

    @Override
    public int getSquelch() {
        return 0;
    }

    @Override
    public int getSignalLevel() {
        return 0;
    }

    @Override
    public boolean scan(int freq) throws IOException {
        return false;
    }

    @Override
    public boolean isConnected() throws IOException {
        return false;
    }

    @Override
    public void addEventListener(IEventListener listener) {

    }

    @Override
    public void removeEventListener(IEventListener listener) {

    }

    @Override
    public int getTransmitFreq() {
        return 0;
    }

    @Override
    public void setTransmitFreq(int transmitFreq) throws IOException {

    }

    @Override
    public void setToneSquelch(int frequency) throws IOException {

    }

    @Override
    public void enableToneSquelch(boolean enable) throws IOException {

    }

    @Override
    public int getToneSquelchFrequency() {
        return this.ctcssFrequency;
    }

    @Override
    public boolean getToneSquelchEnabled() {
        return this.ctcssEnabled;
    }

    @Override
    public ISoftwareTransmitter getSoftwareTransmitter(){
        return null;
    }

    private boolean isSuccess(String result){
        String [] tokens = result.split(":");
        return (tokens.length >= 2 && tokens[1].startsWith("0"));
    }


    private ISerialComms serialComms;
    private int txFrequency;
    private int rxFrequency;
    private int squelch;
    private int ctcssFrequency;
    private boolean ctcssEnabled;
    private List<IEventListener> eventListeners = new ArrayList<>();

    private final static String SetFrequenciesCommand = "AT+DMOSETGROUP=1,%06.4f,%06.4f,0000,%d,0000";
    private final static String DeviceName = "DRA-818V";
}