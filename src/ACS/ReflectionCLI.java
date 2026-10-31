package ACS;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.function.Function;

public class ReflectionCLI {
    static void main(String[] args) throws MalformedURLException, ClassNotFoundException {

        if (args.length != 1) {
            IO.println("Error, arguments length != 1");
            return;
        }

        File classFile = new File(args[0]);
        String className = classFile.getName().replace(".class", "");
        File parentDir = classFile.getParentFile();
        URLClassLoader classLoader = new URLClassLoader(new URL[]{ parentDir.toURI().toURL() });
        Class<?> loadedClass = classLoader. loadClass(className);
        analyzeClass(loadedClass);
    }

    private static String getParameterString(Class<?>[] paramTypes) {
        if (paramTypes.length == 0) return "";
        StringBuilder params = new StringBuilder();
        for (Class<?> param : paramTypes)
            params.append(param.getSimpleName()).append(",");
        return params.toString();
    }

    public static Object[] createFieldRow(Field field) {
        return new Object[] {
                field,
                Modifier.toString(field.getModifiers()),
                field.getName(),
                "Type: " + field.getType().getSimpleName(),
                "Generic Type: " + field.getGenericType()
        };
    }

    public static Object[] createConstructorRow(Constructor<?> constructor) {
        return new Object[] {
                "Constructor",
                Modifier.toString(constructor.getModifiers()),
                constructor.getDeclaringClass().getSimpleName(),
                "(" + getParameterString(constructor.getParameterTypes()) + ")"
        };
    }

    public static Object[] createMethodRow(Method method) {
        return new Object[] {
                "Method",
                Modifier.toString(method.getModifiers()),
                method.getName(),
                "(" + method.getReturnType().getSimpleName() + getParameterString(method.getParameterTypes()) + ")"
        };
    }

    public static Object[] createInterfaceRow(Class<?> iface) {
        return new Object[] {
                "Interface",
                "",
                iface.getSimpleName(),
                iface.getName()
        };
    }

    public static <T> void showReflectionElements(
                T[] elements,
                String sectionTitle,
                Function<T, Object[]> rowCreator
            ) {
        if (elements == null || elements.length == 0)
            IO.println("Error table is empty or equals null!");
        IO.println(sectionTitle);
        for (T element : elements)
            IO.println(Arrays.toString(rowCreator.apply(element)));
        IO.println();
    }

    public static void analyzeClass(Class<?> clazz) {
        showReflectionElements(clazz.getDeclaredFields(), "FIELD", ReflectionCLI::createFieldRow);
        showReflectionElements(clazz.getDeclaredConstructors(), "CONSTRUCTORS", ReflectionCLI::createConstructorRow);
        showReflectionElements(clazz.getDeclaredMethods(), "METHODS", ReflectionCLI::createMethodRow);
        showReflectionElements(clazz.getInterfaces(), "INTERFACES", ReflectionCLI::createInterfaceRow);
    }
}
