package simplehttpproxy;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMethod;

public interface ProxyFunctionIntf {
	void sendAndGetResponse(final HttpServletRequest request, final HttpServletResponse response, final RequestMethod method) throws IOException;
}
