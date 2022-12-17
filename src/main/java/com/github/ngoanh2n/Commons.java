package com.github.ngoanh2n;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

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
@CanIgnoreReturnValue
public final class Commons {
    private static final Logger LOGGER = LoggerFactory.getLogger(Commons.class);

    public static String timestamp() {
        Format dateFormat = new SimpleDateFormat("yyyyMMdd.HHmmss.SSS");
        return dateFormat.format(new Date());
    }

    public static File createDir(@Nonnull File file) {
        return createDir(file.toPath()).toFile();
    }

    public static Path createDir(@Nonnull Path path) {
        String extension = FilenameUtils.getExtension(path.toString());
        if (!extension.isEmpty()) {
            path = path.getParent();
        }

        Iterator<Path> elements = path.iterator();
        Path parentElement = Paths.get("");

        while (elements.hasNext()) {
            parentElement = parentElement.resolve(elements.next());
            parentElement.toFile().mkdirs();
        }
        return path;
    }

    public static File getRelative(@Nonnull File file) {
        return getRelative(file.toPath()).toFile();
    }

    public static Path getRelative(@Nonnull Path path) {
        String userDir = System.getProperty("user.dir");
        Path userPath = Paths.get(userDir);
        try {
            return userPath.relativize(path);
        } catch (IllegalArgumentException ignored) {
            return path;
        }
    }

    public static File writeProps(Properties props, File file) {
        createDir(file.toPath());
        try (OutputStream os = Files.newOutputStream(file.toPath())) {
            props.store(os, null);
        } catch (IOException e) {
            String msg = "Path not found to create file: %s";
            LOGGER.error(String.format(msg, file.getAbsolutePath()), e);
        }
        return file;
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
