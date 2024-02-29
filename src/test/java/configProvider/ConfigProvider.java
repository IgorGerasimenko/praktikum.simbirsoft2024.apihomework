package configProvider;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Интерфейс чтения конфигураций из файла application.conf
  * @version 1.0
 * @autor Игорь Герасименко
 */
public interface ConfigProvider {

    Config config = readConfig();
    static Config readConfig(){
        return ConfigFactory.load("application.conf");
    }
    String URL = readConfig().getString("url");

}


