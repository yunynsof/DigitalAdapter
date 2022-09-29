package hn.com.tigo.josm.orchestrator.adapter.digital;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import hn.com.tigo.josm.common.adapter.AbstractAdapter;
import hn.com.tigo.josm.common.adapter.config.AdapterConfig;
import hn.com.tigo.josm.common.exceptions.AdapterException;
import hn.com.tigo.josm.orchestrator.adapter.digital.utils.DigitalConstantsAdapter;
import hn.com.tigo.josm.orchestrator.driver.digital.DigitalDriver;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.log4j.Logger;

/**
 * The Class DigitalAdapter class singleton, used to manage the
 * configuration for attributes Throughput and TimeOut for tasks in
 * NexusAdapter .This attributes come from ConfigurationJosm service from
 * NexusAdapter.xml. Also create a Driver's Pool to manage the maintenance
 * threads execution and monitoring.
 * 
 * @author Leonardo Vijil
 * @version 1.0.0
 * @since 14/03/2032
 */
@Singleton
@Startup
@DependsOn("MonitoringManager")
@ConcurrencyManagement(BEAN)
public class DigitalAdapter extends AbstractAdapter<DigitalDriver> {

	/** The Constant LOGGER.*/
	private static final Logger LOGGER = Logger.getLogger(DigitalAdapter.class);


	/**
	 * Instantiates a new NexusAdapter and create a NexusAdapter Monitor
	 * instance. Also Load the configuration and create the drivers.
	 */
	public DigitalAdapter() {
		LOGGER.info("Init NexusAdapter Singleton");
	}

	
	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.adapter.AbstractAdapter#createDriver()
	 */
	@Override
	public DigitalDriver createDriver() throws AdapterException {
		final AdapterConfig config =  this.getConfigurationType();
		final String digitalServiceDelete = config.getDriverConfig().getConnections().get("digitalServiceDelete").getParameters().get(DigitalConstantsAdapter.ENDPOINT);
		final String digitalServiceCreate = config.getDriverConfig().getConnections().get("digitalServiceCreate").getParameters().get(DigitalConstantsAdapter.ENDPOINT);
		final String tigoSportProvisioning = config.getDriverConfig().getConnections().get("tigoSportProvisioning").getParameters().get(DigitalConstantsAdapter.ENDPOINT);
		final String userNameDelete = config.getDriverConfig().getConnections().get("digitalServiceDelete").getParameters().get("userName");
		final String passwordDelete = config.getDriverConfig().getConnections().get("digitalServiceDelete").getParameters().get("password");
		final String userNameCreate = config.getDriverConfig().getConnections().get("digitalServiceCreate").getParameters().get("userName");
		final String passwordCreate = config.getDriverConfig().getConnections().get("digitalServiceCreate").getParameters().get("password");
		final String passwordTigoSport = config.getDriverConfig().getConnections().get("tigoSportProvisioning").getParameters().get("password");
		return new DigitalDriver(digitalServiceDelete, digitalServiceCreate, userNameDelete, passwordDelete, userNameCreate, passwordCreate, tigoSportProvisioning, passwordTigoSport);
	}

}
