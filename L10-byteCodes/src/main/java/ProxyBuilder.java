import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.stream.Collectors;

class ProxyBuilder<T> implements InvocationHandler {

    ProxyBuilder(T claazz) {
        tClass = claazz;
    }

    private final T tClass;

    static <T> T createProxy(Class<T> tClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        InvocationHandler handler = new ProxyBuilder(tClass.getDeclaredConstructor().newInstance());
        return (T) Proxy.newProxyInstance(ProxyBuilder.class.getClassLoader(), tClass.getInterfaces(), handler);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Log.class)) {
            System.out.printf("executed method: %s, params: %s\n", method.getName(), Arrays.stream(args).map(Object::toString)
                    .collect(Collectors.joining(", ")));
        }
        return method.invoke(tClass, args);
    }
}
