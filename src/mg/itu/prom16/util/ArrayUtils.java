package mg.itu.prom16.util;

import java.util.Objects;

public class ArrayUtils {
    public static  <T> boolean contains(T[] list, T target){
        for(T item: list) {
            if(Objects.equals(item, target)) {
                return true;
            }
        }

        return false;
    }
}
