package com.github.ngoanh2n.utilities;

import com.github.ngoanh2n.Prop;
import com.google.common.base.Preconditions;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nonnull;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Utilities for finding and reading Java resources
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-16
 */
public class Resources {

    /**
     * {@code true}:  Look for the resources on the classpath:
     * [PROJECT]/out/test/resources/ or [PROJECT]/out/production/resources/
     * <br>
     * {@code false}: Look for the resources in root location
     * [PROJECT]/src/test/resources/ or [PROJECT]/main/test/resources/
     */
    public static final Prop<Boolean> findOnClasspath = new Prop<>("ngoanh2n.findOnClasspath", Boolean.class, true);

    /**
     * Check whether the resource exists
     *
     * @param resourceName is the name of resource <br>
     *                     e.g. com/foo/File.properties
     * @return {@code true} if and only if the file exists; {@code false} otherwise
     */
    public static boolean exists(@Nonnull final String resourceName) {
        return url(resourceName) != null;
    }

    /**
     * Get the path of resource
     *
     * @param resourceName is the name of resource <br>
     *                     e.g. com/foo/File.properties
     * @return {@linkplain Path} of resource if the file exists; {@linkplain ResourceNotFound } otherwise
     */
    public static Path path(@Nonnull final String resourceName) {
        URL resource = url(resourceName);
        if (resource != null) {
            return Paths.get(resource.getPath());
        } else {
            throw new ResourceNotFound(resourceName);
        }
    }

    /**
     * Get the resource file
     *
     * @param resourceName is the name of resource <br>
     *                     e.g. com/foo/File.properties
     * @return {@linkplain File} of resource if the file exists; {@linkplain ResourceNotFound } otherwise
     */
    public static File file(@Nonnull final String resourceName) {
        return path(resourceName).toFile();
    }

    /**
     * Get the resource file as {@linkplain String}
     *
     * @param resourceName is the name of resource <br>
     *                     e.g. com/foo/File.properties
     * @return {@linkplain String} if the file exists; {@linkplain ResourceNotFound } otherwise
     */
    public static String fileToString(@Nonnull final String resourceName) {
        return fileToString(resourceName, Charset.defaultCharset());
    }

    /**
     * Get the resource file as {@linkplain String}
     *
     * @param resourceName is the name of resource <br>
     *                     e.g. com/foo/File.properties
     * @param charset      the charset to use, null means platform default
     * @return {@linkplain String} if the file exists; {@linkplain ResourceNotFound } otherwise
     */
    public static String fileToString(@Nonnull final String resourceName, @Nonnull final Charset charset) {
        InputStream is = inputStream(resourceName);
        try {
            return IOUtils.toString(is, charset);
        } catch (IOException e) {
            throw new ResourceNotFound(e);
        }
    }

    /**
     * Get the resource file as {@linkplain InputStream}
     *
     * @param resourceName is the name of resource <br>
     *                     e.g. com/foo/File.properties
     * @return {@linkplain InputStream} if the file exists; {@linkplain ResourceNotFound } otherwise
     */
    public static InputStream inputStream(@Nonnull final String resourceName) {
        try {
            return new FileInputStream(file(resourceName));
        } catch (FileNotFoundException e) {
            throw new ResourceNotFound(resourceName);
        }
    }

    private static URL url(final String resourceName) {
        validResourceName(resourceName);
        if (!findOnClasspath.getValue()) {
            AtomicReference<URL> referenceUrl = new AtomicReference<>();
            referenceUrl.set(url(resourceName, "test"));

            if (referenceUrl.get() != null) {
                return referenceUrl.get();
            } else {
                referenceUrl.set(url(resourceName, "main"));
                if (referenceUrl.get() == null) {
                    return null;
                } else {
                    return referenceUrl.get();
                }
            }
        } else {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            return classLoader.getResource(resourceName);
        }
    }

    private static URL url(String resourceName, String src) {
        Path resources = Paths.get("src", src, "resources");
        try {
            Path resourcePath = Paths.get("", resourceName.split("/"));
            return resources.resolve(resourcePath).toFile().toURI().toURL();
        } catch (MalformedURLException e) {
            // Can't happen
            throw new ResourceNotFound(e);
        }
    }

    private static void validResourceName(final String resourceName) {
        Preconditions.checkNotNull(resourceName, "Resource name cannot be null");
        Preconditions.checkArgument(resourceName.length() > 0, "Resource name cannot be empty");
    }
}
