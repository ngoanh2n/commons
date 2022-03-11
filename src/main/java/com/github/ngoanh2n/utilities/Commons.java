package com.github.ngoanh2n.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

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
}
