package io.github.ngoanh2n.commons;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.mozilla.universalchardet.UniversalDetector;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

/**
 * Utilities relate to {@linkplain File}, {@linkplain Path}
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-16
 */
public class FileHelper {

    @CanIgnoreReturnValue
    public static Path createDirectory(@Nonnull Path location) {
        Iterator<Path> elements = location.iterator();
        Path parentElement = Paths.get("");

        while (elements.hasNext()) {
            parentElement = parentElement.resolve(elements.next());
            //noinspection ResultOfMethodCallIgnored
            parentElement.toFile().mkdirs();
        }
        return location;
    }

    public static String charsetOf(@Nonnull File file) throws IOException {
        return UniversalDetector.detectCharset(file);
    }
}
