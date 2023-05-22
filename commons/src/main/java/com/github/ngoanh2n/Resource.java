package com.github.ngoanh2n;

import com.google.common.base.Preconditions;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Utilities for finding and reading Java resources.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public final class Resource {
    /**
     * {@code true}:  Look for the resources on the classpath:
     * [PROJECT]/out/test/resources/ or [PROJECT]/out/production/resources/
     * <br>
     * {@code false}: Look for the resources in root location
     * [PROJECT]/src/test/resources/ or [PROJECT]/main/test/resources/
     */
    public static final Property<Boolean> findOnClasspath = Property.bool("ngoanh2n.findResourceOnClasspath", true);

    private static final Logger log = LoggerFactory.getLogger(Resource.class);

    private Resource() { /* No implementation necessary */ }

    //-------------------------------------------------------------------------------//

    /**
     * Get the resource file.
     *
     * @param resourceName is the name of resource. <br>
     *                     e.g. com/foo/File.properties
     * @return {@linkplain File} of resource if the file exists; {@linkplain RuntimeError} otherwise.
     */
    public static File getFile(@Nonnull String resourceName) {
        return findResource(resourceName);
    }

    /**
     * Gets the path of resource.
     *
     * @param resourceName is the name of resource. <br>
     *                     e.g. com/foo/File.properties
     * @return {@linkplain Path} of resource if the file exists; {@linkplain RuntimeError} otherwise.
     */
    public static Path getPath(@Nonnull String resourceName) {
        return getFile(resourceName).toPath();
    }

    /**
     * Gets the resource file as {@linkplain InputStream}.
     *
     * @param resourceName is the name of resource. <br>
     *                     e.g. com/foo/File.properties
     * @return {@linkplain InputStream} if the file exists; {@linkplain RuntimeError} otherwise.
     */
    public static InputStream getInputStream(@Nonnull String resourceName) {
        try {
            return new FileInputStream(getFile(resourceName));
        } catch (FileNotFoundException e) {
            throw new RuntimeError(String.format("Resource [%s] not found", resourceName));
        }
    }

    /**
     * Gets the resource file as {@linkplain String}.
     *
     * @param resourceName is the name of resource. <br>
     *                     e.g. com/foo/File.properties
     * @return {@linkplain String} if the file exists; {@linkplain RuntimeError} otherwise.
     */
    public static String getContent(@Nonnull String resourceName) {
        return getContent(resourceName, Charset.defaultCharset());
    }

    /**
     * Gets the resource file as {@linkplain String}.
     *
     * @param resourceName is the name of resource. <br>
     *                     e.g. com/foo/File.properties
     * @param charset      the charset to use, null means platform default.
     * @return {@linkplain String} if the file exists; {@linkplain RuntimeError} otherwise.
     */
    public static String getContent(@Nonnull String resourceName, @Nonnull Charset charset) {
        try {
            InputStream is = getInputStream(resourceName);
            return IOUtils.toString(is, charset);
        } catch (IOException e) {
            throw new RuntimeError(e);
        }
    }

    //-------------------------------------------------------------------------------//

    private static File findResource(String name) {
        File file;
        validateResourceName(name);
        String msg = String.format("Find resource %s", name);

        if (findOnClasspath.getValue()) {
            file = findResourceOnClassPath(name);
        } else {
            file = findResourceInRootLocation(name);
        }

        if (file != null) {
            File resourceFile = new File(file.getPath());
            if (resourceFile.exists()) {
                log.debug(msg);
                return resourceFile;
            }
        }

        log.error(msg);
        throw new RuntimeError(msg);
    }

    private static File findResourceOnClassPath(String name) {
        ClassLoader clazzLoader = Thread.currentThread().getContextClassLoader();
        URL url = clazzLoader.getResource(name);
        return (url == null) ? null : new File(url.getFile());
    }

    private static File findResourceInRootLocation(String name) {
        File file;
        AtomicReference<File> refFile = new AtomicReference<>();
        refFile.set(findResourceInRootLocation(name, "test"));

        if (refFile.get() != null) {
            file = refFile.get();
        } else {
            refFile.set(findResourceInRootLocation(name, "main"));
            if (refFile.get() == null) {
                file = null;
            } else {
                file = refFile.get();
            }
        }
        return file;
    }

    private static File findResourceInRootLocation(String name, String src) {
        Path resourcesPath = Paths.get("src", src, "resources");
        Path resourcePath = Paths.get("", name.split("/"));
        return resourcesPath.resolve(resourcePath).toFile();
    }

    private static void validateResourceName(String value) {
        Preconditions.checkNotNull(value, "Resource name cannot be null");
        Preconditions.checkArgument(value.trim().length() > 0, "Resource name cannot be empty");
    }
}
