package io.Daro.project.controller;

import io.Daro.project.model.TaskConfigurationProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/info")
class InfoController {
    //@Value("${spring.datasource.url}")
    //private String url;
    ///@Autowired
    private DataSourceProperties dataSourceProperties;
    //@Value("${task.allowMultipleTasksFromTemplate}")
    private TaskConfigurationProperties myProp, myTest;

    InfoController(final DataSourceProperties dataSourceProperties, final TaskConfigurationProperties myProp) {
        this.dataSourceProperties = dataSourceProperties;
        this.myProp = myProp;


    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/url")
    String url(){
        return dataSourceProperties.getUrl();
    }
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/prop")
    boolean myProp(){
        return myProp.getTemplate().isAllowMultipleTasks();
    }


}
