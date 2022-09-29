package hn.com.tigo.josm.orchestrator.adapter.digital.task;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import hn.com.tigo.josm.common.adapter.AbstractAdapter;
import hn.com.tigo.josm.common.adapter.AdapterValidationType;
import hn.com.tigo.josm.common.adapter.dto.TaskRequestType;
import hn.com.tigo.josm.common.adapter.dto.TaskResponseType;
import hn.com.tigo.josm.common.adapter.task.AbstractTask;
import hn.com.tigo.josm.common.adapter.task.Task;
import hn.com.tigo.josm.common.exceptions.AdapterException;
import hn.com.tigo.josm.common.exceptions.enumerators.AdapterErrorCode;
import hn.com.tigo.josm.orchestrator.adapter.digital.DigitalAdapter;
import hn.com.tigo.josm.orchestrator.adapter.digital.utils.DigitalConstantsAdapter;
import hn.com.tigo.josm.orchestrator.driver.digital.DigitalDriver;
import hn.com.tigo.josm.orchestrator.driver.digital.operations.TigoSportProvisioningOperation;
import hn.com.tigo.josm.orchestrator.driver.digital.request.TigoSportsProvInput;

/**
 * TigoSportProvisioningTask This class contains the processes that are exposed in the web service.
 *
 * @author Yuny Rene Rodriguez Perez {@literal<mailto: yrodriguez@hightech-corp.com />}
 * @version  1.0.0
 * @since 09-26-2022 05:00:39 PM 2022
 */
@WebService
@Stateless(mappedName = "TigoSportProvisioningTask")
public class TigoSportProvisioningTask extends AbstractTask<TigoSportProvisioningOperation, DigitalDriver>
		implements Task {

	/** Attribute that determine a Constant of LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(TigoSportProvisioningTask.class);

	/** Attribute that determine digitalAdapter. */
	@EJB
	private DigitalAdapter digitalAdapter;

	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.adapter.task.AbstractTask#getSingletonAdapter()
	 */
	@Override
	protected AbstractAdapter<DigitalDriver> getSingletonAdapter() {
		return digitalAdapter;
	}

	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.adapter.task.AbstractTask#executeDriverTask(java.lang.Object)
	 */
	@Override
	protected TaskResponseType executeDriverTask(final DigitalDriver driver) throws AdapterException {
		return driver.executeDriver(request);
	}

	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.adapter.task.AbstractTask#createRequest(hn.com.tigo.josm.common.adapter.dto.TaskRequestType)
	 */
	@Override
	protected TigoSportProvisioningOperation createRequest(final TaskRequestType taskType) throws AdapterException {

		TigoSportsProvInput inputModel = new TigoSportsProvInput();
		String json = DigitalAdapter.getParameterValue(DigitalConstantsAdapter.JSON, taskType);
		printParameterValue(DigitalConstantsAdapter.JSON, json);
		validateParameter(DigitalConstantsAdapter.JSON, json, AdapterValidationType.ALL, true);

		try {
			Gson gson = new Gson();
			inputModel = gson.fromJson(json, TigoSportsProvInput.class);

		} catch (NullPointerException | JsonSyntaxException e) {
			LOGGER.error(e.getMessage(), e);
			throw new AdapterException(AdapterErrorCode.PARAMETERS_ERROR, DigitalConstantsAdapter.CORRECT_JSON_STRUCTURE,
					e);
		}
		return new TigoSportProvisioningOperation(inputModel);
	}

}
