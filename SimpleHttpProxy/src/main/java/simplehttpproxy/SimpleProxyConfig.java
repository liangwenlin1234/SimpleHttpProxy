package simplehttpproxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleProxyConfig {
	
    @Bean
    public ProxyFunctionIntf proxyFunction() {
    	return new ProxyFunction();
    }
    
}
