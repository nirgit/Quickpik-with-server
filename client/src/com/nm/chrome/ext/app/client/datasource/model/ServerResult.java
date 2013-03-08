package com.nm.chrome.ext.app.client.datasource.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class ServerResult extends JavaScriptObject {

   protected ServerResult() {
   }

   public final native JsArray<ServerImageItem> getItems() /*-{
     return this.items ;
   }-*/;
}
