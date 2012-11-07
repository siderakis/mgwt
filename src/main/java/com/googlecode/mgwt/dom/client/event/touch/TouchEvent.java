/*
 * Copyright 2010 Daniel Kurka
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.googlecode.mgwt.dom.client.event.touch;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.shared.EventHandler;
import com.googlecode.mgwt.collection.client.JsLightArray;
import com.googlecode.mgwt.collection.shared.LightArray;

/**
 * BaseClass for all TouchEvents
 * 
 * @author Daniel Kurka
 * @param <H> the event handler to associate with this event
 * @version $Id: $
 */
public abstract class TouchEvent<H extends EventHandler> extends DomEvent<H> {

  /**
   * <p>
   * touches
   * </p>
   * 
   * @deprecated use {@link #getTouches()} this method will be removed in a future release
   * 
   * @return a {@link com.google.gwt.core.client.JsArray} object.
   */
  @Deprecated
  public JsArray<JsTouch> touches() {
    return touches(getNativeEvent());
  }

  public LightArray<Touch> getTouches() {
    return new JsLightArray<Touch>(getNativeEvent().getTouches());
  }

  /**
   * <p>
   * touches
   * </p>
   * 
   * @param nativeEvent a {@link com.google.gwt.dom.client.NativeEvent} object.
   * @return a {@link com.google.gwt.core.client.JsArray} object.
   */
  protected native JsArray<JsTouch> touches(NativeEvent nativeEvent) /*-{
		return nativeEvent.touches;
  }-*/;

  /**
   * get the changed touches
   * 
   * @deprecated use {@link #getChangedTouches()}
   * 
   *             this method will be removed in a future release
   * 
   * @return the array of changed touches
   */
  @Deprecated
  public JsArray<JsTouch> changedTouches() {
    return changedTouches(getNativeEvent());
  }

  public LightArray<Touch> getChangedTouches() {
    return new JsLightArray<Touch>(getNativeEvent().getChangedTouches());
  }

  /**
   * <p>
   * changedTouches
   * </p>
   * 
   * @param nativeEvent a {@link com.google.gwt.dom.client.NativeEvent} object.
   * @return a {@link com.google.gwt.core.client.JsArray} object.
   */
  protected native JsArray<JsTouch> changedTouches(NativeEvent nativeEvent) /*-{
		return nativeEvent.changedTouches;
  }-*/;

}
