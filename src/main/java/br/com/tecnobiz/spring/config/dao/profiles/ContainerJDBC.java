package br.com.tecnobiz.spring.config.dao.profiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;

/**
 * Profile para ser utilizado em ambientes providos de um container como JBoss,
 * WebSphere, etc.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.0.0
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Profile("container-jdbc")
public @interface ContainerJDBC {

}
