package fr.provenzano.webemul.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.config.Lookup;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.impl.auth.BasicSchemeFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.Unirest;

import fr.provenzano.webemul.service.TechParameterService;
import fr.provenzano.webemul.service.dto.ParameterDTO;

@Service
@Scope("singleton")
public class ProxyManager {

	/**
	 * The User Agent
	 */
	private static final String AGENT = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";

	@Autowired
	private TechParameterService parameterService;
	
	/**
	 * Used to store the proxy settings
	 */
	private Proxy proxy;

	@PostConstruct
	public void initConnection() {
		ParameterDTO parameterDTO = parameterService.findByName("proxy.enabled");
		boolean isProxyEnabled = parameterDTO!=null && StringUtils.isNotBlank(parameterDTO.getValue()) 
				&& TechParameterService.PARAMETER_ON.equals(parameterDTO.getValue());
		if (isProxyEnabled) {
			String host = parameterService.findByName("proxy.host").getValue();
			String port = parameterService.findByName("proxy.port").getValue();
			String username = parameterService.findByName("proxy.user").getValue();
			String password = parameterService.findByName("proxy.password").getValue();
			if (StringUtils.isNotBlank(host) && StringUtils.isNumeric(port)) {
				if (StringUtils.isNotBlank(username)) {
					setProxy(host, Integer.parseInt(port), username, password);
				} else {
					setProxy(host, Integer.parseInt(port));
				}
				initUnirest(true, host, Integer.parseInt(port), username, password);
			}
		} else {
			proxy = null;
			initUnirest(false, null, null, null, null);
		}
	}
	
	private void initUnirest(Boolean isProxyEnabled, String ip, Integer port, String username, String password) {
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		if (isProxyEnabled && StringUtils.isNotBlank(username)) {
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
			clientBuilder.setDefaultCredentialsProvider(credsProvider);
		}
		clientBuilder.useSystemProperties();
		if (isProxyEnabled) {
			clientBuilder.setProxy(new HttpHost(ip, port));
			clientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());
		}		
		Lookup<AuthSchemeProvider> authProviders = RegistryBuilder.<AuthSchemeProvider> create()
				.register(AuthSchemes.BASIC, new BasicSchemeFactory()).build();
		clientBuilder.setDefaultAuthSchemeRegistry(authProviders);
		Unirest.setHttpClient(clientBuilder.build());
	}
	
	/**
	 * 
	 * Method used to add proxy settings
	 *
	 * @param ip
	 *            the proxy IP
	 * @param port
	 *            the proxy port
	 */
	private void setProxy(String ip, int port) {
		this.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
	}

	/**
	 * 
	 * Method used to add proxy settings with authentication
	 *
	 * @param ip
	 * @param port
	 * @param username
	 * @param password
	 */
	private void setProxy(String ip, int port, String username, String password) {
		this.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
		Authenticator authenticator = new Authenticator() {

			public PasswordAuthentication getPasswordAuthentication() {
				return (new PasswordAuthentication(username, password.toCharArray()));
			}
		};
		Authenticator.setDefault(authenticator);

	}

	/**
	 * The method will return the content in a {@link InputStream} object
	 * 
	 * @param domain
	 *            The Domain name
	 * 
	 * @return the content as {@link InputStream} object
	 * @throws Exception
	 */
	private InputStream getContent(String domain) throws Exception {

		URL url = new URL(domain);
		URLConnection connection = null;
		if (this.proxy != null)
			connection = url.openConnection(this.proxy);
		else
			connection = url.openConnection();

		connection.setRequestProperty("User-Agent", AGENT);
		return connection.getInputStream();
	}

	/**
	 * Method used to get URL content in {@link String} format
	 * 
	 * @param domain
	 *            the {@link String} the URL
	 * @return the {@link String} object returned
	 * @throws Exception
	 */
	public String getString(String domain) throws Exception {
		InputStream is = getContent(domain);
		StringBuilder sb = new StringBuilder();

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		try {
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			/** finally block to close the {@link BufferedReader} */
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	public byte[] getBytes(String domain) throws Exception {

		InputStream in = getContent(domain);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int n = 0;
		try {
			while (-1 != (n = in.read(buf))) {
				out.write(buf, 0, n);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return out.toByteArray();
	}

}
