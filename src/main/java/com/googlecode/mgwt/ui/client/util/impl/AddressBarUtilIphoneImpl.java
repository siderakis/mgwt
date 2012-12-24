/*
 * Copyright 2012 Daniel Kurka
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
package com.googlecode.mgwt.ui.client.util.impl;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.util.AddressBarUtil;

public class AddressBarUtilIphoneImpl implements AddressBarUtil {

  private static final int IPHONE_FULLSCREEN_HEIGHT_OFFSET = 60;

  private static HandlerRegistration resizeHandler;

  @Override
  public void hideAddressBar() {
    if (MGWT.canFullSreen()) {
      ensureResizeListener();
      resize();
    }
  }

  @Override
  public void showAddressBar() {
    Window.scrollTo(0, 0);

  }

  private void resize() {
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      public void execute() {
        double height = Window.getClientHeight() + IPHONE_FULLSCREEN_HEIGHT_OFFSET;
        RootPanel.getBodyElement().getStyle().setHeight(height, Unit.PX);
        RootPanel.getBodyElement().getStyle().setProperty("minHeight", height, Unit.PX);
        Window.scrollTo(0, 0);
      }
    });
  }

  private native void setupPreventScrolling(Element el)/*-{
		var func = function(event) {
			event.preventDefault();
			return false;
		};
		el.ontouchmove = func;
  }-*/;

  private native void setupScrollOnTouch(Element el)/*-{
		var func = function(event) {
			$wnd.scroll(0, 0);
			return true;
		};
		el.ontouchstart = func;
  }-*/;

  protected void ensureResizeListener() {
    if (resizeHandler != null)
      return;

    setupPreventScrolling(Document.get().getBody());
    setupScrollOnTouch(Document.get().getBody());

    resizeHandler = Window.addResizeHandler(new ResizeHandler() {

      @Override
      public void onResize(ResizeEvent event) {
        resize();
      }

    });

  }

}
