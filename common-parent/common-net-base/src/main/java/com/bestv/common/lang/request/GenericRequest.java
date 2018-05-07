//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.lang.request;

public class GenericRequest<T> extends BaseRequest {
    private static final long serialVersionUID = -5473610741275580970L;
    private T value;

    public GenericRequest() {
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
