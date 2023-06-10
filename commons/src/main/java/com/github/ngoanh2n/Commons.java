package com.github.ngoanh2n;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Common helpers.
 *
 * @author ngoanh2n
 */
@CanIgnoreReturnValue
public final class Commons {
    private static final Logger log = LoggerFactory.getLogger(Commons.class);

    private Commons() { /* No implementation necessary */ }

    //-------------------------------------------------------------------------------//

    /**
     * Create a timestamp.
     *
     * @return timestamp as string.
     */
    public static String timestamp() {
        Format dateFormat = new SimpleDateFormat("yyyyMMdd.HHmmss.SSS");
        return dateFormat.format(new Date());
    }

    /**
     * Create recursively directory from {@link File}.
     *
     * @param file is directory as File.
     * @return directory as a file.
     */
    public static File createDir(@Nonnull File file) {
        return createDir(file.toPath()).toFile();
    }

    /**
     * Create recursively directory from {@link Path}.
     *
     * @param path is directory as Path.
     * @return directory as a path.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static Path createDir(@Nonnull Path path) {
        Iterator<Path> elements = path.iterator();
        Path parentElement = Paths.get("");

        while (elements.hasNext()) {
            parentElement = parentElement.resolve(elements.next());
            parentElement.toFile().mkdirs();
        }
        return path;
    }

    /**
     * Get relative path of file against to current user directory.
     *
     * @param file to get relative path.
     * @return relative path.
     */
    public static File getRelative(@Nonnull File file) {
        return getRelative(file.toPath()).toFile();
    }

    /**
     * Get relative path of path against to current user directory.
     *
     * @param path to get relative path.
     * @return relative path.
     */
    public static Path getRelative(@Nonnull Path path) {
        String userDir = System.getProperty("user.dir");
        Path userPath = Paths.get(userDir);
        try {
            return userPath.relativize(path);
        } catch (IllegalArgumentException ignored) {
            return path;
        }
    }

    /**
     * Write {@link Properties} to file.
     *
     * @param file  to be stored.
     * @param props to be written.
     * @return output file.
     */
    public static File writeProps(Properties props, File file) {
        createDir(file.toPath());
        String msg = String.format("Write Properties to %s", getRelative(file));

        try (OutputStream os = Files.newOutputStream(file.toPath())) {
            props.store(os, null);
        } catch (IOException e) {
            log.error(msg);
            throw new RuntimeError(msg, e);
        }
        log.debug(msg);
        return file;
    }

    /**
     * Read {@link Properties} from given Java resource name.
     *
     * @param resourceName Java resource name to read.
     * @return {@link Properties} object.
     */
    public static Properties readProps(@Nonnull String resourceName) {
        File file = Resources.getFile(resourceName);
        return readProps(file, "UTF-8");
    }

    /**
     * Read {@link Properties} from given properties file.
     *
     * @param file    to read.
     * @param charset The name of a supported charset.
     * @return {@link Properties} object.
     */
    public static Properties readProps(@Nonnull File file, String charset) {
        Properties props = new Properties();
        String path = getRelative(file).getPath().replace('\\', '/');
        String msg = "Read Properties " + path;

        try (InputStream is = Files.newInputStream(file.toPath())) {
            InputStreamReader isr = new InputStreamReader(is, charset);
            props.load(isr);
        } catch (IOException e) {
            log.error(msg);
            throw new RuntimeError(msg, e);
        }
        log.debug(msg);
        return props;
    }

    /**
     * Get the charset of a file. <br>
     * Method to mark {@link UniversalDetector} for reusing.
     *
     * @param file The file to check charset for.
     * @return The charset of the file, null when could not be determined.
     * @throws IOException if some IO error occurs.
     */
    public static String detectCharset(File file) throws IOException {
        return UniversalDetector.detectCharset(file.toPath());
    }

    /**
     * Read value of the {@link Field}. Its parents will be considered. <br>
     * <ul>
     *     <li>{@code private Type aField}
     *     <li>{@code private final Type aField}
     * </ul>
     *
     * @param <T>    Type of result will be returned.
     * @param target The object instance to reflect, must not be {@code null}.
     * @param name   The field name to obtain.
     * @return The field value.
     */
    @SuppressWarnings("unchecked")
    public static <T> T readField(Object target, String name) {
        String msg = msgFieldAccess(target.getClass(), name, "Read");
        Field[] fields = FieldUtils.getAllFields(target.getClass());

        for (Field field : fields) {
            if (field.getName().equals(name)) {
                field.setAccessible(true);
                try {
                    return (T) field.get(target);
                } catch (IllegalAccessException e) {
                    log.error(msg);
                    throw new RuntimeError(msg, e);
                }
            }
        }
        log.error(msg);
        throw new RuntimeError(msg);
    }

    /**
     * Read value of the {@link Field}. Its parents will be considered. <br>
     * <ul>
     *     <li>{@code private static final Type aField}
     * </ul>
     *
     * @param <T>    Type of result will be returned.
     * @param target The object class to reflect, must not be {@code null}.
     * @param name   The field name to obtain.
     * @return The field value.
     */
    @SuppressWarnings("unchecked")
    public static <T> T readField(Class<?> target, String name) {
        String msg = msgFieldAccess(target, name, "Read");
        Field[] fields = FieldUtils.getAllFields(target);

        for (Field field : fields) {
            if (field.getName().equals(name)) {
                field.setAccessible(true);
                try {
                    return (T) field.get(target);
                } catch (IllegalAccessException e) {
                    log.error(msg);
                    throw new RuntimeError(msg, e);
                }
            }
        }
        log.error(msg);
        throw new RuntimeError(msg);
    }

    /**
     * Write value to the field with modifiers:
     * <ul>
     *     <li>Target object has fields:<pre>
     *         {@code private Type aField}
     *         {@code private final Type aField}</pre>
     *     <li>Target object's parents have fields:<pre>
     *         {@code private Type aField}
     *         {@code private final Type aField}
     *         {@code private static Type aField}
     *         {@code private static final Type aField}</pre>
     * </ul>
     *
     * @param target The object instance to reflect, must not be {@code null}.
     * @param name   The field name to obtain.
     * @param value  The new value for the field of object being modified.
     */
    public static void writeField(Object target, String name, Object value) {
        String msg = msgFieldAccess(target.getClass(), name, "Write");
        List<Field> fieldList = Arrays.stream(FieldUtils.getAllFields(target.getClass()))
                .filter(field -> field.getName().equals(name))
                .collect(Collectors.toList());

        for (Field field : fieldList) {
            field.setAccessible(true);
            try {
                if (!Modifier.isStatic(field.getModifiers())) {
                    Object fValue = field.get(target);
                    if (fValue.hashCode() != value.hashCode()) {
                        field.set(target, value);
                    }
                } else {
                    Field modifiers = FieldUtils.getField(Field.class, "modifiers", true);
                    modifiers.setAccessible(true);
                    modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                    field.set(null, value);
                }
            } catch (IllegalAccessException e) {
                log.error(msg);
                throw new RuntimeError(msg, e);
            }
        }
    }

    /**
     * Write value to the field with modifiers:
     * <ul>
     *     <li>Target object has fields:<pre>
     *         {@code private static Type aField}
     *         {@code private static final Type aField}</pre>
     *     <li>Target object's parents have fields:<pre>
     *         {@code private static Type aField}
     *         {@code private static final Type aField}</pre>
     * </ul>
     *
     * @param target The object class to reflect, must not be {@code null}.
     * @param name   The field name to obtain.
     * @param value  The new value for the field of object being modified.
     */
    public static void writeField(Class<?> target, String name, Object value) {
        String msg = msgFieldAccess(target, name, "Write");
        List<Field> fields = Arrays.stream(FieldUtils.getAllFields(target))
                .filter(field -> field.getName().equals(name))
                .collect(Collectors.toList());

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (!Modifier.isStatic(field.getModifiers())) {
                    boolean override = readField(field, "override");
                    if (!override) {
                        Object fValue = field.get(target);
                        if (fValue.hashCode() != value.hashCode()) {
                            field.set(target, value);
                        }
                    }
                } else {
                    Field modifiers = FieldUtils.getField(Field.class, "modifiers", true);
                    modifiers.setAccessible(true);
                    modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                    field.set(null, value);
                }
            } catch (IllegalAccessException e) {
                log.error(msg);
                throw new RuntimeError(msg, e);
            }
        }
    }

    /**
     * Convert String value to a specific object.
     *
     * @param type  The Class object which to return an object.
     * @param value String value to convert.
     * @param <T>   The type of target object.
     * @return The target object.
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertValue(Class<T> type, String value) {
        if (!type.isEnum()) {
            if (type == URL.class) {
                try {
                    return (T) new URL(value);
                } catch (Exception e) {
                    throw new RuntimeError(e);
                }
            }
            if (type == String.class) return (T) value;
            if (type == Byte.class) return (T) Byte.valueOf(value);
            if (type == Long.class) return (T) Long.valueOf(value);
            if (type == Short.class) return (T) Short.valueOf(value);
            if (type == Float.class) return (T) Float.valueOf(value);
            if (type == Double.class) return (T) Double.valueOf(value);
            if (type == Integer.class) return (T) Integer.valueOf(value);
            if (type == Boolean.class) return (T) Boolean.valueOf(value);
            throw new RuntimeError("Type " + type.getTypeName() + " cannot be parsed");
        }
        return Commons.buildEnum(type, value).orElse(null);
    }

    /**
     * Build enum from enum class and enum constant name.
     *
     * @param type The Class object of the enum type from which to return a constant.
     * @param name The name of enum constant to return, exactly as declared in its enum declaration.
     * @param <T>  The type of enum object.
     * @return Optional of the enum constant of the specified enum type with the specified name.
     */
    @SuppressWarnings("unchecked")
    public static <T> Optional<T> buildEnum(Class<T> type, String name) {
        for (Field field : type.getDeclaredFields()) {
            if (field.isEnumConstant() && field.getName().equals(name)) {
                try {
                    Method valueOfMethod = type.getDeclaredMethod("valueOf", String.class);
                    return (Optional<T>) Optional.of(valueOfMethod.invoke(null, name));
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    return Optional.empty();
                }
            }
        }
        return Optional.empty();
    }

    //-------------------------------------------------------------------------------//

    private static String msgFieldAccess(Class<?> target, String name, String action) {
        return String.format("%s field %s.%s", action, target.getName(), name);
    }
}
