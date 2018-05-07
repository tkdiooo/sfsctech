//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.util;

import java.lang.reflect.Constructor;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public final class ObjectPoolFactoryUtil {
    public ObjectPoolFactoryUtil() {
    }

    public static <E extends IObject> SimpleObjectPool getObjectPool(Class<? extends E> objectClass, Object[] args, int maxTotal, int maxIdle) {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        return new SimpleObjectPool(new ObjectPoolFactoryUtil.ObjectPoolFactoryBase(objectClass, args), config);
    }

    private static class ObjectPoolFactoryBase<T> extends BasePooledObjectFactory<T> {
        private final Class<T> objectClass;
        private final Object[] args;
        private Class[] argTypes;

        public ObjectPoolFactoryBase(Class<T> objectClass, Object[] args) {
            this.objectClass = objectClass;
            this.args = args;
            if (args != null && args.length > 0) {
                this.argTypes = new Class[args.length];

                for(int i = 0; i < args.length; ++i) {
                    this.argTypes[i] = args[i].getClass();
                }
            }

        }

        public T create() throws Exception {
            Constructor<T> constructor = this.objectClass.getDeclaredConstructor(this.argTypes);
            constructor.setAccessible(true);
            return constructor.newInstance(this.args);
        }

        public PooledObject<T> wrap(T t) {
            return new DefaultPooledObject(t);
        }
    }
}
