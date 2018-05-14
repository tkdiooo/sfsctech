//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sfsctech.common.cloud.net.resolver;


import com.sfsctech.common.cloud.net.domain.ServiceInterface;

/**
 * Class ServiceInterface
 *
 * @author 张麒 2018-5-9.
 * @version Description:
 */
public interface InterfaceResolver<T> {

    ServiceInterface parse(T t);
}
