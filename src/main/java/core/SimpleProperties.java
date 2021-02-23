package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

@SuppressWarnings({"java:S1659", "java:S116", "java:S3066"})

enum Config 
        { 
            FIELD01 ("Selected Directory", System.getProperty("user.home")) , 
            FIELD02 ("Selected Mode", "ESPCNx2") ,
            FIELD03 ("Dark Mode", true);
            
        
            final String KEY ,   
                         DEFAULT_VALUE;

            private Config (String key, Object defaultValue) 
            { 
                this.KEY = key;
                this.DEFAULT_VALUE = String.valueOf(defaultValue);
            } 
        
            public void setValue (String newValue) 
            {  
                SimpleProperties.getInstance().setProperty(this, newValue); 
            }

            public void setValue (boolean newValue) 
            { 
                SimpleProperties.getInstance().setProperty(this, String.valueOf(newValue)); 
            }

            public void setValue (int newValue) {
                SimpleProperties.getInstance().setProperty(this, String.valueOf(newValue));
            }

            public String getString () 
            { 
                return SimpleProperties.getInstance().getProperty(this); 
            }

            public boolean getBoolean () {
                return SimpleProperties.getInstance().getBooleanProperty(this);
            }

            public int getInt() {
                return SimpleProperties.getInstance().getIntProperty(this);
            }
        } 

@SuppressWarnings("java:S106")
public class SimpleProperties {

    public static final String DEFAULT_PATH = System.getProperty("user.home") + File.separator + ".EasyUpscaler" + File.separator + "config.xml";   

    private static SimpleProperties instance;

    private String propertiesFilePath;

    private Properties properties;

    // PUBLIC singleton instance getter
    public static SimpleProperties getInstance() {
        if(instance == null) {
            instance = new SimpleProperties(DEFAULT_PATH);
            System.out.println("created new properties instance");
        }
        return instance;
    }

    // PRIVATE CONSTRUCTOR create config
    private SimpleProperties(String path) {
        propertiesFilePath = path;
        properties = new Properties();
        // checkPath create/check propertiesFilePath - return false if failed
        if (checkPath())
            try (FileInputStream in = new FileInputStream(propertiesFilePath)) {
                // load properties from xml
                properties.loadFromXML(in);
            } catch (InvalidPropertiesFormatException e) {
                System.err.println("Format error on config.xml -> writing default config");
            } catch (IOException e) {
                System.err.println("RARE Error Opening FileInputStream");
            } finally {
                // create missing properties and set to default - returns false if nothing changed
                if (updateProperties())
                    store();
            }
        else
            System.err.println("oh no.. config wasn't loaded");
    }

    // PUBLIC set property
    public void setProperty(String key, String value) {
        // check that new value is different from old value
        if (!properties.getProperty(key).equals(value)) {
            properties.setProperty(key, value);
            store();
        } else
            System.err.println("Trying to set property '" + key + "' to the same value (" + value + ")");
    }

    public void setProperty(Config field, String value) {
        setProperty(field.KEY , value);
    }

    // PUBLIC get property
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(Config field) {
        return getProperty(field.KEY);
    }

    public boolean getBooleanProperty(String key) {
        return properties.getProperty(key).equals("true");
    }

    public boolean getBooleanProperty(Config field) {
        return getBooleanProperty(field.KEY);
    }

    public int getIntProperty(String key) {
        return Integer.valueOf(properties.getProperty(key));
    }

    public int getIntProperty(Config field) {
        return getIntProperty(field.KEY);
    }

    // store data to config file
    private void store() {
        try (FileOutputStream outStream = new FileOutputStream(propertiesFilePath)) {
            properties.storeToXML(outStream, "PNG Upscale program parameters");
            System.out.println("Stored properties :" + properties.toString());
        } catch (IOException e) {
            System.err.println("IOException");
            e.printStackTrace();
        }
    }

    // create missing properties and set to default - returns false if nothing changed
    private boolean updateProperties() {
        boolean changed = false;

        for(Config field : Config.values()) {
            if (!properties.containsKey(field.KEY)) {
                properties.setProperty(field.KEY, field.DEFAULT_VALUE);
                changed = true;
                System.err.println("Properties didn't contain " + field.KEY);
            }
        }
        return changed;
    }

    // check path to config.xml
    private boolean checkPath() {
        File config = new File(propertiesFilePath);
        try {
            if (Files.notExists(config.toPath())) {
                // create path to config if it doesn't exist (create directories)
                Files.createDirectories(config.toPath().getParent());
                // create config.txt
                if (!config.createNewFile()) {
                    System.err.println("Error creating file");
                    return false;
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("IO Exception");
            return false;
        }
    }
}