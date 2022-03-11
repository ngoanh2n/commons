package com.github.ngoanh2n;

import com.google.common.base.Preconditions;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nonnull;
import java.io.*;
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
     * Get the resource file
     *
     * @param resourceName is the name of resource <br>
     *                     e.g. com/foo/File.properties
     * @return {@linkplain File} of resource if the file exists; {@linkplain ResourceNotFound } otherwise
     */
    public static File getFile(@Nonnull final String resourceName) {
        return file(resourceName);
    }

    /**
     * Get the path of resource
     *
     * @param resourceName is the name of resource <br>
     *                     e.g. com/foo/File.properties
     * @return {@linkplain Path} of resource if the file exists; {@linkplain ResourceNotFound } otherwise
     */
    public static Path getPath(@Nonnull final String resourceName) {
        return getFile(resourceName).toPath();
    }

    /**
     * Get the resource file as {@linkplain String}
     *
     * @param resourceName is the name of resource <br>
     *                     e.g. com/foo/File.properties
     * @return {@linkplain String} if the file exists; {@linkplain ResourceNotFound } otherwise
     */
    public static String getContent(@Nonnull final String resourceName) {
        return getContent(resourceName, Charset.defaultCharset());
    }

    /**
     * Get the resource file as {@linkplain String}
     *
     * @param resourceName is the name of resource <br>
     *                     e.g. com/foo/File.properties
     * @param charset      the charset to use, null means platform default
     * @return {@linkplain String} if the file exists; {@linkplain ResourceNotFound } otherwise
     */
    public static String getContent(@Nonnull final String resourceName, @Nonnull final Charset charset) {
        try {
            InputStream is = getInputStream(resourceName);
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
    public static InputStream getInputStream(@Nonnull final String resourceName) {
        try {
            return new FileInputStream(getFile(resourceName));
        } catch (FileNotFoundException e) {
            throw new ResourceNotFound(resourceName);
        }
    }

    private static File file(final String name) {
        File file;
        validate(name);

        if (findOnClasspath.getValue()) {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL url = classLoader.getResource(name);
            file = (url == null) ? null : new File(url.getFile());
        } else {
            AtomicReference<File> refFile = new AtomicReference<>();
            refFile.set(file(name, "test"));

            if (refFile.get() != null) {
                file = refFile.get();
            } else {
                refFile.set(file(name, "main"));
                if (refFile.get() == null) {
                    file = null;
                } else {
                    file = refFile.get();
                }
            }
        }

        if (file != null) {
            File resourceFile = new File(file.getPath());
            if (resourceFile.exists()) return resourceFile;
        }
        throw new ResourceNotFound(name);
    }

    private static File file(String name, String src) {
        Path resourcesPath = Paths.get("src", src, "resources");
        Path resourcePath = Paths.get("", name.split("/"));
        return resourcesPath.resolve(resourcePath).toFile();
    }

    private static void validate(String name) {
        Preconditions.checkNotNull(name, "Resource name cannot be null");
        Preconditions.checkArgument(name.length() > 0, "Resource name cannot be empty");
    }
}
