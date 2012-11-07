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
package com.googlecode.mgwt.dom.client.event.mouse;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.googlecode.mgwt.collection.client.JsLightArray;
import com.googlecode.mgwt.collection.shared.LightArray;
import com.googlecode.mgwt.dom.client.event.touch.JsTouch;
import com.googlecode.mgwt.dom.client.event.touch.Touch;
import com.googlecode.mgwt.dom.client.event.touch.TouchMoveEvent;

/**
 * A simulated TouchMoveEvent is really a mouse move event. This is used mostly in dev mode and for
 * blackberry devices to handle them equally to real touch devices
 * 
 * @author Daniel Kurka
 * @version $Id: $
 */
public class SimulatedTouchMoveEvent extends TouchMoveEvent {

  private int x;
  private int y;

  private int x_start;
  private int y_start;
  private boolean multiTouch;

  /**
   * <p>
   * Constructor for SimulatedTouchMoveEvent.
   * </p>
   * 
   * @param event a {@link com.google.gwt.event.dom.client.MouseMoveEvent} object.
   */
  public SimulatedTouchMoveEvent(MouseMoveEvent event) {
    x = event.getClientX();
    y = event.getClientY();
    multiTouch = false;

    if (event.isAltKeyDown() && MultiTouchMouseEmulator.isHasValues()) {
      multiTouch = true;
      int[] start = MultiTouchMouseEmulator.getTouchStart();
      x_start = start[0];
      y_start = start[1];
    }

    setNativeEvent(event.getNativeEvent());
    setSource(event.getSource());
  }

  @Override
  public LightArray<Touch> getTouches() {
    return new JsLightArray<Touch>(touches(getNativeEvent()));
  }

  @Override
  public LightArray<Touch> getChangedTouches() {
    return new JsLightArray<Touch>(changedTouches(getNativeEvent()));
  }

  /** {@inheritDoc} */
  @Override
  protected native JsArray<JsTouch> touches(NativeEvent nativeEvent) /*-{
		var touch = {};

		touch.pageX = this.@com.googlecode.mgwt.dom.client.event.mouse.SimulatedTouchMoveEvent::x;
		touch.pageY = this.@com.googlecode.mgwt.dom.client.event.mouse.SimulatedTouchMoveEvent::y;

		var toucharray = [];

		if (this.@com.googlecode.mgwt.dom.client.event.mouse.SimulatedTouchMoveEvent::multiTouch) {
			var touch_start = {};
			touch_start.pageX = this.@com.googlecode.mgwt.dom.client.event.mouse.SimulatedTouchMoveEvent::x_start;
			touch_start.pageY = this.@com.googlecode.mgwt.dom.client.event.mouse.SimulatedTouchMoveEvent::y_start;
			toucharray.push(touch_start);
		}

		toucharray.push(touch);

		return toucharray;
  }-*/;

  /** {@inheritDoc} */
  @Override
  protected native JsArray<JsTouch> changedTouches(NativeEvent nativeEvent) /*-{
		var touch = {};

		touch.pageX = this.@com.googlecode.mgwt.dom.client.event.mouse.SimulatedTouchMoveEvent::x;
		touch.pageY = this.@com.googlecode.mgwt.dom.client.event.mouse.SimulatedTouchMoveEvent::y;

		var toucharray = [];

		if (this.@com.googlecode.mgwt.dom.client.event.mouse.SimulatedTouchMoveEvent::multiTouch) {
			var touch_start = {};
			touch_start.pageX = this.@com.googlecode.mgwt.dom.client.event.mouse.SimulatedTouchMoveEvent::x_start;
			touch_start.pageY = this.@com.googlecode.mgwt.dom.client.event.mouse.SimulatedTouchMoveEvent::y_start;
			toucharray.push(touch_start);
		}

		toucharray.push(touch);

		return toucharray;
  }-*/;
}
