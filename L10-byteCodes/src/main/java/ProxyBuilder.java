import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class ProxyBuilder<T> implements InvocationHandler {

    ProxyBuilder(T claazz, List<String> modified) {
        this.tClass = claazz;
        this.modified = modified;
    }

    private final T tClass;
    private List<String> modified;

    static <T> T createProxy(Class<T> tClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        var toModify = new ArrayList<String>();
        for (var meth : tClass.getMethods()) {
            if (meth.isAnnotationPresent(Log.class)) {
                toModify.add(meth.getName() + Arrays.stream(meth.getParameterTypes()).map(Class::getName).collect(Collectors.joining()));
            }
        }
        InvocationHandler handler = new ProxyBuilder(tClass.getDeclaredConstructor().newInstance(), toModify);
        return (T) Proxy.newProxyInstance(ProxyBuilder.class.getClassLoader(), tClass.getInterfaces(), handler);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        var name = method.getName();
        if (modified.contains(name + Arrays.stream(args).map(Object::getClass).map(Class::getName).collect(Collectors.joining()))) {
            System.out.printf("executed method: %s, params: %s\n", name, Arrays.stream(args).map(Object::toString)
                    .collect(Collectors.joining(", ")));
        }
        return method.invoke(tClass, args);
    }
}
