package com.bjit.finalproject.TMS.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.web.bind.annotation.RestController;

@RestController
@OpenAPIDefinition(info = @Info(title = "TMS",description = "Management system for BJIT Academy",version = "1.0"))
public class ApiDocumentationConfig {

}
