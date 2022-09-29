package hn.com.tigo.josm.orchestrator.adapter.digital.task;

import hn.com.tigo.josm.common.adapter.AbstractAdapter;
import hn.com.tigo.josm.common.adapter.AdapterValidationType;
import hn.com.tigo.josm.common.adapter.dto.TaskRequestType;
import hn.com.tigo.josm.common.adapter.dto.TaskResponseType;
import hn.com.tigo.josm.common.adapter.task.AbstractTask;
import hn.com.tigo.josm.common.adapter.task.Task;
import hn.com.tigo.josm.common.exceptions.AdapterException;
import hn.com.tigo.josm.orchestrator.adapter.digital.DigitalAdapter;
import hn.com.tigo.josm.orchestrator.adapter.digital.utils.DigitalConstantsAdapter;
import hn.com.tigo.josm.orchestrator.driver.digital.DigitalDriver;
import hn.com.tigo.josm.orchestrator.driver.digital.operations.DigitalCreateOperation;
import hn.com.tigo.josm.orchestrator.driver.digital.request.CreateServiceRequest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

/**
 * The Class DigitalServiceCreateTask is used to execute the method DigitalServiceCreateTask (DigitalDriver) .
 *
 * @author Leonardo Vijil
 * @version 1.0.0
 * @since 18/10/2019
 */
@WebService
@Stateless(mappedName = "DigitalServiceCreateTask")
public class DigitalServiceCreateTask extends AbstractTask<DigitalCreateOperation, DigitalDriver> implements Task {
	
	/** Attribute that determine log. */
	protected static final Logger LOGGER = Logger.getLogger(DigitalServiceCreateTask.class);
	
	/** Attribute that determine nexusAdapter. */
	@EJB
	private DigitalAdapter nexusAdapter;
	
	
	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.adapter.task.AbstractTask#getSingletonAdapter()
	 */
	@Override
	protected AbstractAdapter<DigitalDriver> getSingletonAdapter() {
		return nexusAdapter;
	}
	
	
	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.adapter.task.AbstractTask#createRequest(hn.com.tigo.josm.common.adapter.dto.TaskRequestType)
	 */
	@Override
	protected DigitalCreateOperation createRequest(final TaskRequestType taskType) throws AdapterException {				
		final String json = DigitalAdapter.getParameterValue(DigitalConstantsAdapter.JSON, taskType);
		printParameterValue(DigitalConstantsAdapter.JSON, json);
		validateParameter(DigitalConstantsAdapter.JSON, json, AdapterValidationType.ALFANUMERIC_ALL, true);

		return new DigitalCreateOperation(new Gson().fromJson(json, CreateServiceRequest.class));
	}

	
	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.adapter.task.AbstractTask#executeDriverTask(java.lang.Object)
	 */
	@Override
	protected TaskResponseType executeDriverTask(DigitalDriver driver) throws AdapterException {
		return driver.executeDriver(request);
	}
}
