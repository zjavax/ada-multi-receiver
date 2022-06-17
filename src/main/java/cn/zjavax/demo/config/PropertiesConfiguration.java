package cn.zjavax.demo.config;

import com.bloxbean.cardano.client.backend.api.BackendService;
import com.bloxbean.cardano.client.backend.blockfrost.common.Constants;
import com.bloxbean.cardano.client.backend.blockfrost.service.BFBackendService;
import io.blockfrost.sdk.impl.AccountServiceImpl;
import io.blockfrost.sdk.impl.AssetServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @author ausstaker (https://github.com/Ausstaker)
 * @since Nov 2021
 */
@Configuration
public class PropertiesConfiguration implements ApplicationContextAware {
//
//    @Value("${blockfrost.cardano.testnet.project.id}")
//    private String blockfrostCardanoTestnetProjectId;

    @Bean
    protected EncryptedPropertyConfigurer encryptedPropertyPlaceholder() {
        EncryptedPropertyConfigurer dpc = new EncryptedPropertyConfigurer();
        dpc.setLocations(new Resource[] {new ClassPathResource("encrypted.properties")});
        return dpc;
    }

    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

//    @Bean("accountServiceImpl")
//    public AccountServiceImpl getAccountServiceImpl(){
//        return new AccountServiceImpl(Constants.BLOCKFROST_TESTNET_URL, blockfrostCardanoTestnetProjectId);
//    }
//
//    @Bean("backendService")
//    public BackendService getBFBackendService(){
//        return new BFBackendService(Constants.BLOCKFROST_TESTNET_URL, blockfrostCardanoTestnetProjectId);
//    }
//
//    @Bean("assetServiceImpl")
//    public AssetServiceImpl getAssetServiceImpl(){
//        return new AssetServiceImpl(Constants.BLOCKFROST_TESTNET_URL, blockfrostCardanoTestnetProjectId);
//    }

}
