package com.github.ngoanh2n;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import javax.annotation.Nonnull;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * Common helpers
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-16
 */
@SuppressWarnings({
        "unchecked",
        "ResultOfMethodCallIgnored"
})
public final class Commons {
    public static String timeStamp() {
        Format dateFormat = new SimpleDateFormat("yyyyMMdd.HHmmss.SSS");
        return dateFormat.format(new Date());
    }

    @CanIgnoreReturnValue
    public static Path createDir(@Nonnull Path location) {
        Iterator<Path> elements = location.iterator();
        Path parentElement = Paths.get("");

        while (elements.hasNext()) {
            parentElement = parentElement.resolve(elements.next());
            parentElement.toFile().mkdirs();
        }
        return location;
    }

    public static Path getRelative(@Nonnull Path path) {
        return getRelative(path.toFile());
    }

    public static Path getRelative(@Nonnull File file) {
        String userDir = System.getProperty("user.dir");
        Path userPath = Paths.get(userDir);
        try {
            return userPath.relativize(file.toPath());
        } catch (IllegalArgumentException ignored) {
            return file.toPath();
        }
    }

    public static <T> T getPrivateValue(Class<?> clazz, Object clazzInstance, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(clazzInstance);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            String msg = "Error of private value [%s, %s]";
            throw new RuntimeError(String.format(msg, clazz.getSimpleName(), fieldName));
        }
    }
}
