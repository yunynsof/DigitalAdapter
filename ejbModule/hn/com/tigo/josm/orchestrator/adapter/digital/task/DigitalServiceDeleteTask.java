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
import hn.com.tigo.josm.orchestrator.driver.digital.operations.DigitalDeleteOperation;
import hn.com.tigo.josm.orchestrator.driver.digital.request.DeleteServiceRequest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

/**
 * The Class DigitalServiceDeleteTask is used to execute the method DigitalServiceDeleteTask (DigitalDriver) .
 *
 * @author Leonardo Vijil
 * @version 1.0.0
 * @since 18/10/2019
 */
@WebService
@Stateless(mappedName = "DigitalServiceDeleteTask")
public class DigitalServiceDeleteTask extends AbstractTask<DigitalDeleteOperation, DigitalDriver> implements Task {
	
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
	protected DigitalDeleteOperation createRequest(final TaskRequestType taskType) throws AdapterException {				
		final String json = DigitalAdapter.getParameterValue(DigitalConstantsAdapter.JSON, taskType);
		printParameterValue(DigitalConstantsAdapter.JSON, json);
		validateParameter(DigitalConstantsAdapter.JSON, json, AdapterValidationType.ALFANUMERIC_ALL, true);

		return new DigitalDeleteOperation(new Gson().fromJson(json, DeleteServiceRequest.class));
	}

	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.adapter.task.AbstractTask#executeDriverTask(java.lang.Object)
	 */
	@Override
	protected TaskResponseType executeDriverTask(DigitalDriver driver) throws AdapterException {
		return driver.executeDriver(request);
	}
}
