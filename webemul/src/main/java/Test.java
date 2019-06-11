import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Test {

	public static void main(String[] args) throws Exception{

		URL url;
		URLConnection uc;
		StringBuilder parsedContentFromUrl = new StringBuilder();
		String urlString="https://cdn.thegamesdb.net/images/original/boxart/front/140-1.jpg";
		System.out.println("Getting content for URl : " + urlString);
		url = new URL(urlString);
		uc = url.openConnection();
		uc.addRequestProperty("User-Agent", 
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		uc.connect();
		uc.getInputStream();
		BufferedInputStream in = new BufferedInputStream(uc.getInputStream());
		int ch;
		while ((ch = in.read()) != -1) {
		    parsedContentFromUrl.append((char) ch);
		}
		System.out.println(parsedContentFromUrl);
		
		
	}

}
