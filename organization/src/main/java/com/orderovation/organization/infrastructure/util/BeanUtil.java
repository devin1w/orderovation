package com.orderovation.organization.infrastructure.util;

import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;


/**
 * @author dt
 * @date 2018-12-04 下午 2:40
 */
public class BeanUtil {

    private BeanUtil() {
    }

    public static <E, T> List<T> copyList(List<E> elements, Class<T> cls) {
        List<T> resultList = new ArrayList<T>();
        if (elements == null || elements.isEmpty()) {
            return resultList;
        }
        BeanCopier copier = BeanCopier.create(elements.get(0).getClass(), cls, false);
        for (E element : elements) {
            T obj = null;
            try {
                obj = cls.newInstance();
                copier.copy(element, obj, null);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            resultList.add(obj);
        }
        return resultList;
    }

    public static <E, T> T copyObject(E element, Class<T> cls) {
        if (element == null) {
            return null;
        }
        BeanCopier copier = BeanCopier.create(element.getClass(), cls, false);
        try {
            T obj = cls.newInstance();
            copier.copy(element, obj, null);
            return obj;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
