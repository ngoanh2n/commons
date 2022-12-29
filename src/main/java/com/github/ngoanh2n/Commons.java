package com.github.ngoanh2n;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
 * Common helpers.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-16
 */
@SuppressWarnings({"unchecked", "ResultOfMethodCallIgnored"})
@CanIgnoreReturnValue
public final class Commons {
    /**
     * Creates a timestamp.
     *
     * @return timestamp as string.
     */
    public static String timestamp() {
        Format dateFormat = new SimpleDateFormat("yyyyMMdd.HHmmss.SSS");
        return dateFormat.format(new Date());
    }

    /**
     * Creates recursively directory from {@linkplain File}.
     *
     * @param file is directory as File.
     * @return directory as a file.
     */
    public static File createDir(@Nonnull File file) {
        return createDir(file.toPath()).toFile();
    }

    /**
     * Creates recursively directory from {@linkplain Path}.
     *
     * @param path is directory as Path.
     * @return directory as a path.
     */
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
     * Gets relative path of file against to current user directory.
     *
     * @param file to get relative path.
     * @return relative path.
     */
    public static File getRelative(@Nonnull File file) {
        return getRelative(file.toPath()).toFile();
    }

    /**
     * Gets relative path of path against to current user directory.
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
     * Writes {@linkplain Properties} to file.
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
            LOGGER.error(msg);
            throw new RuntimeError(msg, e);
        }
        LOGGER.debug(msg);
        return file;
    }

    /**
     * Reads {@linkplain Properties} from given Java resource name.
     *
     * @param resourceName Java resource name to read.
     * @return {@linkplain Properties} object.
     */
    public static Properties readProps(@Nonnull String resourceName) {
        File file = Resource.getFile(resourceName);
        return readProps(file);
    }

    /**
     * Reads {@linkplain Properties} from given properties file.
     *
     * @param file to read.
     * @return {@linkplain Properties} object.
     */
    public static Properties readProps(@Nonnull File file) {
        Properties props = new Properties();
        String msg = String.format("Read Properties from %s", getRelative(file));

        try (InputStream is = Files.newInputStream(file.toPath())) {
            props.load(is);
        } catch (IOException e) {
            LOGGER.error(msg);
            throw new RuntimeError(msg, e);
        }
        LOGGER.debug(msg);
        return props;
    }

    //-------------------------------------------------------------------------------//

    /**
     * Gets the charset of a file. <br>
     * Method to mark {@linkplain UniversalDetector} for reusing.
     *
     * @param file The file to check charset for.
     * @return The charset of the file, null when could not be determined.
     * @throws IOException if some IO error occurs.
     */
    public static String detectCharset(File file) throws IOException {
        return UniversalDetector.detectCharset(file.toPath());
    }

    /**
     * Reads the named {@link Field}. Superclasses will be considered. <br>
     * Method to mark {@linkplain FieldUtils} for reusing.
     *
     * @param <T>       Type of result will be returned.
     * @param object    The object to reflect, must not be {@code null}.
     * @param fieldName The field name to obtain.
     * @return The field value.
     */
    public static <T> T readField(Object object, String fieldName) {
        try {
            return (T) FieldUtils.readField(object, fieldName, true);
        } catch (IllegalAccessException e) {
            String clazzName = object.getClass().getName();
            String msg = String.format("Read field %s in class %s", fieldName, clazzName);
            LOGGER.error(msg);
            throw new RuntimeError(msg, e);
        }
    }

    //-------------------------------------------------------------------------------//

    private static final Logger LOGGER = LoggerFactory.getLogger(Commons.class);

    private Commons() { /* No implementation necessary */ }
}
