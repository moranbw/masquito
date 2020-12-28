package com.moranbw.masquito.common;

public enum DnsMasqCommands {
    START("systemctl start dnsmasq"), 
    STOP("systemctl stop dnsmasq"),
    RESTART("systemctl restart dnsmasq");

    private final String[] command;

    private DnsMasqCommands(String aCommand) {
        this.command = aCommand.split(" ");
    }

    public String[] getCommand() {
        return this.command;
    }

}