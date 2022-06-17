package cn.zjavax.demo.config;

import cn.zjavax.demo.util.EncryptionUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.ObjectUtils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author ausstaker (https://github.com/Ausstaker)
 * @since Nov 2021
 */
public class EncryptedPropertyConfigurer extends PropertyPlaceholderConfigurer {

 public static Map<String, String> map = new HashMap<>();

 private static Log log = LogFactory.getLog(EncryptedPropertyConfigurer.class);

    @Override
    protected void convertProperties(Properties props) {
        Enumeration propertyNames = props.propertyNames();
        while (propertyNames.hasMoreElements()) {
             String propertyName = (String) propertyNames.nextElement();
             String propertyValue = props.getProperty(propertyName);
             String val=null;
             try {
                  val = EncryptionUtil.decrypt(propertyValue);
             } catch (Exception e) {
                  log.error("Error while decoding the value[" + propertyValue + "]", e);
             }
             if (!ObjectUtils.nullSafeEquals(propertyValue, val)) {
                  log.debug("Setting property: "+propertyName);
                  props.setProperty(propertyName, val); // FOR NEWER SPRING USE System.setProperty(propertyName, val);
                  map.put(propertyName, val);
             }
        }
    }

}