package net.joshscoper.sugarmc.files;

import net.joshscoper.sugarmc.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class ConfigManager {

    private final Main main;
    private FileConfiguration dataConfig;
    private File configFile;

    public ConfigManager(Main main) {
        this.main = main;
        saveDefaultConfig();
    }

    public void reloadDataConfig(){
        if (this.configFile == null){
            this.configFile = new File(this.main.getDataFolder(), "config.yml");
            this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);

            InputStream defaultStream = this.main.getResource("config.yml");
            if (defaultStream  != null){
                YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
                this.dataConfig.setDefaults(defaultConfig);
            }
        }
    }

    public void setDataConfig(String path, String value){
        if (this.dataConfig == null|| this.configFile == null){
            return;
        }
        this.dataConfig.set(path, value);
        saveDataConfig();
    }

    public FileConfiguration getDataConfig() {
        if (this.dataConfig == null) {
            reloadDataConfig();
        }
        return this.dataConfig;
    }


    public void saveDataConfig() {
        if (this.dataConfig == null || this.configFile == null) {
            return;
        }
        try {
            this.getDataConfig().save(this.configFile);
        } catch (IOException e){
            this.main.getLogger().log(Level.SEVERE, "Error Saving config.yml", e);
        }
    }

    public void saveDefaultConfig(){
        if (getDataConfig() == null){
            reloadDataConfig();
        }

        if (!this.configFile.exists()){
            this.main.saveResource("config.yml", false);
        }

    }
}
