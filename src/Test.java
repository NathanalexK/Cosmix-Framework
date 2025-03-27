import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public void doTest(List<String> args) {

    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Method m = Test.class.getMethod("doTest", List.class);
        Parameter[] parameters = m.getParameters();
        for(Parameter parameter: parameters) {
//            System.out.println(((ParameterizedType) parameter.getParameterizedType()).getActualTypeArguments()[0]);
            if(parameter.getType() == List.class){
                System.out.println(((ParameterizedType) parameter.getParameterizedType()).getActualTypeArguments()[0]);
            }
        }

        String a = "";
        String[] s = new String[0];
        List<String> l = new ArrayList<>();
        System.out.println(a.getClass());
        System.out.println(s.getClass());
//        System.out.println(((ParameterizedType) l.getClass().).getActualTypeArguments()[0]);
    }
}
