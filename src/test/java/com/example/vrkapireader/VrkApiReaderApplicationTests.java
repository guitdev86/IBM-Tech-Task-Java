package com.example.vrkapireader;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.vrkapireader.controller.RequestController;
import com.example.vrkapireader.model.Election;
import com.example.vrkapireader.repository.RequestRepository;
import com.example.vrkapireader.service.RequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.ArrayList;


@SpringBootTest
class ApplicationElementsTests {

	@Autowired
	RequestRepository requestRepository;

	@Autowired
	RequestService requestService;

	@Autowired
	RequestController requestController;

	@Test
	void requestRepositoryLoads() throws Exception {
		assertThat(requestRepository).isNotNull();
	}

	@Test
	void requestServiceLoads() throws Exception {
		assertThat(requestService).isNotNull();
	}

	@Test
	void requestControllerLoads() throws Exception {
		assertThat(requestController).isNotNull();
	}

	@Test
	void sortDataFunctionShouldSortAnArray() throws Exception {
		ArrayList<Election> electionArray = new ArrayList();

		Election firstElement = new Election(1, "Election 1", "2016-10-23 00:00:00+0300");
		Election secondElement = new Election(2, "Election 2", "2017-04-23 00:00:00+0300");
		Election thirdElement = new Election(3, "Election 3", "2016-10-09 00:00:00+0300");

		electionArray.add(firstElement);
		electionArray.add(secondElement);
		electionArray.add(thirdElement);

		ArrayList<Election> expected = requestService.sortData(electionArray);

		assertThat(expected)
				.isInstanceOf(ArrayList.class)
				.startsWith(thirdElement)
				.hasAtLeastOneElementOfType(Election.class);
	}

	@Test
	void getFilteredDataFunctionTest() throws JsonProcessingException {

		ArrayList<Election> electionArrayList = requestService.getFilteredData();

		assertThat(electionArrayList)
				.isInstanceOf(ArrayList.class)
				.isNotEmpty();

		assertThat(electionArrayList.get(0))
				.isNotNull()
				.isInstanceOf(Election.class);
	}

	@Test
	void getInfoFromURLFunctionShouldReturnData() throws JsonProcessingException{
		JsonNode jsonNode = requestRepository.getInfoFromURL();

		assertThat(jsonNode)
				.isNotEmpty()
				.isInstanceOf(JsonNode.class);
	}

}

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HttpRequestTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void responseShouldBeNotEmpty() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/results",
				String.class)).contains("id", "name", "date");
	}

}

