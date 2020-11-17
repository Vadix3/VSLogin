package com.example.vslogin;

public class DataContainer {

    private int batteryLevel = 0;
    private int releaseVersion = 0;
    private int sdkVersion = 0;
    private String deviceName = "";
    private long deviceRam = 0l;
    private int deviceVolume = 0;
    private String deviceOrientation = "";
    private String appInstalled="";

    public DataContainer() {
    }

    public DataContainer(int batteryLevel, int releaseVersion, int sdkVersion, String deviceName, long deviceRam
            , int deviceVolume, String deviceOrientation,String appInstalled) {
        this.batteryLevel = batteryLevel;
        this.releaseVersion = releaseVersion;
        this.sdkVersion = sdkVersion;
        this.deviceName = deviceName;
        this.deviceRam = deviceRam;
        this.deviceVolume = deviceVolume;
        this.deviceOrientation = deviceOrientation;
        this.appInstalled=appInstalled;
    }

    public String getAppInstalled() {
        return appInstalled;
    }

    public void setAppInstalled(String appInstalled) {
        this.appInstalled = appInstalled;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public int getReleaseVersion() {
        return releaseVersion;
    }

    public void setReleaseVersion(int releaseVersion) {
        this.releaseVersion = releaseVersion;
    }

    public int getSdkVersion() {
        return sdkVersion;
    }

    @Override
    public String toString() {
        return "DataContainer{" +
                "batteryLevel=" + batteryLevel +
                ", releaseVersion=" + releaseVersion +
                ", sdkVersion=" + sdkVersion +
                ", deviceName='" + deviceName + '\'' +
                ", deviceRam=" + deviceRam +
                ", deviceVolume=" + deviceVolume +
                ", deviceOrientation='" + deviceOrientation + '\'' +
                ", appInstalled='" + appInstalled + '\'' +
                '}';
    }

    public void setSdkVersion(int sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public long getDeviceRam() {
        return deviceRam;
    }

    public void setDeviceRam(long deviceRam) {
        this.deviceRam = deviceRam;
    }

    public int getDeviceVolume() {
        return deviceVolume;
    }

    public void setDeviceVolume(int deviceVolume) {
        this.deviceVolume = deviceVolume;
    }

    public String getDeviceOrientation() {
        return deviceOrientation;
    }

    public void setDeviceOrientation(String deviceOrientation) {
        this.deviceOrientation = deviceOrientation;
    }
}
