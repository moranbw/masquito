package com.moranbw.masquito.leaseservice;

import java.util.Date;

public class Lease {
    private Date leaseExpiry;
    private String macAddress;
    private String ipAddress;
    private String hostName;
    private String clientId;

    public Lease() {
    }

    public Date getLeaseExpiry() {
        return this.leaseExpiry;
    }

    public void setLeaseExpiry(Date aLeaseExpiry) {
        this.leaseExpiry = aLeaseExpiry;
    }

    public String getMacAddress() {
        return this.macAddress;
    }

    public void setMacAddress(String aMacAddress) {
        this.macAddress = aMacAddress;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String aIpAddress) {
        this.ipAddress = aIpAddress;
    }

    public String getHostName() {
        return this.hostName;
    }

    public void setHostName(String aHostName) {
        this.hostName = aHostName;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String aClientId) {
        this.clientId = aClientId;
    }
}
