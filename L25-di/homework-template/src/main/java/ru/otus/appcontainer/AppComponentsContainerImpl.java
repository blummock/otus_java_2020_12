package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        try {
            List<Method> components = Arrays.stream(configClass.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(AppComponent.class))
                    .sorted(Comparator.comparingInt(this::getAnnotationOrder)).collect(Collectors.toList());
            Object config = configClass.getConstructor(null).newInstance(null);
            for (Method component : components) {
                Object[] args = Arrays.stream(component.getParameterTypes()).map(this::getAppComponent).toArray();
                Object obj = component.invoke(config, args);
                appComponents.add(obj);
                appComponentsByName.put(component.getAnnotation(AppComponent.class).name(), obj);
            }
        } catch (Exception e) {
            throw new RuntimeException("Init exception");
        }
    }

    private int getAnnotationOrder(Method method) {
        return method.getAnnotation(AppComponent.class).order();
    }


    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponents.stream().filter(o -> componentClass.isAssignableFrom(o.getClass())).
                findFirst().orElseThrow(() -> new RuntimeException("Component not found"));
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return Optional.ofNullable((C) appComponentsByName.get(componentName))
                .orElseThrow(() -> new RuntimeException("Component not found"));
    }
}
