package com.sfsctech.common.rpc.util;


import com.sfsctech.common.base.result.ValidatorResult;
import com.sfsctech.common.util.SpringContextUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Class ValidatorUtil
 *
 * @author 张麒 2017/3/16.
 * @version Description:
 */
public class ValidatorUtil {

    public static Validator validator = (Validator) SpringContextUtil.getBean("validator");

    public static <T> ValidatorResult validate(T t) {
        ValidatorResult result = new ValidatorResult();
        Set<ConstraintViolation<T>> set = validator.validate(t);
        result.setHasErrors(set.iterator().hasNext());
        if (result.hasErrors()) {
            set.forEach(error -> result.getMessages().put(error.getPropertyPath().toString(), error.getMessage()));
        }
        return result;
    }
}
