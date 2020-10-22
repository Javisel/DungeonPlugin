package us.someteamname.partysystem.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
  private final String n;
  
  private FileConfiguration fc;
  
  private File file;
  
  private final JavaPlugin plugin;
  
  private static List<Config> configs = new ArrayList<>();
  
  public Config(String n) {
    this.plugin = JavaPlugin.getProvidingPlugin(getClass());
    this.n = n;
    configs.add(this);
  }
  
  public static void copy(InputStream in, File file) {
    try {
      OutputStream out = new FileOutputStream(file);
      byte[] buf = new byte[1024];
      int len;
      while ((len = in.read(buf)) > 0)
        out.write(buf, 0, len); 
      out.close();
      in.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public String getName() {
    if (this.n == null)
      try {
        throw new Exception();
      } catch (Exception e) {
        e.printStackTrace();
      }  
    return this.n;
  }
  
  public JavaPlugin getInstance() {
    if (this.plugin == null)
      try {
        throw new Exception();
      } catch (Exception e) {
        e.printStackTrace();
      }  
    return this.plugin;
  }
  
  public static Config getConfig(String n) {
    for (Config c : configs) {
      if (c.getName().equals(n))
        return c; 
    } 
    return new Config(n);
  }
  
  public boolean delete() {
    return getFile().delete();
  }
  
  public boolean exists() {
    if (this.fc == null || this.file == null) {
      File temp = new File(getDataFolder(), String.valueOf(getName()) + ".yml");
      if (!temp.exists())
        return false; 
      this.file = temp;
    } 
    return true;
  }
  
  public File getFile() {
    if (this.file == null) {
      this.file = new File(getDataFolder(), String.valueOf(getName() + ".yml"));
      if (!this.file.exists())
        try {
          this.file.createNewFile();
        } catch (IOException e) {
          e.printStackTrace();
        }  
    } 
    return this.file;
  }
  
  public FileConfiguration getConfig() {
    if (this.fc == null)
      this.fc = (FileConfiguration)YamlConfiguration.loadConfiguration(getFile()); 
    return this.fc;
  }
  
  public File getDataFolder() {
    File dir = new File(Config.class.getProtectionDomain().getCodeSource().getLocation().getPath().replaceAll("%20", " "));
    File d = new File(dir.getParentFile().getPath(), getInstance().getName());
    if (!d.exists())
      d.mkdirs(); 
    return d;
  }
  
  public void reload() {
    if (this.file == null) {
      this.file = new File(getDataFolder(), String.valueOf(getName() + ".yml"));
      if (!this.file.exists())
        try {
          this.file.createNewFile();
        } catch (IOException e) {
          e.printStackTrace();
        }  
      this.fc = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
      File defConfigStream = new File(this.plugin.getDataFolder(), String.valueOf(getName()) + ".yml");
      if (defConfigStream != null) {
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        this.fc.setDefaults((Configuration)defConfig);
      } 
    } 
  }
  
  public void saveConfig() {
    try {
      getConfig().save(getFile());
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
}
