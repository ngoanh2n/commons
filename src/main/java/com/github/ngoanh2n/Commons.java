package com.github.ngoanh2n;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import javax.annotation.Nonnull;
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
        "SpellCheckingInspection",
        "ResultOfMethodCallIgnored",
})
public class Commons {
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
}
