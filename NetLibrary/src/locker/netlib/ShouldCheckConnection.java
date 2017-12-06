package locker.netlib;

import java.lang.annotation.*;

/**
 * Annotation that indicates that subclasses should check the connection
 * state before performing some operations.
 *
 * @author Marco Cipriani
 * @version 0.1
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
@Documented
@SuppressWarnings("unused")
@Deprecated
public @interface ShouldCheckConnection {

}