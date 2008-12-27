/*
 * Copyright 2005-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.dozer.event;

import java.util.List;

import net.sf.dozer.util.MapperConstants;
import net.sf.dozer.util.MappingUtils;

/**
 * Internal class that handles dozer events and invokes any public event listeners. Only intended for internal use.
 * 
 * @author garsombke.franz
 */
public class DozerEventManager implements EventManager {
  private final List<DozerEventListener> eventListeners;

  public DozerEventManager(List<DozerEventListener> eventListeners) {
    this.eventListeners = eventListeners;
  }

  public void fireEvent(DozerEvent event) {
    // If no listeners were specified, then just return.
    if (eventListeners == null) {
      return;
    }
    String eventType = event.getType();
    for (DozerEventListener listener : eventListeners) {
      if (eventType.equals(MapperConstants.MAPPING_STARTED_EVENT)) {
        listener.mappingStarted(event);
      } else if (eventType.equals(MapperConstants.MAPPING_PRE_WRITING_DEST_VALUE)) {
        listener.preWritingDestinationValue(event);
      } else if (eventType.equals(MapperConstants.MAPPING_POST_WRITING_DEST_VALUE)) {
        listener.postWritingDestinationValue(event);
      } else if (eventType.equals(MapperConstants.MAPPING_FINISHED_EVENT)) {
        listener.mappingFinished(event);
      } else {
        MappingUtils.throwMappingException("Unsupported event type: " + eventType);
      }
    }
  }

}
