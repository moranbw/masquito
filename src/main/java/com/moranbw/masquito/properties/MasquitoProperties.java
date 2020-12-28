package com.moranbw.masquito.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "masquito")
public class MasquitoProperties {
    private String dnsMasqConfPath;
    private String dnsMasqLeasesPath;
    private String hostsPath;
    private String resolvConfPath;

    public String getDnsMasqConfPath() {
        return this.dnsMasqConfPath;
    }

    public void setDnsMasqConfPath(String aDnsMasqConfPath) {
        this.dnsMasqConfPath = aDnsMasqConfPath;
    }

    public String getDnsMasqLeasesPath() {
        return this.dnsMasqLeasesPath;
    }

    public void setDnsMasqLeasesPath(String aDnsMasqLeasesPath) {
        this.dnsMasqLeasesPath = aDnsMasqLeasesPath;
    }

    public String getHostsPath() {
        return this.hostsPath;
    }

    public void setHostsPath(String aHostsPath) {
        this.hostsPath = aHostsPath;
    }

    public String getResolvConfPath() {
        return this.resolvConfPath;
    }

    public void setResolvConfPath(String aResolvConfPath) {
        this.resolvConfPath = aResolvConfPath;
    }
}
