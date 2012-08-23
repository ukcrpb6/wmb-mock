/**
 * Copyright 2012 Bob Browning
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ibm.broker.plugin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public abstract class AbstractNativeManager<T, N extends NativeFor<T>> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractNativeManager.class);

    private Map<Long, N> allocations = Maps.newConcurrentMap();

    public List<N> getAllocations() {
        return ImmutableList.copyOf(allocations.values());
    }

    public N getNative(long handle) {
        return allocations.get(handle);
    }

    public abstract long getHandle(T object);

    public N getNative(T object) {
        return getNative(getHandle(object));
    }

    protected N register(N instance) {
        logger.info("Registering new {} with handle {}", instance.getClass().getSimpleName(), instance.getHandle());
        allocations.put(instance.getHandle(), instance);
        return instance;
    }

    protected N allocate(Object... parameters) {
        return register(createNativeInstance(parameters));
    }

    protected void remove(long handle) {
        allocations.remove(handle);
    }

    protected void remove(N o) {
        remove(o.getHandle());
    }

    protected void remove(T o) {
        remove(getHandle(o));
    }

    protected abstract N createNativeInstance(Object... parameters);

    public void clear() {
        allocations.clear();
    }

    public boolean isManaged(T o) {
        return isManaged(getHandle(o));
    }

    public boolean isManaged(N o) {
        return isManaged(o.getHandle());
    }

    public boolean isManaged(long handle) {
        return allocations.containsKey(handle);
    }
}
