/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.indexgenesys.skydev.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author ernest
 */
public class Verify {
      
   public static final String EMPTY_STRING = "";
   public static final Object[] EMPTY_ARRAY = new Object[] {};
   public static final List<?> EMPTY_LIST = new ArrayList<>(0);
   public static final Map<?, ?> EMPTY_MAP = new HashMap<>(0);
   
   public static boolean isNull(Object object) {
      return object == null;
   }
   
   public static boolean isNotNull(Object object) {
      return !isNull(object);
   }
   
   public static boolean Null(Object object) {
      return isNull(object);
   }
   
   public static boolean notNull(Object object) {
      return isNotNull(object);
   }
   
   public static boolean containsNull(Object... objects) {
      if (objects == null) {
         return true;
      } else {
         for (Object object : objects) {
            if (object instanceof Object[]) {
               return containsNull((Object[]) object);
            } else if (object instanceof Collection) {
               return containsNull((Collection<?>) object);
            } else if (isNull(object)) {
               return true;
            } else {
               
            }
         }
      }
      return false;
   }
   
   public static boolean notContainsNull(Object... objects) {
      return !containsNull(objects);
   }
   
   public static <E> boolean containsNull(Collection<E> collection) {
      if (collection == null) {
         return true;
      } else {
         for (E element : collection) {
            if (isNull(element)) {
               return true;
            }
         }
      }
      return false;
   }
   
   public static <E> boolean notContainsNull(Collection<E> collection) {
      return !containsNull(collection);
   }
   
   public static boolean containsNullOrEmpty(Object... objects) {
      if (isNullOrEmpty(objects)) {
         return true;
      } else {
         for (Object object : objects) {
            if (object instanceof Object[]) {
               return containsNullOrEmpty((Object[]) object);
            } else if (object instanceof Collection) {
               return containsNullOrEmpty((Collection<?>) object);
            } else if (isNullOrEmpty(object)) {
               return true;
            } else {
               
            }
         }
      }
      return false;
   }
   
   public static boolean containsEmptyOrNull(Object... objects) {
      return containsNullOrEmpty(objects);
   }
   
   public static boolean notContainsNullOrEmpty(Object... objects) {
      return !containsNullOrEmpty(objects);
   }
   
   public static boolean notContainsEmptyOrNull(Object... objects) {
      return notContainsNullOrEmpty(objects);
   }
   
   public static <E> boolean containsNullOrEmpty(Collection<E> collection) {
      if (isNullOrEmpty(collection)) {
         return true;
      } else {
         for (E element : collection) {
            if (isNullOrEmpty(element)) {
               return true;
            }
         }
      }
      return false;
   }
   
   public static <E> boolean containsEmptyOrNull(Collection<E> collection) {
      return containsNullOrEmpty(collection);
   }
   
   public static <E> boolean notContainsNullOrEmpty(Collection<E> collection) {
      return !containsNullOrEmpty(collection);
   }
   
   public static <E> boolean notContainsEmptyOrNull(Collection<E> collection) {
      return notContainsNullOrEmpty(collection);
   }
   
   public static boolean isEmpty(Object object) {
      if (object == null) return false;
      if (object instanceof String) {
         return ((String) object).isEmpty();
      } else if (object instanceof Collection) {
         return ((Collection<?>) object).isEmpty();
      } else if (object instanceof Map) {
         return ((Map<?, ?>) object).isEmpty();
      } else if (object.getClass().isArray()) {
         return ((Object[]) object).length == 0;
      } else {
         return false;
      }
   }
   
   public static boolean empty(Object object) {
      return isEmpty(object);
   }
   
   public static boolean isNotEmpty(Object object) {
      return !isEmpty(object);
   }
   
   public static boolean notEmpty(Object object) {
      return isNotEmpty(object);
   }
   
   public static boolean isEmpty(String string) {
      return string != null && string.isEmpty();
   }
   
   public static boolean isNotEmpty(String string) {
      return !isEmpty(string);
   }
   
   public static boolean empty(String string) {
      return isEmpty(string);
   }
   
   public static boolean notEmpty(String string) {
      return isNotEmpty(string);
   }
   
   public static boolean containsEmpty(String... strings) {
      if (strings != null) {
         for (String string : strings) {
            if (isEmpty(string)) {
               return true;
            }
         }
      }
      return false;
   }
   
   public static boolean notContainsEmpty(String... strings) {
      return !containsEmpty(strings);
   }
   
   public static boolean containsEmptyOrNull(String... strings) {
      if (isNotNull((Object[]) strings)) {
         for (String string : strings) {
            if (isEmptyOrNull(string)) {
               return true;
            }
         }
      }
      return false;
   }
   
   public static boolean notContainsEmptyOrNull(String... strings) {
      return !containsEmptyOrNull(strings);
   }
   
   public static <E> boolean isEmpty(Collection<E> collection) {
      return collection != null && collection.isEmpty();
   }
   
   public static <E> boolean isNotEmpty(Collection<E> collection) {
      return !isEmpty(collection);
   }
   
   public static <E> boolean empty(Collection<E> collection) {
      return isEmpty(collection);
   }
   
   public static <E> boolean notEmpty(Collection<E> collection) {
      return isNotEmpty(collection);
   }
   
   public static <K, V> boolean isNull(Map<K, V> map) {
      return map == null;
   }
   
   public static <K, V> boolean isNotNull(Map<K, V> map) {
      return map != null;
   }
   
   public static <K, V> boolean Null(Map<K, V> map) {
      return isNull(map);
   }
   
   public static <K, V> boolean notNull(Map<K, V> map) {
      return isNotNull(map);
   }
   
   public static <K, V> boolean isNullOrEmpty(Map<K, V> map) {
      return map == null || map.isEmpty();
   }
   
   public static <K, V> boolean isNotNullOrEmpty(Map<K, V> map) {
      return map != null && !map.isEmpty();
   }
   
   public static <K, V> boolean nullOrEmpty(Map<K, V> map) {
      return isNullOrEmpty(map);
   }
   
   public static <K, V> boolean notNullOrEmpty(Map<K, V> map) {
      return notNullOrEmpty(map);
   }
   
   public static boolean isNullOrEmpty(Object object) {
      // null.
      if (object == null) {
         return true;
      }
      // empty.
      if (object instanceof String) {
         return ((String) object).isEmpty();
      } else if (object instanceof Collection) {
         return ((Collection<?>) object).isEmpty();
      } else if (object instanceof Map) {
         return ((Map<?, ?>) object).isEmpty();
      } else if (object.getClass().isArray()) {
         return ((Object[]) object).length == 0;
      } else {
         return false;
      }
   }
   
   public static boolean isNotNullOrEmpty(Object object) {
      return !isNullOrEmpty(object);
   }
   
   public static boolean nullOrEmpty(Object object) {
      return isNullOrEmpty(object);
   }
   
   public static boolean notNullOrEmpty(Object object) {
      return isNotNullOrEmpty(object);
   }
   
   public static <K, V> boolean isEmpty(Map<K, V> map) {
      return map != null && map.isEmpty();
   }
   
   public static <K, V> boolean isNotEmpty(Map<K, V> map) {
      return map != null && !map.isEmpty();
   }
   
   public static <K, V> boolean empty(Map<K, V> map) {
      return isEmpty(map);
   }
   
   public static <K, V> boolean notEmpty(Map<K, V> map) {
      return isNotEmpty(map);
   }
   
   public static boolean isEmptyOrNull(Object object) {
      // null.
      if (object == null) {
         return true;
      }
      // empty.
      if (object instanceof String) {
         return ((String) object).isEmpty();
      } else if (object instanceof Collection) {
         return ((Collection<?>) object).isEmpty();
      } else if (object instanceof Map) {
         return ((Map<?, ?>) object).isEmpty();
      } else if (object.getClass().isArray()) {
         return ((Object[]) object).length == 0;
      } else {
         return false;
      }
   }
   
   public static boolean isNotEmptyOrNull(Object object) {
      return !isEmptyOrNull(object);
   }
   
   public static boolean emptyOrNull(Object object) {
      return isEmptyOrNull(object);
   }
   
   public static boolean notEmptyOrNull(Object object) {
      return isNotEmptyOrNull(object);
   }
   
   public static boolean isEmptyOrNull(Object... objects) {
       // objects != null && objects.length > 0
       if (!isEmptyOrNull((Object) objects)) {
          for (Object object : objects) {
             if (isEmptyOrNull(object)) {
                return true;
             }
          }
          return false;
       }
       return true;
    }
    
    public static boolean isNotEmptyOrNull(Object... objects) {
       return !isEmptyOrNull(objects);
    }
    
    public static boolean emptyOrNull(Object... objects) {
       return isEmptyOrNull(objects);
    }
    
    public static boolean notEmptyOrNull(Object... objects) {
       return isNotEmptyOrNull(objects);
    }
   
   public static boolean exists(Object object) {
      return isNotNull(object);
   }
   
   public static boolean notExists(Object object) {
      return isNull(object);
   }
   
   public static boolean empty(Object... objects) {
      if (objects != null) {
         for (Object object : objects) {
            if (empty(object)) {
               return true;
            }
         }
         return false;
      }
      return true;
   }
   
   public static boolean isArray(Object object) {
      return (object instanceof Object[] || object.getClass().isArray());
   }
   
   public static boolean array(Object object) {
      return isArray(object);
   }
   
   public static boolean isNotArray(Object object) {
      return !isArray(object);
   }
   
   public static boolean notArray(Object object) {
      return isNotArray(object);
   }
   
   public static boolean isList(Object object) {
      return object instanceof java.util.List;
   }
   
   public static boolean isNotList(Object object) {
      return !isList(object);
   }
   
   public static boolean list(Object object) {
      return isList(object);
   }
   
   public static boolean notList(Object object) {
      return isNotList(object);
   }
   
   public static boolean isMap(Object object) {
      return object instanceof java.util.Map;
   }
   
   public static boolean isNotMap(Object object) {
      return !isMap(object);
   }
   
   public static boolean map(Object object) {
      return isMap(object);
   }
   
   public static boolean notMap(Object object) {
      return isNotMap(object);
   }
   
   public static boolean isArrayOrList(Object object) {
      return isArray(object) || isList(object);
   }
   
   public static boolean isNotArrayOrList(Object object) {
      return !isArrayOrList(object);
   }
   
   public static boolean arrayOrList(Object object) {
      return isArrayOrList(object);
   }
   
   public static boolean notArrayOrList(Object object) {
      return !arrayOrList(object);
   }
   
   public static boolean isPair(Object object) {
      boolean isPair = false;
      if (isNotNullOrEmpty(object)) {
         if (isArray(object) && Array.getLength(object) == 2) {
            isPair = true;
         }
         if (isList(object) && ((List<?>) object).size() == 2) {
            isPair = true;
         }
      }
      return isPair;
   }
   
   public static boolean pair(Object object) {
      return isPair(object);
   }
   
   public static boolean isNotPair(Object object) {
      return !isPair(object);
   }
   
   public static boolean notPair(Object object) {
      return isNotPair(object);
   }
   
   public static boolean containsOnlyPairs(Object... objects) {
      if (isNotNullOrEmpty(objects)) {
         for (Object object : objects) {
            if (isNotPair(object)) {
               return false;
            }
         }
         return true;
      }
      return false;
   }
   
   public static boolean onlyPairs(Object... objects) {
      return containsOnlyPairs(objects);
   }
   
   public static boolean notContainsOnlyPairs(Object... objects) {
      return !containsOnlyPairs(objects);
   }
   
   public static boolean notOnlyPairs(Object... objects) {
      return notContainsOnlyPairs(objects);
   }
   
   public static <T extends Number> NumberVerification<T> numberVerification(T number) {
      return NumberVerification.<T>of(number);
   }
   
   public static <T extends Number> NumberVerification<T> verifyNumber(T number) {
      return numberVerification(number);
   }
   
   public static <T extends Number> NumberVerification<T> number(T number) {
      return numberVerification(number);
   }
   
}
