package com.vyw.differences;

public class BtDeviceInfoModel {
    private String deviceName, deviceHardwareAddress;

    public BtDeviceInfoModel(){}

    public BtDeviceInfoModel(String deviceName, String deviceHardwareAddress){
        this.deviceName = deviceName;
        this.deviceHardwareAddress = deviceHardwareAddress;
    }

    public String getDeviceName(){return deviceName;}

    public String getDeviceHardwareAddress(){return deviceHardwareAddress;}

}
