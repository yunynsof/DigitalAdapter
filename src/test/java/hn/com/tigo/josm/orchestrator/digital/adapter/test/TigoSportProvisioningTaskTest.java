package hn.com.tigo.josm.orchestrator.digital.adapter.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import hn.com.tigo.josm.common.adapter.dto.ParameterArray;
import hn.com.tigo.josm.common.adapter.dto.ParameterType;
import hn.com.tigo.josm.common.adapter.dto.TaskRequestType;
import hn.com.tigo.josm.common.adapter.dto.TaskResponseType;
import hn.com.tigo.josm.common.exceptions.AdapterException;
import hn.com.tigo.josm.orchestrator.adapter.digital.utils.DigitalConstantsAdapter;

public class TigoSportProvisioningTaskTest extends AbstractTest{

	public TigoSportProvisioningTaskTest() throws Exception {
		super("java:global/DigitalAdapter/TigoSportProvisioningTask!hn.com.tigo.josm.common.adapter.task.Task");
	}
	
	@Test
	public void test()  {
		
		try {
			final TaskResponseType response = task.executeTask(buildTaskRequestAction());
			assertEquals(0, response.getResponseCode());
		} catch (AdapterException e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	public void testErr()  {
		
		try {
			final TaskResponseType response = task.executeTask(buildTaskRequestActionErr());
			assertEquals(0, response.getResponseCode());
		} catch (AdapterException e) {
			//fail(e.getMessage());
		}
	}
	
	private TaskRequestType buildTaskRequestAction() {
		final ParameterArray parameterArray = new ParameterArray();
		
		ParameterType parameterTypeReq = new ParameterType();
		parameterTypeReq.setName(DigitalConstantsAdapter.JSON);
		parameterTypeReq.setValue("{\r\n" + 
				"    \"transactionId\": \"5652a915-d3d6-4082-9dab-6465a27c5f18\",\r\n" + 
				"    \"dateEvent\": \"2022-08-12 14:03:42\",\r\n" + 
				"    \"duration\": 10,\r\n" + 
				"    \"productId\": 1133,\r\n" + 
				"    \"orderType\": \"PURCHASE\",\r\n" + 
				"    \"eventType\": \"TIGO_SPORT_SUBSCRIPCION\",\r\n" + 
				"    \"additionalsParams\": [\r\n" + 
				"        {\r\n" + 
				"            \"attribute\": \"PAYMENTID\",\r\n" + 
				"            \"value\": \"9723500420211129150342\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"attribute\": \"AMOUNT\",\r\n" + 
				"            \"value\": \"0.0\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"attribute\": \"MTR\",\r\n" + 
				"            \"value\": \"CPE;7;5652a915-d3d6-4082-9dab-6465a27c5f18\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"attribute\": \"CHANNELID\",\r\n" + 
				"            \"value\": \"7\"\r\n" + 
				"        }\r\n" + 
				"    ]\r\n" + 
				"}");
		parameterArray.getParameter().add(parameterTypeReq);

		final TaskRequestType taskRequestType = new TaskRequestType();
		taskRequestType.setParameters(parameterArray);
		return taskRequestType;
	}
	
	private TaskRequestType buildTaskRequestActionErr() {
		final ParameterArray parameterArray = new ParameterArray();
		
		ParameterType parameterTypeReq = new ParameterType();
		parameterTypeReq.setName(DigitalConstantsAdapter.JSON);
		parameterTypeReq.setValue("");
		parameterArray.getParameter().add(parameterTypeReq);

		final TaskRequestType taskRequestType = new TaskRequestType();
		taskRequestType.setParameters(parameterArray);
		return taskRequestType;
	}
}
