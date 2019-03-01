package simplehttpproxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMethod;

public class ProxyGetFunction implements ProxyFunctionIntf{

	@Override
	public void sendAndGetResponse(HttpServletRequest request, HttpServletResponse response, RequestMethod method) throws IOException {
        HttpURLConnection urlConnection = ProxyFunctionUtils.getHttpURLConnection(request, method);

		final OutputStream toClient = response.getOutputStream();
		
		urlConnection.connect();
		
		final InputStream fromDestination = urlConnection.getInputStream();
		ProxyFunctionUtils.inputToOutput(fromDestination, toClient);
		
	}

}
