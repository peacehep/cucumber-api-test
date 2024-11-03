package com.cucumber.api.test.util;

import java.io.IOException;
import java.util.Properties;

public class GlobalSetting {

    public static final Properties prop = getProperties();
    public static final String BASE_URL = prop.getProperty("baseURL");
    public static final String X_CLIENT_ID = prop.getProperty("xClientId");
    public static final String X_API_KEY = prop.getProperty("xApiKey");
    public static final String POST_ACCESS_API = prop.getProperty("postAccess");
    public static final String GET_CURRENT_RATES_API = prop.getProperty("getCurrentRates");


    public static Properties getProperties() {
        Properties prop = new Properties();
        try {
            prop.load(GlobalSetting.class.getClassLoader().getResourceAsStream("prop.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
}
