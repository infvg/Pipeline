package org.pipelinemc.Pipeline.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {
	 boolean ignoreCancelled() default false;
	 EventPriority priority() default EventPriority.NORMAL;
}
