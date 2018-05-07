//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.util;

import com.bestv.common.lang.annotation.concurrent.GuardedBy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CircledSet<V> {
    @GuardedBy("readLock, writeLock")
    private List<V> list = new ArrayList();
    private Lock readLock;
    private Lock writeLock;

    public CircledSet() {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        this.readLock = readWriteLock.readLock();
        this.writeLock = readWriteLock.writeLock();
    }

    public boolean isEmpty() {
        this.readLock.lock();
        boolean isEmpty = this.list.isEmpty();
        this.readLock.unlock();
        return isEmpty;
    }

    public boolean contains(V value) {
        this.readLock.lock();
        boolean containsValue = this.list.contains(value);
        this.readLock.unlock();
        return containsValue;
    }

    public boolean add(V value) {
        this.writeLock.lock();
        if (!this.list.contains(value)) {
            boolean added = this.list.add(value);
            this.writeLock.unlock();
            return added;
        } else {
            this.writeLock.unlock();
            return false;
        }
    }

    public boolean remove(V value) {
        this.writeLock.lock();
        boolean removed = this.list.remove(value);
        this.writeLock.unlock();
        return removed;
    }

    public int size() {
        this.readLock.lock();
        int size = this.list.size();
        this.readLock.unlock();
        return size;
    }

    public Iterator<V> iterator() {
        return new CircledSet.CircledSetIterator();
    }

    private class CircledSetIterator implements Iterator<V> {
        int current;

        private CircledSetIterator() {
            this.current = 0;
        }

        public boolean hasNext() {
            CircledSet.this.readLock.lock();
            boolean hasNext = CircledSet.this.size() > 0;
            CircledSet.this.readLock.unlock();
            return hasNext;
        }

        public V next() {
            if (CircledSet.this.size() <= 0) {
                return null;
            } else {
                CircledSet.this.readLock.lock();
                V value = CircledSet.this.list.get(++this.current % CircledSet.this.list.size());
                CircledSet.this.readLock.unlock();
                return value;
            }
        }

        public void remove() {
            CircledSet.this.writeLock.lock();
            CircledSet.this.list.remove(this.current % CircledSet.this.list.size());
            CircledSet.this.writeLock.lock();
        }
    }
}
