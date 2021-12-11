package pl.put.poznan.transformer.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.JsonMinifier;
import pl.put.poznan.transformer.logic.JsonTools;
import pl.put.poznan.transformer.logic.JsonValidator;

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

        // perform the transformation, you should run your logic here, below is just a silly example
        JsonTools transformer = new JsonTools(transforms);
        return transformer.transform(text);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public String post(@PathVariable String text,
                      @RequestBody String json) {
        String result;

        if(JsonValidator.isValidJson(json)) {
            String minified = JsonMinifier.minify(json);
            result = minified;
        }
        else {
            result = "Json is not valid";
        }

        logger.debug(result);
        return result;
    }
}


