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
 * Find and read Java resources.
 * <ul>
 *     <li>{@code File file = Resources.getFile("file.json")}</li>
 *     <li>{@code Path path = Resources.getPath("file.json")}</li>
 *     <li>{@code String content = Resources.getContent("file.yml")}</li>
 *     <li>{@code InputStream is = Resources.getInputStream("file.png")}</li>
 * </ul>
 *
 * <b>System Property</b>
 * <ul>
 *     <li>{@code ngoanh2n.findResourceOnClasspath}<br>
 *          Indicate to find the resource file on classpath. Default to {@code true}.
 *          <ul>
 *              <li>true: Look for the resources on the classpath
 *                  <pre>{@code
 *                      {project}/out/test/resources/
 *                      {project}/out/production/resources/
 *                  }</pre>
 *              </li>
 *              <li>false: Look for the resources in root location
 *                  <pre>{@code
 *                      {project}/src/test/resources/
 *                      {project}/src/main/resources/
 *                  }</pre>
 *              </li>
 *          </ul>
 *     </li>
 * </ul>
 *
 * <em>Repository:</em>
 * <ul>
 *     <li><em>GitHub: <a href="https://github.com/ngoanh2n/commons">ngoanh2n/commons</a></em></li>
 *     <li><em>Maven: <a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/commons">com.github.ngoanh2n:commons</a></em></li>
 * </ul>
 *
 * @author ngoanh2n
 * @since 2019
 */
public final class Resources {
    private static final Logger log = LoggerFactory.getLogger(Resources.class);
    private static final Property<Boolean> findOnClasspath = Property.ofBoolean("ngoanh2n.findResourceOnClasspath", true);

    private Resources() { /**/ }

    //-------------------------------------------------------------------------------//

    /**
     * Get the resource file.
     *
     * @param resourceName is the name of resource.<br>
     *                     e.g. com/foo/File.properties
     * @return {@link File} of resource if the file exists; {@link RuntimeError} otherwise.
     */
    public static File getFile(@Nonnull String resourceName) {
        return new Target(resourceName).getFile();
    }

    /**
     * Get the path of resource.
     *
     * @param resourceName is the name of resource.<br>
     *                     e.g. com/foo/File.properties
     * @return {@link Path} of resource if the file exists; {@link RuntimeError} otherwise.
     */
    public static Path getPath(@Nonnull String resourceName) {
        Target target = new Target(resourceName);
        if (target.insideJar()) {
            String path = target.getURL().toString().replace("jar:file:/", "");
            return Paths.get(path);
        }
        return getFile(resourceName).toPath();
    }

    /**
     * Get the resource file as {@link InputStream}.
     *
     * @param resourceName is the name of resource.<br>
     *                     e.g. com/foo/File.properties
     * @return {@link InputStream} if the file exists; {@link RuntimeError} otherwise.
     */
    public static InputStream getInputStream(@Nonnull String resourceName) {
        try {
            Target target = new Target(resourceName);
            if (target.insideJar()) {
                return target.getInputStream();
            }
            return new FileInputStream(getFile(resourceName));
        } catch (FileNotFoundException e) {
            throw new RuntimeError(String.format("Resource [%s] not found", resourceName));
        }
    }

    /**
     * Get the resource file as {@link String}.
     *
     * @param resourceName is the name of resource.<br>
     *                     e.g. com/foo/File.properties
     * @return {@link String} if the file exists; {@link RuntimeError} otherwise.
     */
    public static String getContent(@Nonnull String resourceName) {
        return getContent(resourceName, Charset.defaultCharset());
    }

    /**
     * Get the resource file as {@link String}.
     *
     * @param resourceName is the name of resource.<br>
     *                     e.g. com/foo/File.properties
     * @param charset      the charset to use, null means platform default.
     * @return {@link String} if the file exists; {@link RuntimeError} otherwise.
     */
    public static String getContent(@Nonnull String resourceName, @Nonnull Charset charset) {
        try {
            InputStream is = getInputStream(resourceName);
            return IOUtils.toString(is, charset);
        } catch (IOException e) {
            throw new RuntimeError(e);
        }
    }

    //===============================================================================//

    private static final class Target {
        private final String name;
        private URL url;

        private Target(String name) {
            this.name = name;
            this.url = getURL();
            this.validateResourceName();
        }

        private boolean insideJar() {
            if (url == null) {
                return false;
            }
            return url.toString().contains(".jar!");
        }

        private File getFile() {
            if (insideJar()) {
                return new File(url.getFile());
            } else {
                File file = findResource();
                String msg = String.format("Find resource %s", name);

                if (file != null) {
                    if (file.exists()) {
                        log.debug(msg);
                        return file;
                    }
                }
                log.error(msg);
                throw new RuntimeError(msg);
            }
        }

        private URL getURL() {
            if (url == null) {
                ClassLoader clazzLoader = Thread.currentThread().getContextClassLoader();
                url = clazzLoader.getResource(name);
            }
            return url;
        }

        private InputStream getInputStream() {
            ClassLoader clazzLoader = Thread.currentThread().getContextClassLoader();
            return clazzLoader.getResourceAsStream(name);
        }

        private File findResource() {
            if (findOnClasspath.getValue()) {
                return findResourceOnClassPath(name);
            } else {
                return findResourceInRootLocation(name);
            }
        }

        private File findResourceOnClassPath(String name) {
            ClassLoader clazzLoader = Thread.currentThread().getContextClassLoader();
            URL url = clazzLoader.getResource(name);
            return (url == null) ? null : new File(url.getFile());
        }

        private File findResourceInRootLocation(String name) {
            AtomicReference<File> arFile = new AtomicReference<>();
            arFile.set(findResourceInRootLocation(name, "test"));

            if (arFile.get() != null) {
                return arFile.get();
            } else {
                arFile.set(findResourceInRootLocation(name, "main"));
                if (arFile.get() == null) {
                    return null;
                } else {
                    return arFile.get();
                }
            }
        }

        private File findResourceInRootLocation(String name, String src) {
            Path resourcesPath = Paths.get("src", src, "resources");
            Path resourcePath = Paths.get("", name.split("/"));
            return resourcesPath.resolve(resourcePath).toFile();
        }

        private void validateResourceName() {
            Preconditions.checkNotNull(name, "Resource name cannot be null");
            Preconditions.checkArgument(name.trim().length() > 0, "Resource name cannot be empty");
        }
    }
}
