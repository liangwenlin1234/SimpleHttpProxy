package simplehttpproxy;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import simplehttpproxy.ProxyRestController;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import org.apache.log4j.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SimpleProxyTest {
	private static Logger logger = Logger.getLogger(SimpleProxyTest.class);
	
	protected MockMvc mvc;
	
	@Autowired
    private ProxyRestController proxyRestController;

	@Autowired
    WebApplicationContext webApplicationContext;
	
	@Before
	public void setUp() {
	    mvc = MockMvcBuilders.standaloneSetup(proxyRestController).build();
	}
	
    @Test
    //@Ignore
    public void testSimpleProxyGet() throws Exception {    	
    	String url = "http://httpbin.org/get";
        String result = mvc.perform(get(url))
                           .andDo(print())
                           .andExpect(handler().handlerType(ProxyRestController.class))
                           .andExpect(handler().methodName("proxyGet"))
                           .andExpect(status().isOk())
                           .andReturn()
                           .getResponse()
                           .getContentAsString();
        
        logger.debug("result => " + result);

    }
    
    @Test
    public void testSimpleProxyPost() throws Exception {    	
    	String url = "http://httpbin.org/post";
        String result = mvc.perform(post(url))
                           .andDo(print())
                           .andExpect(handler().handlerType(ProxyRestController.class))
                           .andExpect(handler().methodName("proxyPost"))
                           .andExpect(status().isOk())
                           .andReturn()
                           .getResponse()
                           .getContentAsString();
        
        logger.debug("result => " + result);

    }    
    
}
