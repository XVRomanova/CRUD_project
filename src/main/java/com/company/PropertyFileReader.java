package com.company;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class PropertyFileReader {

    public String getPropValue(String key) throws IOException {
        FileInputStream fileInputStream;
        Properties properties = new Properties();
        HashMap<String,String> list = new HashMap<>();

        try{
            fileInputStream = new FileInputStream("src/main/resources/config.properties");
            properties.load(fileInputStream);

            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");

            list.put("url",url);
            list.put("user", user);
            list.put("password", password);

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return list.get(key);
    }
}