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
package com.googlecode.mgwt.dom.client.event.animation;

import com.google.gwt.event.dom.client.DomEvent;
import com.googlecode.mgwt.dom.client.event.EventTypesHolder;

/**
 * 
 * Represent a native animation end event
 * 
 * @author Daniel Kurka
 * @version $Id: $
 */
public class AnimationEndEvent extends DomEvent<AnimationEndHandler> {

  /**
   * Event type for animation end events.
   */
  @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "MS_PKGPROTECT", justification = "we need this for testing")
  protected static Type<AnimationEndHandler> TYPE;

  /**
   * <p>
   * getType
   * </p>
   * 
   * @return a Type object.
   */
  public static Type<AnimationEndHandler> getType() {
    if (TYPE == null) {
      TYPE = new Type<AnimationEndHandler>(EventTypesHolder.EVENT_TYPES.getAnimationEnd(), new AnimationEndEvent());
    }
    return TYPE;
  }

  /**
   * {@inheritDoc}
   * 
   * Gets the event type associated with animation end events.
   */
  @Override
  public com.google.gwt.event.dom.client.DomEvent.Type<AnimationEndHandler> getAssociatedType() {
    if (TYPE == null) {
      TYPE = new Type<AnimationEndHandler>(EventTypesHolder.EVENT_TYPES.getAnimationEnd(), new AnimationEndEvent());
    }
    return TYPE;
  }

  /**
   * Protected constructor, use
   * {@link DomEvent#fireNativeEvent(com.google.gwt.dom.client.NativeEvent, com.google.gwt.event.shared.HasHandlers)}
   * to fire animation end events.
   */
  protected AnimationEndEvent() {

  }

  /** {@inheritDoc} */
  @Override
  protected void dispatch(AnimationEndHandler handler) {
    handler.onAnimationEnd(this);

  }
}
