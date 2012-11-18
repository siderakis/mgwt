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
package com.googlecode.mgwt.ui.client.widget;

import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWordWrap;
import com.googlecode.mgwt.dom.client.event.tap.Tap;
import com.googlecode.mgwt.dom.client.event.touch.Touch;
import com.googlecode.mgwt.dom.client.event.touch.TouchCancelEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchMoveEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.theme.base.InputCss;
import com.googlecode.mgwt.ui.client.widget.touch.TouchWidget;

/**
 * A touch enabled radio button implementation
 * 
 * @author Daniel Kurka
 * @version $Id: $
 */
public class MRadioButton extends TouchWidget implements HasText, HasEnabled, HasValueChangeHandlers<Boolean>, HasName, HasValue<Boolean>, HasWordWrap, IsEditor<LeafValueEditor<Boolean>> {

  private InputElement inputRadio;
  private LabelElement labelElement;
  private LeafValueEditor<Boolean> editor;
  private final InputCss css;

  /**
   * Construct a radio button
   * 
   * @param name the name of the group
   */
  @UiConstructor
  public MRadioButton(String name) {
    this(MGWTStyle.getTheme().getMGWTClientBundle().getInputCss(), name);
  }

  /**
   * Construct a radio button
   * 
   * @param css the css to use
   * @param name the group name to use
   */
  public MRadioButton(InputCss css, String name) {
    this.css = css;
    css.ensureInjected();
    setElement(DOM.createSpan());

    sinkEvents(Event.ONCHANGE);

    labelElement = LabelElement.as(DOM.createLabel());
    getElement().appendChild(labelElement);
    inputRadio = InputElement.as(DOM.createInputRadio(name));
    getElement().appendChild(inputRadio);

    addStyleName(css.radioButton());

    addTouchHandler(new TouchHandler() {

      private boolean ignore;
      private boolean labelOrContainer;
      private int start_x;
      private int start_y;

      private int last_x;
      private int last_y;

      @Override
      public void onTouchCanceled(TouchCancelEvent event) {
        if (ignore)
          return;

      }

      @Override
      public void onTouchEnd(TouchEndEvent event) {
        if (!isEnabled())
          return;

        if (ignore)
          return;

        if (Math.abs(last_x - start_x) < Tap.RADIUS && Math.abs(last_y - start_y) < Tap.RADIUS) {
          if (labelOrContainer) {
            inputRadio.setChecked(true);
            ValueChangeEvent.fire(MRadioButton.this, true);
          }
        }

      }

      @Override
      public void onTouchMove(TouchMoveEvent event) {
        if (!isEnabled())
          return;

        if (ignore)
          return;
        Touch touch = event.getTouches().get(0);
        last_x = touch.getPageX();
        last_y = touch.getPageY();

      }

      @Override
      public void onTouchStart(TouchStartEvent event) {
        if (!isEnabled())
          return;
        ignore = inputRadio.isChecked();

        if (ignore)
          return;

        Touch touch = event.getTouches().get(0);
        start_x = touch.getPageX();
        start_y = touch.getPageY();
        last_x = start_x;
        last_y = start_y;

        EventTarget eventTarget = event.getNativeEvent().getEventTarget();
        labelOrContainer = true;
        if (com.google.gwt.dom.client.Element.is(eventTarget)) {
          com.google.gwt.dom.client.Element el = com.google.gwt.dom.client.Element.as(eventTarget);

          if (inputRadio.isOrHasChild(el)) {
            labelOrContainer = false;
          }
        }

      }
    });

    addHandler(new ChangeHandler() {

      @Override
      public void onChange(ChangeEvent event) {
        ValueChangeEvent.fire(MRadioButton.this, true);

      }
    }, ChangeEvent.getType());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.HasText#getText()
   */
  /** {@inheritDoc} */
  @Override
  public String getText() {
    return labelElement.getInnerText();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.HasText#setText(java.lang.String)
   */
  /** {@inheritDoc} */
  @Override
  public void setText(String text) {
    labelElement.setInnerText(text);

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google
   * .gwt.event.logical.shared.ValueChangeHandler)
   */
  /** {@inheritDoc} */
  @Override
  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
    return addHandler(handler, ValueChangeEvent.getType());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.editor.client.IsEditor#asEditor()
   */
  /** {@inheritDoc} */
  @Override
  public LeafValueEditor<Boolean> asEditor() {
    if (editor == null) {
      editor = TakesValueEditor.of(this);
    }
    return editor;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.HasWordWrap#getWordWrap()
   */
  /** {@inheritDoc} */
  @Override
  public boolean getWordWrap() {
    return !getElement().getStyle().getProperty("whiteSpace").equals("nowrap");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.HasWordWrap#setWordWrap(boolean)
   */
  /** {@inheritDoc} */
  @Override
  public void setWordWrap(boolean wrap) {
    getElement().getStyle().setProperty("whiteSpace", wrap ? "normal" : "nowrap");

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.HasValue#getValue()
   */
  /** {@inheritDoc} */
  @Override
  public Boolean getValue() {
    if (isAttached()) {
      return inputRadio.isChecked();
    } else {
      return inputRadio.isDefaultChecked();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object)
   */
  /** {@inheritDoc} */
  @Override
  public void setValue(Boolean value) {
    setValue(value, false);

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object, boolean)
   */
  /** {@inheritDoc} */
  @Override
  public void setValue(Boolean value, boolean fireEvents) {
    if (value == null) {
      value = Boolean.FALSE;
    }

    Boolean oldValue = getValue();
    inputRadio.setChecked(value);
    inputRadio.setDefaultChecked(value);
    if (value.equals(oldValue)) {
      return;
    }
    if (fireEvents) {
      ValueChangeEvent.fire(this, value);
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.HasName#setName(java.lang.String)
   */
  /** {@inheritDoc} */
  @Override
  public void setName(String name) {
    replaceInputElement(DOM.createInputRadio(name));

  }

  private void replaceInputElement(com.google.gwt.user.client.Element elem) {
    InputElement newInputElem = InputElement.as(elem);
    // Collect information we need to set

    boolean checked = getValue();
    boolean enabled = isEnabled();
    String formValue = getFormValue();
    String uid = inputRadio.getId();
    String accessKey = inputRadio.getAccessKey();
    int sunkEvents = Event.getEventsSunk(inputRadio);

    // Clear out the old input element
    setEventListener(asOld(inputRadio), null);

    getElement().replaceChild(newInputElem, inputRadio);

    // Sink events on the new element
    Event.sinkEvents(elem, Event.getEventsSunk(inputRadio));
    Event.sinkEvents(inputRadio, 0);
    inputRadio = newInputElem;

    // Setup the new element
    Event.sinkEvents(inputRadio, sunkEvents);
    inputRadio.setId(uid);
    if (!"".equals(accessKey)) {
      inputRadio.setAccessKey(accessKey);
    }

    setValue(checked);
    setEnabled(enabled);
    setFormValue(formValue);

    // Set the event listener
    if (isAttached()) {
      setEventListener(asOld(inputRadio), this);
    }

  }

  /**
   * set the formvalue of this radio button
   * 
   * @param formValue the formvalue that would be sent to a server
   */
  public void setFormValue(String formValue) {
    inputRadio.setAttribute("value", formValue);

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.HasName#getName()
   */
  /** {@inheritDoc} */
  @Override
  public String getName() {
    return inputRadio.getName();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.HasEnabled#isEnabled()
   */
  /** {@inheritDoc} */
  @Override
  public boolean isEnabled() {
    return !inputRadio.isDisabled();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.HasEnabled#setEnabled(boolean)
   */
  /** {@inheritDoc} */
  @Override
  public void setEnabled(boolean enabled) {
    inputRadio.setDisabled(!enabled);
    if (enabled) {
      removeStyleDependentName(css.disabled());
    } else {
      addStyleDependentName(css.disabled());
    }

  }

  /**
   * get the form value of the input element
   * 
   * @return the form value
   */
  public String getFormValue() {
    return inputRadio.getValue();
  }

  private Element asOld(com.google.gwt.dom.client.Element elem) {
    Element oldSchool = elem.cast();
    return oldSchool;
  }

  private void setEventListener(com.google.gwt.dom.client.Element e, EventListener listener) {
    DOM.setEventListener(asOld(e), listener);
  }

}
