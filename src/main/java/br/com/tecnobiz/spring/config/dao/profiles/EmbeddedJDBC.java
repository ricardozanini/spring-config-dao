package br.com.tecnobiz.spring.config.dao.profiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;

/**
 * Profile para ser utilizado em ambientes de desenvolvimento, normalmente
 * associados � testes de integra��o, de unidade, etc.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.0.0
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Profile("embedded-jdbc")
public @interface EmbeddedJDBC {

}
