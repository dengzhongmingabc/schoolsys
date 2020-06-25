package com.honorfly.schoolsys.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.sysconfig")
public class AppConfig {
    private int sessionExpire;

    public int getSessionExpire() {
        return sessionExpire;
    }

    public void setSessionExpire(int sessionExpire) {
        this.sessionExpire = sessionExpire;
    }
}
