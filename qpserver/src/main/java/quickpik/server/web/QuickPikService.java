package quickpik.server.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.json.JSONWithPadding;

@Path("/quickpik")
public class QuickPikService {

	private final static int BUFFER_SIZE_IN_BYTES = 1024;
	private final static String FLICKR_API = "http://api.flickr.com/services/feeds/photos_public.gne?format=json&tagmode=all&tags=" ;
	
	@GET
	@Path("searchPhotos")
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON})
	public JSONWithPadding getPhotos(@QueryParam("searchExp") String searchExp,
											@QueryParam("callback") String callback) {
		// "log" the call 
		System.out.println("[" + new Date() + "] searching for: " + searchExp) ;
		URL flickrUrl = getSearchURL(searchExp) ;
		JSONObject data = tryToGetSearchResult(flickrUrl);
		return new JSONWithPadding(data, callback);
	}

	private URL getSearchURL(String searchExp) {
		String composedURL = FLICKR_API + searchExp;
		try {
			return new URL(composedURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException("URL composition failed. Please check your URL: " + composedURL, e) ;
		}
	}

	private JSONObject tryToGetSearchResult(URL flickrUrl) {
		try {
			return getSearchResult(flickrUrl) ;
		} catch (IOException | JSONException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed searching.", e) ;
		}
	}

	private JSONObject getSearchResult(URL flickrUrl) throws IOException, JSONException {
		InputStream is = flickrUrl.openStream() ;
		String result = readDataFromStream(is);
		// Flickr specific string prefix for the JSON feed.
		if(result.indexOf("jsonFlickrFeed(") >= 0) {
			result = result.substring("jsonFlickrFeed(".length(), result.length()-1) ;
			return new JSONObject(result) ;
		} else {
			return new JSONObject("{}") ;
		}
	}

	private String readDataFromStream(InputStream is) throws IOException {
		StringBuilder data = new StringBuilder() ;
		byte[] buffer = new byte[BUFFER_SIZE_IN_BYTES] ;
		int bytesRead = is.read(buffer) ;
		while(bytesRead != -1) {
			data.append(new String(buffer, 0, bytesRead)) ;
			bytesRead = is.read(buffer) ;
		}
		is.close() ;
		return data.toString() ;
	}
}
