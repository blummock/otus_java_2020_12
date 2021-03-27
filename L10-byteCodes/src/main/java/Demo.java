import java.lang.reflect.InvocationTargetException;

public class Demo {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        TestLoggingInterface myClass = ProxyBuilder.createProxy(TestLogging.class);
        myClass.calculation(1);
        myClass.calculation(2,1.1);
        myClass.calculation(1,2,"sub");
        myClass.calculation("Hello");
    }
}



