package com.nm.chrome.ext.app.client.datasource.model;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Describes the image items we receive from the server.
 * @author Nir Moav
 */
public class ServerImageItem extends JavaScriptObject {

	protected ServerImageItem() {
	}
	
	public final native String getImageURL() /*-{
    	return this.media.m ;
  	}-*/;

}
