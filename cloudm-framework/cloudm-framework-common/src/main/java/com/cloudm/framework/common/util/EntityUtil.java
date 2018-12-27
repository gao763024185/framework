package com.cloudm.framework.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * 实体对象工具类
 *
 * Created by jackson on 2017/5/11.
 *
 */
@Slf4j
public class EntityUtil {

    private static final String DATE_CLASS_NAME = Date.class.getName();

    /**
     * 将list中元素的某一成员组装成list返回。注意：会去重!
     * @param list 元素列表
     * @param fieldName 成员变量的field
     * @param <T> 元素类型
     * @return 返回该字段组成的list
     */
    public static <T> List makeListByFieldName(List<T> list, String fieldName) {
        List returnList = new LinkedList();
        if (list == null || list.isEmpty()) {
            return returnList;
        }
        Object firstObj = list.get(0);
        Field field = FieldUtils.getField(firstObj.getClass(), fieldName, true);
        if (field == null) {
            throw new RuntimeException(firstObj.getClass().getName() + "不存在" + fieldName);
        }
        try {
            for (T o : list) {
                if(!returnList.contains(field.get(o))) {
                    returnList.add(field.get(o));
                }
            }
        } catch (IllegalAccessException e) {
            //懒得在外面try catch, 直接ignore
            throw new RuntimeException(e);
        }
        return returnList;
    }

    /**
     * 将Collection中元素的某一成员组装成Set返回
     * @param collection 元素列表
     * @param fieldName 成员变量的field
     * @param <T> 元素类型
     * @return 返回该字段组成的LinkedHashSet。若元素中不存在名为fieldName的成员变量，则返回EmptySet
     */
    public static <T> LinkedHashSet makeLinkedSetByFieldName(Collection<T> collection, String fieldName) {
        LinkedHashSet returnLinkedSet = new LinkedHashSet<>();
        Iterator<T> it = collection.iterator();
        boolean isFirst = true;
        Field field = null;
        while (it.hasNext()) {
            T o = it.next();
            if (isFirst) {
                field = FieldUtils.getField(o.getClass(), fieldName, true);
                if (field == null) {
                    throw new RuntimeException(o.getClass().getName() + "不存在" + fieldName);
                }
                isFirst = false;
            }
            try {
                returnLinkedSet.add(field.get(o));
            } catch (IllegalAccessException e) {
                //ignore
                throw new RuntimeException(e);
            }
        }
        return returnLinkedSet;
    }

    /**
     * 将list中的元素放到Map<M, N>以建立 key - value 索引<p>
     * modified from com.tqmall.saint.biz.util.EntityUtil#makeEntityMap(java.util.List, java.lang.String)
     *
     * @param collection Collection<V> 元素列表
     * @param keyFieldName String 元素的属性名称, 该属性的值作为Map的key
     * @param valueFieldName String 元素的属性名称, 该属性的值作为Map的value
     * @param <M> key类型
     * @param <N> value类型
     * @param <V> 列表元素类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <M, N, V> Map<M, N> makeMapWithKeyValue(Collection<V> collection, String keyFieldName, String valueFieldName) {
        Map<M, N> map = new HashMap<>();
        if (collection == null || collection.isEmpty()) {
            return map;
        }
        Iterator<V> it = collection.iterator();
        boolean isFirst = true;
        Method keyGetter = null;
        Method valueGetter = null;
        try {
            while (it.hasNext()) {
                V o = it.next();
                if (isFirst) {
                    keyGetter = getMethod(o.getClass(), keyFieldName, "get");
                    valueGetter = getMethod(o.getClass(), valueFieldName, "get");
                    isFirst = false;
                }
                map.put((M) keyGetter.invoke(o), (N) valueGetter.invoke(o));
            }
        } catch (Exception e) {
            log.error("makeEntityMap error list is " + collection, e);
            return map;
        }
        return map;
    }

    /**
     *
     * 将list中的元素放到Map<K, V>以建立 key - value 索引<p>
     *
     * @param list  List<V> 元素列表
     * @param keyFieldName String 元素的属性名称, 该属性的值作为索引key
     * @param <K> key类型
     * @param <V> value类型
     * @return Map<K, V> key - value 索引
     *
     * Created by gry on 2014/8/14.
     *
     */
  @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> makeEntityMap(List<V> list, String keyFieldName) {
        Map<K, V> map = new HashMap<>();
        if(list == null || list.size() == 0) {
            return map;
        }
        try {
            Method getter = getMethod(list.get(0).getClass(), keyFieldName, "get");
            for (V item : list) {
                map.put((K) getter.invoke(item), item);
            }
        } catch (Exception e) {
            log.error("makeEntityMap error list is " + list, e);
            return map;
        }
        return map;
    }

    /**
     * 将list中的元素转化为Map<String, List<V>>
     * 其中key相同的对象汇总为list
     * @param list  List<V> 元素列表
     * @param splitVar  属性之间间隔
     * @param keyFieldNames String 元素的属性名称动态数组, 依次循环该属性的值并以splitVar分割作为索引key
     * @param <V> value类型
     * @return Map<String, List<V>> key - value 索引
     * @author liuting
     */
    public static <V> Map<String, List<V>> makeEntityMapListByKeys(List<V> list,String splitVar , String... keyFieldNames) {
        Map<String, List<V>> map = new HashMap<>();
        if(list == null || list.isEmpty() || keyFieldNames == null || keyFieldNames.length == 0 || StringUtil.isEmpty(splitVar)) {
            return map;
        }
        try {
            List<Method> getterList = new ArrayList<>();
            for(String key : keyFieldNames){
                getterList.add(getMethod(list.get(0).getClass(),key, "get"));
            }
            for (V item : list) {
                Object[] keys = new Object[getterList.size()];
                for (int i = 0; i < getterList.size(); i++) {
                    keys[i] =  getterList.get(i).invoke(item);
                }
                String key = StringUtil.getStringByArraySplitSeparator(splitVar, keys);
                if (map.get(key) == null) {
                    List<V> childrenList = new ArrayList<>();
                    childrenList.add(item);
                    map.put(key, childrenList);
                } else {
                    map.get(key).add(item);
                }
            }
        } catch (Exception e) {
            log.error("makeEntityListMap error list is " + list, e);
            return map;
        }
        return map;
    }

    /**
     *
     * 将list中的元素放到Map<String, V>以建立 key - value 索引<p>
     *
     * @param list  List<V> 元素列表
     * @param splitVar  属性之间间隔
     * @param keyFieldNames String 元素的属性名称动态数组, 依次循环该属性的值作为索引key
     * @param <V> value类型
     * @return Map<String, V> key - value 索引
     *
     * Created by gry on 2014/8/14.
     *
     */

    @SuppressWarnings("unchecked")
    public static <V> Map<String, V> makeEntityMapByKeys(List<V> list,String splitVar , String... keyFieldNames) {
        Map<String, V> map = new HashMap<>();
        if(list == null || list.size() == 0 || keyFieldNames == null || keyFieldNames.length==0 || StringUtil.isEmpty(splitVar)) {
            return map;
        }
        try {
            List<Method> getterList = new ArrayList<>();
            for(String key : keyFieldNames){
                getterList.add(getMethod(list.get(0).getClass(),key, "get"));
            }
            for (V item : list) {
                StringBuffer keys= new StringBuffer("");
                for (int i=0;i<getterList.size();i++){
                    keys.append(getterList.get(i).invoke(item));
                    if(i<getterList.size()-1){
                        keys.append(splitVar);
                    }
                }
                map.put(keys.toString(), item);
            }
        } catch (Exception e) {
            log.error("makeEntityMap error list is " + list, e);
            return map;
        }
        return map;
    }
    /**
     *
     * 将list中的元素放到Map<K, List<V>> 以建立 key - List<value> 索引<p>
     *
     * @param list  List<V> 元素列表
     * @param keyFieldName String 元素的属性名称, 该属性的值作为索引key
     * @param <K> key类型
     * @param <V> value类型
     * @return Map<K, V> key - value 索引
     *
     * Created by gry on 2014/8/14.
     *
     */
    public static <K, V> Map<K, List<V>> makeEntityListMap(List<V> list, String keyFieldName) {
        Map<K, List<V>> map = new LinkedHashMap<>();
        if(list == null || list.size() == 0) {
            return map;
        }
        try {
            Method getter = getMethod(list.get(0).getClass(), keyFieldName, "get");
            for (V item : list) {
        @SuppressWarnings("unchecked")
                K key = (K) getter.invoke(item);
                List<V> groupList = map.get(key);
                if (groupList == null) {
                    groupList = new ArrayList<>();
                    map.put(key, groupList);
                }
                groupList.add(item);
            }
        } catch (Exception e) {
            log.error("makeEntityListMap error list is " + list, e);
            return map;
        }
        return map;
    }

    /**
     * 获取getter或setter
     */
  @SuppressWarnings("unchecked")
  public static Method getMethod(@SuppressWarnings("rawtypes") Class clazz, String fieldName,
      String methodPrefix) throws NoSuchMethodException {
        String first = fieldName.substring(0, 1);
        String getterName = methodPrefix + fieldName.replaceFirst(first, first.toUpperCase());
        return clazz.getMethod(getterName);
    }

    /**
     *  比较两个对象改变了的属性值，然后以string拼接返回
     * @param oldObj 对象1
     * @param newObj 对象2
     * @return 改变的属性值拼接的字符串
     */
    public static <T> String compareToObjProperty(T oldObj,T newObj){
        String modifiedStr = "";
        Field[] fields = oldObj.getClass().getDeclaredFields();
        for(Field field : fields){
            try {
                if(!Modifier.isStatic(field.getModifiers())){
                    String tempFieldType =field.getType().getName();
                    Method tempMethod = getMethod(oldObj.getClass(), field.getName(), "get");
                    if(field.getName().equals("ATTRIBUTE_ORDER_SN")){
                        System.out.println("aa");
                    }
                    if(tempMethod!=null){
                        Object tempOld = tempMethod.invoke(oldObj);
                        Object tempNew = tempMethod.invoke(newObj);
                        if(findDifference(tempOld,tempNew)){
                            if(tempFieldType.equals("java.util.Date")){
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd mm:HH:ss");
                                modifiedStr += field.getName() + "[" + ( tempOld == null ? null: sdf.format(tempOld)) + "," +
                                        (tempNew == null ? null : sdf.format(tempNew)) + "],";
                            } else {
                                modifiedStr += field.getName()+"["+tempOld+","+ tempNew+"],";
                            }
                        }
                    }
                }
            }catch (Exception e) {
                log.error("compareTo error", e);
            }
        }
        return modifiedStr;
    }

    /**
     * 获取所有field，不包含field，修改自FieldUtils的getAllFieldsList方法
     * @param cls 类
     * @param forceAccess 是否包含private的field
     * @return
     */
    private static List<Field> getFieldsList(Class<?> cls, final boolean forceAccess) {
        Validate.isTrue(cls != null, "The class must not be null");
        final List<Field> allFields = new ArrayList<Field>();
        Class<?> currentClass = cls;
        final Field[] declaredFields = currentClass.getDeclaredFields();
        for (Field field : declaredFields) {
            if (!Modifier.isPublic(field.getModifiers())) {
                if (forceAccess) {
                    field.setAccessible(true);
                } else {
                    continue;
                }
            }
            allFields.add(field);
        }
        return allFields;
    }

    /**
     * 获取所有field，包含所有父类，来自FieldUtils的getAllFieldsList方法
     * @param cls 类
     * @param forceAccess 是否包含private的field
     * @return
     */
    private static List<Field> getAllFieldsList(Class<?> cls, final boolean forceAccess) {
        Validate.isTrue(cls != null, "The class must not be null");
        final List<Field> allFields = new ArrayList<Field>();
        Class<?> currentClass = cls;
        while (currentClass != null) {
            final Field[] declaredFields = currentClass.getDeclaredFields();
            for (Field field : declaredFields) {
                if (!Modifier.isPublic(field.getModifiers())) {
                    if (forceAccess) {
                        field.setAccessible(true);
                    } else {
                        continue;
                    }
                }
                allFields.add(field);
            }
            currentClass = currentClass.getSuperclass();
        }
        return allFields;
    }

    /**
     * 获取类型的field
     * @param cls 类型
     * @param checkSupers 是否要获取父类的field
     * @param forceAccess 是否包含private的field
     * @return
     */
    private static List<Field> getFieldsList(Class<?> cls, final boolean checkSupers, final boolean forceAccess) {
        if (checkSupers) {
            return getAllFieldsList(cls, forceAccess);
        } else {
            return getFieldsList(cls, forceAccess);
        }
    }

    /**
     * 比较两个对象改变了的属性值，然后以string拼接返回
     * @param oldObj 对象1
     * @param newObj 对象2
     * @param formatter 格式(默认为%s[%s,%s]，第一个%s对应fieldname，第二个%对应对象1的fieldname的值，第三个%s对应对象2的fieldname的值)
     * @param checkSupers 是否需要比较所有父类(无视继承的接口)
     * @param forceAccess 是否需要比较private成员变量
     * @param <T> 要比较的对象类型
     * @return
     */
    public static <T> String compareToObjProperty(T oldObj, T newObj, String formatter, final boolean checkSupers, final boolean forceAccess) {
        StringBuilder builder = new StringBuilder();
        if (oldObj == newObj) {
            return builder.toString();
        }
        if (StringUtils.isEmpty(formatter)) {
            formatter = "%s[%s,%s]";
        }
        List<Field> fields = getFieldsList(oldObj.getClass(), checkSupers, forceAccess);
        for (Field field : fields) {
            try {
                Object tmpOld = field.get(oldObj);
                Object tmpNew = field.get(newObj);
                if (findDifference(tmpOld, tmpNew)) {
                    if (field.getType().getName().equals(DATE_CLASS_NAME)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd mm:HH:ss");
                        String strOld = tmpOld == null ? null : sdf.format(tmpOld);
                        String strNew = tmpNew == null ? null : sdf.format(tmpNew);
                        builder.append(String.format(formatter, field.getName(), strOld, strNew));
                        builder.append(",");
                    } else {
                        builder.append(String.format(formatter, field.getName(), tmpOld, tmpNew));
                        builder.append(",");
                    }
                }
            } catch (Exception e) {
                log.error("compareTo error",e);
            }
        }
        return builder.toString();
    }

    /**
     * 比较两个对象的值是否不同
     * @param obj1 对象1
     * @param obj2 对象2
     * @param <T> object
     * @return 若俩对象的值不相同则为true，反之为false
     */
    public static <T> Boolean findDifference(T obj1,T obj2){
        if(obj1 == null && obj2 == null){
            return false;
        }
        if(obj1 == null || obj2 == null){
            return true;
        }
        if(obj1 instanceof BigDecimal){
            return ((BigDecimal) obj1).compareTo((BigDecimal)obj2) != 0;
        }else {
            return !obj1.equals(obj2);
        }
    }

    public static <V,K> Map<V, K> makeEntityMapNew(List<Map<String,Object>> hashMap,String keyFieldName) {
        Map<V, K> map = new HashMap<>();
        if(hashMap == null || hashMap.size() == 0) {
            return map;
        }
        try {
            for(Map linkedHashMap:hashMap){
                map.put((V)linkedHashMap.get(keyFieldName).toString(),(K)linkedHashMap);
            }
        } catch (Exception e) {
            log.error("makeEntityListMap error list is " + hashMap, e);
            return map;
        }
        return map;
    }


    public static Map<Integer, Map> makeEntityMapSpecial(List<Map> hashMap,String keyFieldName) {
        Map<Integer, Map> map = new HashMap<>();
        if(hashMap == null || hashMap.size() == 0) {
            return map;
        }
        try {
            for(Map linkedHashMap:hashMap){
                map.put(Integer.valueOf(linkedHashMap.get(keyFieldName).toString()),linkedHashMap);
            }
        } catch (Exception e) {
            log.error("makeEntityListMap error list is " + hashMap, e);
            return map;
        }
        return map;
    }


}
