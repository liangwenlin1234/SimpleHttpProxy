package simplehttpproxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMethod;

public class ProxyPostFunction implements ProxyFunctionIntf{
	private static Logger logger = Logger.getLogger(ProxyPostFunction.class);
	
	@Override
	public void sendAndGetResponse(HttpServletRequest request, HttpServletResponse response, RequestMethod method) throws IOException {
        final HttpURLConnection urlConnection = ProxyFunctionUtils.getHttpURLConnection(request, method);

		final OutputStream toClient = response.getOutputStream();
		
		urlConnection.setDoOutput(true);
		final OutputStream toDestination = urlConnection.getOutputStream();
		
		// send parameters for POST 
		try (OutputStreamWriter outputStreamToUrl = new OutputStreamWriter(toDestination)) {
			Enumeration<String> names = request.getParameterNames();
			StringBuilder parameters = new StringBuilder("");
			String result = "";
			if (names != null) {
				while (names.hasMoreElements()) {
					String name = names.nextElement();
					String value = request.getParameter(name);
					parameters.append(name + "=" + URLEncoder.encode(value, StandardCharsets.UTF_8.toString()) + "&");
				}
				
				if (!"".equals(parameters.toString())) {
					result = parameters.toString().substring(0, (parameters.length() - 1));
				}

			}
			logger.debug("post-parameters => " + result);
			
			outputStreamToUrl.write(result);
			outputStreamToUrl.flush();
		}

		final InputStream fromDestination = urlConnection.getInputStream();
		ProxyFunctionUtils.inputToOutput(fromDestination, toClient);
		
	}

}
