package hn.com.tigo.josm.orchestrator.digital.adapter.test;

import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;

import hn.com.tigo.josm.common.interfaces.producer.InterfaceFactory;


public class EjbContainerContext implements Closeable{

	/** Attribute that determine a Constant of INSTANCE. */
	public static final EjbContainerContext INSTANCE = new EjbContainerContext();

	/** Attribute that determine a Constant of JUNIT_TEST. */
	private static final boolean JUNIT_TEST = false;

	/** Attribute that determine a Constant of RESOURCE. */
	private static final String RESOURCE = "new://Resource?type=DataSource";

	/** Attribute that determine a Constant of JDBC_URL. */
	private static final String JDBC_URL = "jdbc:oracle:thin:@192.168.159.52:1521/josmdb.celtel.net";

	/** Attribute that determine a Constant of DRIVER. */
	private static final String DRIVER = "oracle.jdbc.OracleDriver";

	/** Attribute that determine a Constant of PASS. */
	private static final String PASS = "j0sM";

	/** Attribute that determine container. */
	private static EJBContainer container;

	/**
	 * Instantiates a new ejb container context.
	 */
	private EjbContainerContext() {

		final Properties properties = new Properties();
		properties.put("ConfigurationDS", RESOURCE);
		properties.put("ConfigurationDS.JtaManaged", true);
		properties.put("ConfigurationDS.UserName", "CONFIGURATION");
		properties.put("ConfigurationDS.Password", "jsm_0101");
		properties.put("ConfigurationDS.JdbcUrl", JDBC_URL);
		properties.put("ConfigurationDS.JdbcDriver", DRIVER);

		properties.put("ConfigurationDS2", RESOURCE);
		properties.put("ConfigurationDS2.JtaManaged", true);
		properties.put("ConfigurationDS2.UserName", "CONFIGURATION2");
		properties.put("ConfigurationDS2.Password", PASS);
		properties.put("ConfigurationDS2.JdbcUrl", JDBC_URL);
		properties.put("ConfigurationDS2.JdbcDriver", DRIVER);
		

		if (JUNIT_TEST) {
			InterfaceFactory.COMMON_CONFIGURATION_REMOTE = "java:global/Common-Configuration/ConfigurationJosm!hn.com.tigo.josm.common.interfaces.ConfigurationJosmRemote";
			InterfaceFactory.MONITORING_MANAGER_REMOTE = "java:global/MonitoringManager-1.0.4/MonitoringManagerExternal!hn.com.tigo.josm.common.interfaces.MonitoringManagerRemote";
		} else {
			InterfaceFactory.COMMON_CONFIGURATION_REMOTE = "java:global/Common-Configuration-2.0.1/ConfigurationJosm!hn.com.tigo.josm.common.interfaces.ConfigurationJosmRemote";
			InterfaceFactory.MONITORING_MANAGER_REMOTE = "java:global/MonitoringManager-1.0.4/MonitoringManagerExternal!hn.com.tigo.josm.common.interfaces.MonitoringManagerRemote";
		}

		container = EJBContainer.createEJBContainer(properties);
	}

	/**
	 * Gets the container.
	 *
	 * @return the container
	 */
	public EJBContainer getContainer() {
		return container;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {

		if (container != null) {
			container.close();
		}
	}

}
