package br.com.tecnobiz.spring.config.dao.helpers;

/**
 * Helper to get environment properties.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.0.0
 */
public final class PropKeys {

	private static final String KEY_SUFFIX = "spring.config.dao.";
	public static final String DB_USER = KEY_SUFFIX + "db.user";
	public static final String DB_PASS = KEY_SUFFIX + "db.password";
	public static final String DB_URL = KEY_SUFFIX + "db.url";
	public static final String DB_DRIVER = KEY_SUFFIX + "db.driver";
	public static final String DB_MAX_CONN = KEY_SUFFIX + "db.max.conn";
	public static final String DB_MIG_SCRIPTS = KEY_SUFFIX
			+ "db.migration_scripts";
	public static final String DB_LOAD_SCRIPTS = KEY_SUFFIX
			+ "db.load_scripts";
	public static final String DB_VAL_QUERY = KEY_SUFFIX
			+ "db.validation_query";
	public static final String JPA_ENTITY_PACKS = KEY_SUFFIX
			+ "jpa.entity_packages";
	public static final String JPA_VENDOR = KEY_SUFFIX + "jpa.vendor";
	public static final String JNDI_DS = KEY_SUFFIX + "jndi.ds";
	public static final String JNDI_EMF = KEY_SUFFIX + "jndi.em";

	private PropKeys() {

	}

}
