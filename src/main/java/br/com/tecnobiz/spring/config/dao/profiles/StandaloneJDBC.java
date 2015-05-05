package br.com.tecnobiz.spring.config.dao.profiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;

/**
 * Profile para ser utilizado em ambientes StandaloneJDBC, normalmente em
 * produ��o, homologa��o ou qualquer outro ambiente com uma infra-estrutura
 * definida.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.0.0
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Profile("standalone-jdbc")
public @interface StandaloneJDBC {

}
