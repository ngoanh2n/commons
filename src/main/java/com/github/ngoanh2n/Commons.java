package com.github.ngoanh2n;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import javax.annotation.Nonnull;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * Common helpers and utilities
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-16
 */
public class Commons {

    public static String timeStamp() {
        //noinspection SpellCheckingInspection
        return new SimpleDateFormat("yyyyMMdd.HHmmss.SSS").format(new Date());
    }

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
}
