package com.googlecode.mgwt.ui.client.widget.header;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;

public abstract class HeaderAbstractAppearance implements HeaderAppearance {

  @UiTemplate("HeaderButtonAbstractAppearance.ui.xml")
  interface BinderButton extends UiBinder<Element, HeaderButton> {
  }

  private static final BinderButton UI_BINDER_BUTTON = GWT.create(BinderButton.class);

  @UiTemplate("HeaderPanelAbstractAppearance.ui.xml")
  interface BinderPanel extends UiBinder<Widget, HeaderPanel> {
  }

  private static final BinderPanel UI_BINDER_PANEL = GWT.create(BinderPanel.class);

  @UiTemplate("HeaderTitleAbstractAppearance.ui.xml")
  interface BinderTitle extends UiBinder<Element, HeaderTitle> {
  }

  private static final BinderTitle UI_BINDER_TITLE = GWT.create(BinderTitle.class);

  @Override
  public UiBinder<? extends Element, HeaderButton> uiBinder() {
    return UI_BINDER_BUTTON;
  }

  @Override
  public UiBinder<Widget, HeaderPanel> panelBinder() {
    return UI_BINDER_PANEL;
  }

  @Override
  public UiBinder<? extends Element, HeaderTitle> uiBinderTitle() {
    return UI_BINDER_TITLE;
  }
}
