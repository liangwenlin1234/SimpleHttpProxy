package simplehttpproxy;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/")
public class ProxyRestController {
	private static final Logger logger = Logger.getLogger(ProxyRestController.class);
	
	private final ProxyFunctionIntf proxyFunction;
	
	@Autowired
	public ProxyRestController(ProxyFunctionIntf proxyFunction) {
		this.proxyFunction = proxyFunction;
	}

	@RequestMapping(value="/**", method = RequestMethod.GET, produces=MediaType.ALL_VALUE)
	@ResponseBody
    public void proxyGet(HttpServletRequest request, HttpServletResponse response) { 
	    try {
	    	proxyFunction.sendAndGetResponse(request, response, RequestMethod.GET);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
    }
	
	@RequestMapping(value="/**", method = RequestMethod.POST, produces=MediaType.ALL_VALUE)
	@ResponseBody
    public void proxyPost(HttpServletRequest request, HttpServletResponse response) {
	    try {
	    	proxyFunction.sendAndGetResponse(request, response, RequestMethod.POST);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
    }
	
}
