package simplehttpproxy;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMethod;

public class ProxyFunction implements ProxyFunctionIntf{

	private final ProxyFunctionIntf proxyGetFunction = new ProxyGetFunction();
	
	private final ProxyFunctionIntf proxyPostFunction = new ProxyPostFunction();
	
	@Override
	public void sendAndGetResponse(HttpServletRequest request, HttpServletResponse response, RequestMethod method) throws IOException {
		switch (method) {
		case POST:
			proxyPostFunction.sendAndGetResponse(request, response, method);
			break;
		case GET:
			proxyGetFunction.sendAndGetResponse(request, response, method);
			break;
		case DELETE:
			break;
		case HEAD:
			break;
		case OPTIONS:
			break;
		case PATCH:
			break;
		case PUT:
			break;
		case TRACE:
			break;
		default:
			break;
		}
		
	}

}
