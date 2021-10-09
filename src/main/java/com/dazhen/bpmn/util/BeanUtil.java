package com.dazhen.bpmn.util;

import org.springframework.cglib.beans.BeanCopier;

import java.util.HashMap;
import java.util.Map;

/**
 * @author junke
 */
public class BeanUtil {
    private static final Map<String, BeanCopier> BEAN_COPIER_MAP = new HashMap<>();


    /**
     * 如果使用了lombok的Accessor(chain=true)注解的，此方法将拷贝失败
     *
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T copyWithCopier(Object source, T target) {
        String beanKey = generateKey(source.getClass(), target.getClass());
        BeanCopier copier;
        if (!BEAN_COPIER_MAP.containsKey(beanKey)) {
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            BEAN_COPIER_MAP.put(beanKey, copier);
        } else {
            copier = BEAN_COPIER_MAP.get(beanKey);
        }
        copier.copy(source, target, null);
        return target;
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }
}
