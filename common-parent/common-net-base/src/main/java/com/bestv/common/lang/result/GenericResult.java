//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.lang.result;

public class GenericResult<T> extends BaseResult {
    private static final long serialVersionUID = 1039884688004906480L;
    private T value;

    public GenericResult() {
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
