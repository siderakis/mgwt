package com.googlecode.mgwt.ui.client.theme.platform.list.widgetlist;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;

import com.googlecode.mgwt.ui.client.widget.list.widgetlist.WidgetListAbstractAppearance;

public class WidgetListIOSAppearance extends WidgetListAbstractAppearance {

  static {
    Resources.INSTANCE.css().ensureInjected();
  }

  interface Resources extends ClientBundle {

    Resources INSTANCE = GWT.create(Resources.class);

    @Source({"widgetlist-base.css", "widgetlist-ios.css"})
    WidgetListCss css();
  }

  @Override
  public WidgetListCss css() {
    return Resources.INSTANCE.css();
  }
}
