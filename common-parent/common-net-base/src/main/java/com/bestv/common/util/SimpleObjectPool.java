//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.util;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class SimpleObjectPool<T extends IObject> extends GenericObjectPool<T> {
    SimpleObjectPool(PooledObjectFactory factory, GenericObjectPoolConfig config) {
        super(factory, config);
    }

    public T fetchObject() throws Exception {
        T object = this.borrowObject();
        return object;
    }

    public void putBack(T object) {
        object.clear();
        this.returnObject(object);
    }
}
