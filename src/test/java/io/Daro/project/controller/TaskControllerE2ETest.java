package io.Daro.project.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.Daro.project.model.Task;
import io.Daro.project.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    TaskRepository repo;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void httpGet_returnsAllTasks(){
        //given
        int initial = repo.findAll().size();
        repo.save(new Task("foo", LocalDateTime.now()));
        repo.save(new Task("bar", LocalDateTime.now()));

        //when
        Task[] result = restTemplate.getForObject("http://localhost:" + port + "/tasks", Task[].class);

        //then
        assertThat(result).hasSize(initial + 2);

    }

    @Test
    void httpGet_returnsTaskById() {

        //given


        repo.save(new Task("task1", LocalDateTime.now()));
        repo.save(new Task("task2", LocalDateTime.now()));

        //when
        Task resultTask1 = restTemplate.getForObject("http://localhost:" + port + "/tasks/1", Task.class);
        Task resultTask2 = restTemplate.getForObject("http://localhost:" + port + "/tasks/2", Task.class);

        //then
        String res1 = "description of task1";
        String res2 = "description of task2";

        assertThat(resultTask1).descriptionText().contains(res1);
        assertThat(resultTask2).descriptionText().contains(res2);
        assertThat(resultTask1).toString().equals(res1);
        assertThat(resultTask2).toString().equals(res2);
        assertThat(resultTask1).hasFieldOrProperty("description");
        assertThat(resultTask2).hasFieldOrProperty("description");
        //assertThat(resultTask1).hasNoNullFieldsOrProperties();
        //assertThat(resultTask2).hasNoNullFieldsOrProperties();

    }

    @Test
    public void httpPost_createNewTask()
            throws IOException, JSONException {

        String URL = "http://localhost:" + port + "/tasks/1";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject JsonObject = new JSONObject();
        JsonObject.put("description", "task1");
        JsonObject.put("deadline", LocalDateTime.now());

        HttpEntity<String> request =
                new HttpEntity<String>(JsonObject.toString(), headers);

        Task result =
                restTemplate.postForObject(URL, request, Task.class);
        String root = objectMapper.writeValueAsString(result);

        assertNotNull(result);
        assertNotNull(root);
        assertThat(result).isNotNull();
        assertThat(result).toString().contains("task1");
    }



}