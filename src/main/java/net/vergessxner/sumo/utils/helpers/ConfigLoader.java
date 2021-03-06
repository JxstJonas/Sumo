package net.vergessxner.sumo.utils.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

/**
 * @author Jonas H.
 * Created: 24 Juni 2021
 */

public class ConfigLoader<T> {

    public static final Gson PRETTY_GSON = new GsonBuilder().setPrettyPrinting().create();

    private final File configFile;

    private Class<T> aClass;

    private T config;


    public ConfigLoader(File file, Class<T> type) {
        this.configFile = file;
        this.aClass = type;

        try {
            config = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            BufferedReader reader = null;
            reader = new BufferedReader(new FileReader(this.configFile));

            this.config = PRETTY_GSON.fromJson(reader, aClass);
            if(config == null)
                set(aClass.newInstance());
            reader.close();
        } catch (IOException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void set(T instance) {
        try {
            if (!this.configFile.exists())
                configFile.createNewFile();

            Writer writer = new FileWriter(this.configFile);

            PRETTY_GSON.toJson(instance, writer);

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        load();

    }

    public static Gson getPrettyGson() {
        return PRETTY_GSON;
    }

    public File getConfigFile() {
        return configFile;
    }

    public Class<T> getaClass() {
        return aClass;
    }

    public T getConfig() {
        return config;
    }

}