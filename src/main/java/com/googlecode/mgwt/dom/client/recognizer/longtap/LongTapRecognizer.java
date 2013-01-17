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
package com.googlecode.mgwt.dom.client.recognizer.longtap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HasHandlers;
import com.googlecode.mgwt.collection.shared.CollectionFactory;
import com.googlecode.mgwt.collection.shared.LightArray;
import com.googlecode.mgwt.dom.client.event.touch.Touch;
import com.googlecode.mgwt.dom.client.event.touch.TouchCancelEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchMoveEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchUtil;
import com.googlecode.mgwt.dom.client.recognizer.EventPropagator;
import com.googlecode.mgwt.dom.client.recognizer.TimerExecturGwtTimerImpl;
import com.googlecode.mgwt.dom.client.recognizer.TimerExecutor;
import com.googlecode.mgwt.dom.client.recognizer.TimerExecutor.CodeToRun;

/**
 * This class can recognize long taps
 * 
 * @author Daniel Kurka
 * 
 */
public class LongTapRecognizer implements TouchHandler {

  public static final int DEFAULT_WAIT_TIME_IN_MS = 1500;
  public static final int DEFAULT_MAX_DISTANCE = 15;

  protected enum State {
    INVALID, READY, FINGERS_DOWN, FINGERS_UP, WAITING
  };

  protected State state;
  private final HasHandlers source;
  private final int numberOfFingers;
  private final int time;

  private LightArray<Touch> startPositions;
  private int touchCount;
  private final int distance;

  private TimerExecutor timerExecutor;

  private EventPropagator eventPropagator;

  private static EventPropagator DEFAULT_EVENT_PROPAGATOR;

  /**
   * Construct a LongTapRecognizer with that fires on one finger after 1.5s
   * 
   * @param source the source on which to fire events on
   */
  public LongTapRecognizer(HasHandlers source) {
    this(source, 1);
  }

  /**
   * 
   * Construct a LongTapRecognizer with that after 1.5s
   * 
   * @param source source the source on which to fire events on
   * @param numberOfFingers the number of fingers to detect
   */
  public LongTapRecognizer(HasHandlers source, int numberOfFingers) {
    this(source, numberOfFingers, DEFAULT_WAIT_TIME_IN_MS);
  }

  /**
   * Construct a LongTapRecognizer
   * 
   * @param source the source on which to fire events on
   * @param numberOfFingers the number of fingers that should be detected
   * @param time the time the fingers need to touch
   */
  public LongTapRecognizer(HasHandlers source, int numberOfFingers, int time) {
    this(source, numberOfFingers, time, DEFAULT_MAX_DISTANCE);
  }

  /**
   * Construct a LongTapRecognizer
   * 
   * @param source the source on which to fire events on
   * @param numberOfFingers the number of fingers that should be detected
   * @param time the time the fingers need to touch
   * @param maxDistance the maximum distance each finger is allowed to move
   */
  public LongTapRecognizer(HasHandlers source, int numberOfFingers, int time, int maxDistance) {

    if (source == null) {
      throw new IllegalArgumentException("source can not be null");
    }
    if (numberOfFingers < 1) {
      throw new IllegalArgumentException("numberOfFingers > 0");
    }

    if (time < 200) {
      throw new IllegalArgumentException("time > 200");
    }

    if (maxDistance < 0) {
      throw new IllegalArgumentException("maxDistance > 0");
    }

    this.source = source;
    this.numberOfFingers = numberOfFingers;
    this.time = time;
    this.distance = maxDistance;

    reset();
    startPositions = CollectionFactory.constructArray();

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.googlecode.mgwt.dom.client.event.touch.TouchStartHandler#onTouchStart(com.googlecode.mgwt
   * .dom.client.event.touch.TouchStartEvent)
   */
  @Override
  public void onTouchStart(TouchStartEvent event) {

    LightArray<Touch> touches = event.getTouches();
    touchCount++;

    switch (state) {
      case INVALID:
        break;
      case READY:
        startPositions.push(TouchUtil.cloneTouch(touches.get(touchCount - 1)));
        state = State.FINGERS_DOWN;
        break;
      case FINGERS_DOWN:
        startPositions.push(TouchUtil.cloneTouch(touches.get(touchCount - 1)));
        break;
      case FINGERS_UP:
      default:
        invalidate();
        break;
    }

    if (touchCount == numberOfFingers) {
      state = State.WAITING;
      getTimerExecutor().execute(new CodeToRun() {

        @Override
        public void onExecution() {
          if (state != State.WAITING) {
            // something else happened forget it
            return;
          }

          getEventPropagator().fireEvent(source, new LongTapEvent(source, numberOfFingers, time, startPositions));
          reset();

        }
      }, time);
    }

    if (touchCount > numberOfFingers) {
      invalidate();
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.googlecode.mgwt.dom.client.event.touch.TouchMoveHandler#onTouchMove(com.googlecode.mgwt
   * .dom.client.event.touch.TouchMoveEvent)
   */
  @Override
  public void onTouchMove(TouchMoveEvent event) {
    switch (state) {
      case WAITING:
      case FINGERS_DOWN:
      case FINGERS_UP:
        // compare positions
        LightArray<Touch> currentTouches = event.getTouches();
        for (int i = 0; i < currentTouches.length(); i++) {
          Touch currentTouch = currentTouches.get(i);
          for (int j = 0; j < startPositions.length(); j++) {
            Touch startTouch = startPositions.get(j);
            if (currentTouch.getIdentifier() == startTouch.getIdentifier()) {
              if (Math.abs(currentTouch.getPageX() - startTouch.getPageX()) > distance || Math.abs(currentTouch.getPageY() - startTouch.getPageY()) > distance) {
                invalidate();
                break;
              }
            }
            if (state == State.INVALID) {
              break;
            }
          }
        }

        break;

      default:
        state=State.INVALID;
        break;
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler#onTouchEnd(com.googlecode.mgwt.dom
   * .client.event.touch.TouchEndEvent)
   */
  @Override
  public void onTouchEnd(TouchEndEvent event) {
    int currentTouches = event.getTouches().length();
    switch (state) {
      case WAITING:
        invalidate();
        break;

      case FINGERS_DOWN:
        state = State.FINGERS_UP;
        break;
      case FINGERS_UP:
        // are we ready?
        if (currentTouches == 0 && touchCount == numberOfFingers) {
          // fire and reset

          reset();
        }
        break;

      case INVALID:
      default:
        if (currentTouches == 0)
          reset();
        break;
    }

  }

  public void invalidate() {
    state = State.INVALID;
  }
  /*
   * (non-Javadoc)
   * 
   * @see
   * com.googlecode.mgwt.dom.client.event.touch.TouchCancelHandler#onTouchCanceled(com.googlecode
   * .mgwt.dom.client.event.touch.TouchCancelEvent)
   */
  @Override
  public void onTouchCanceled(TouchCancelEvent event) {
    invalidate();
    int currentTouches = event.getTouches().length();
    if (currentTouches == 0)
      reset();
  }

  /**
   * set the timer executor - used for testing...
   * 
   * @param timerExecutor the timer executor to use
   */
  protected void setTimerExecutor(TimerExecutor timerExecutor) {
    this.timerExecutor = timerExecutor;
  }

  /**
   * set the event propagator that is used by the recognizer - used for testing
   * 
   * @param eventPropagator
   */
  protected void setEventPropagator(EventPropagator eventPropagator) {
    this.eventPropagator = eventPropagator;

  }

  protected TimerExecutor getTimerExecutor() {
    if (timerExecutor == null) {
      timerExecutor = new TimerExecturGwtTimerImpl();
    }
    return timerExecutor;
  }

  public void reset() {
    state = State.READY;
    touchCount = 0;
  }

  protected EventPropagator getEventPropagator() {
    if (eventPropagator == null) {
      if (DEFAULT_EVENT_PROPAGATOR == null) {
        DEFAULT_EVENT_PROPAGATOR = GWT.create(EventPropagator.class);
      }
      eventPropagator = DEFAULT_EVENT_PROPAGATOR;
    }
    return eventPropagator;
  }

}
