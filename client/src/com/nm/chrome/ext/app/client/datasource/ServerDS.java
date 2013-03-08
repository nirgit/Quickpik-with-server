package com.nm.chrome.ext.app.client.datasource;

import java.util.LinkedList;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.nm.chrome.ext.app.client.datasource.model.ServerImageItem;
import com.nm.chrome.ext.app.client.datasource.model.ServerResult;
import com.nm.chrome.ext.app.client.model.FilterLevel;
import com.nm.chrome.ext.app.client.model.Photo;
import com.nm.chrome.ext.app.client.model.PhotosSearchResult;

/**
 * Defines the QuickPik Server as a (images) data source.
 * @author Nir Moav
 */
public class ServerDS implements IImagesDataSource {

	private final static String QUICKPIK_SERVER_URL = "http://localhost:8080/qpserver/rest/quickpik/searchPhotos?searchExp=" ;

	/**
	 * @see com.nm.chrome.ext.app.client.datasource.IImagesDataSource#getImages(java.lang.String, com.nm.chrome.ext.app.client.model.FilterLevel, com.google.gwt.core.client.Callback, int)
	 */
	@Override
	public void getImages(String searchExpression, FilterLevel filter, Callback<PhotosSearchResult, Void> callback, int fromPage) {
		getImages(searchExpression, filter, callback) ;
	}
	
	/**
	 * @see com.nm.chrome.ext.app.client.datasource.IImagesDataSource#getImages(java.lang.String, com.nm.chrome.ext.app.client.model.FilterLevel, com.google.gwt.core.client.Callback)
	 */
	@Override
	public void getImages(final String searchExpression, final FilterLevel filter, final Callback<PhotosSearchResult, Void> callback) {
		String url = QUICKPIK_SERVER_URL + searchExpression ;
		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.requestObject(url, new AsyncCallback<ServerResult>() {
			public void onFailure(Throwable throwable) {
				// do some error handling..
			}

			public void onSuccess(ServerResult result) {
				handleLoadedImagesResult(searchExpression, filter, result, callback) ;
			}
		});
	}
	
	private void handleLoadedImagesResult(String searchExpression, FilterLevel filter, ServerResult result, 
											Callback<PhotosSearchResult, Void> callback) {
		JsArray<ServerImageItem> imageItems = result.getItems();
		LinkedList<Photo> photos = collectImages(imageItems);
		callback.onSuccess(new PhotosSearchResult(searchExpression, filter, photos, 0, false)) ;
	}

	private LinkedList<Photo> collectImages(JsArray<ServerImageItem> imageItems) {
		LinkedList<Photo> photos = new LinkedList<Photo>();
		for (int i = 0; i < imageItems.length(); i++) {
			ServerImageItem imageItem = imageItems.get(i);
			Photo p = new Photo(i+"", imageItem.getImageURL(), imageItem.getImageURL()) ;
			photos.add(p) ;
		}
		return photos;
	}
}
