package com.sfsctech.core.base.json;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.sfsctech.core.base.constants.JsonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * Class FastJsonFilter
 *
 * @author 张麒 2020-2-20.
 * @version Description:
 */
@Slf4j
public class FastJsonFilter implements ValueFilter {

    @Override
    public Object process(Object object, String name, Object value) {
//        if (!(value instanceof String) || StringUtils.isBlank((String) value)) {
//            return value;
//        }
//        try {
//            Field[] fields = object.getClass().getDeclaredFields();
//            Field field = null;
//            for (Field field1 : fields) {
//                if (field1.getName().equals(name)) {
//                    field = field1;
//                    break;
//                }
//            }
//            if (null == field) {
//                field = object.getClass().getSuperclass().getDeclaredField(name);
//            }
//            Desensitized desensitization;
//            if (String.class != field.getType() || (desensitization = field.getAnnotation(Desensitized.class)) == null) {
//                return value;
//            }
//            String valueStr = (String) value;
//            JsonConstants.SensitiveType type = desensitization.type();
//            switch (type) {
//                case ChineseName:
//                    return DesensitizedUtil.chineseName(valueStr);
//                case IdCard:
//                    return DesensitizedUtil.idCardNum(valueStr);
//                case FixedPhone:
//                    return DesensitizedUtil.fixedPhone(valueStr);
//                case MobilePhone:
//                    return DesensitizedUtil.mobilePhone(valueStr);
//                case Address:
//                    return DesensitizedUtil.address(valueStr, 8);
//                case Email:
//                    return DesensitizedUtil.email(valueStr);
//                case BankCard:
//                    return DesensitizedUtil.bankCard(valueStr);
//                case Password:
//                    return DesensitizedUtil.password(valueStr);
//                case CarNumber:
//                    return DesensitizedUtil.carNumber(valueStr);
//                default:
//            }
//        } catch (NoSuchFieldException e) {
//            log.error("当前数据类型为{},值为{}", object.getClass(), value);
//            return value;
//        }
        return value;

    }
}
