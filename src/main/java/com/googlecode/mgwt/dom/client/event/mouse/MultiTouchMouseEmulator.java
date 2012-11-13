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
package com.googlecode.mgwt.dom.client.event.mouse;

public class MultiTouchMouseEmulator {

  private static int[] coordinates;
  private static boolean hasValues;

  public static void onTouchStart(int x, int y) {
    coordinates = new int[] {x, y};
    hasValues = true;
  }

  @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "MS_EXPOSE_REP", justification = "we need this for testing")
  public static int[] getTouchStart() {
    return coordinates;
  }

  public static boolean isHasValues() {
    return hasValues;
  }

  public static void reset() {
    hasValues = false;

  }
}
