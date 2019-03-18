package simplehttpproxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMethod;

public class ProxyFunctionUtils {
	private static Logger logger = Logger.getLogger(ProxyFunctionUtils.class);
	
	private ProxyFunctionUtils() {}
    	
	public static void inputToOutput(final InputStream input, final OutputStream output) throws IOException {
		byte[] buff = new byte[2048];
		int readLength = 0;

		try {		
			while ((readLength = input.read(buff)) != -1) {
				output.write(buff, 0, readLength);
			}
			output.flush();

		} finally {
			try {
				input.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
			
			try {
				output.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}

		}
	}
	
	public static HttpURLConnection getHttpURLConnection(final HttpServletRequest request, final RequestMethod method) throws IOException {
        URL urlToServer = null;
        HttpURLConnection urlConnection = null;
       
        String requestUrl = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        
        // set URL. for GET, Query string is part of URL
        requestUrl = requestUrl + (queryString == null? "" : "?" + queryString);

		urlToServer = new URL(requestUrl);

		logger.debug("dest-requestUrl-is: " + requestUrl);
		
		urlConnection = (HttpURLConnection) urlToServer.openConnection();

		urlConnection.setRequestMethod(method.name());
		
		// set request header
		Enumeration<String> headerNames = request.getHeaderNames();
		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				String name = headerNames.nextElement();
				String value = request.getHeader(name);
				logger.debug("header-name-value: " + name + " ===>>> " + value);
				urlConnection.setRequestProperty(name, value);
			}
			
		}
		
		logger.debug("proxy.url => " + urlConnection.getURL() + 
			         "; Method=" + urlConnection.getRequestMethod() + 
			         "; Input=" + urlConnection.getDoInput() + 
			         "; Output=" + urlConnection.getDoInput());
		
		return urlConnection;
	}
    
}
