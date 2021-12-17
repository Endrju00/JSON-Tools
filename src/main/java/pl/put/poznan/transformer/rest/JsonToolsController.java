package pl.put.poznan.transformer.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.*;

import java.util.Arrays;

@RestController
@RequestMapping("/{text}")
public class JsonToolsController {

    private static final Logger logger = LoggerFactory.getLogger(JsonToolsController.class);

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public String get(@PathVariable String text,
                              @RequestParam(value="transforms", defaultValue="upper,escape") String[] transforms) {

        // log the parameters
        logger.debug(text);
        logger.debug(Arrays.toString(transforms));

        Json json = new JsonMinifierDecorator ( new JsonData(text));

        logger.debug(json.getData());

        return json.getData();
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public String post(@PathVariable String text,
                      @RequestBody String jsonRequest) {

        Json json1 = new JsonData(jsonRequest);
//        Json json2 = new JsonValidatorDecorator(json1);
//        if(json2.getData().equals("Invalid Json"))
//            return "Invalid Json";
//        else
//            return json2.getData();

        Json json = new JsonClarifierDecorator (json1);
        logger.debug(json.getData());
        return json.getData();
    }
}


