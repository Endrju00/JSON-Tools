package pl.put.poznan.transformer.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.*;
import pl.put.poznan.transformer.logic.decorators.JsonMinifierDecorator;
import pl.put.poznan.transformer.logic.decorators.JsonSaverDecorator;

import java.util.Arrays;

@RestController
public class JsonToolsController {

    private static final Logger logger = LoggerFactory.getLogger(JsonToolsController.class);

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/{jsonRequest}")
    public String get(@PathVariable String jsonRequest,
                      @RequestParam(value="transforms", defaultValue="minify, cut") String[] transforms,
                      @RequestParam(value="cut", defaultValue="") String[] toCut,
                      @RequestParam(value="save", defaultValue="") String[] toSave) {

        // log the parameters
        logger.debug(jsonRequest);
        logger.debug(Arrays.toString(transforms));
        logger.debug(Arrays.toString(toCut));
        logger.debug(Arrays.toString(toSave));

        Json json = new JsonData(jsonRequest);

        JsonTransformer transformer = new JsonTransformer(transforms, toCut, toSave);
        try{
            return transformer.transform(json);
        }
        catch (NoSuchMethodException e) {
            return e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public String post(@RequestParam(value="transforms", defaultValue="minify, cut") String[] transforms,
                       @RequestParam(value="cut", defaultValue="") String[] toCut,
                       @RequestParam(value="save", defaultValue="") String[] toSave,
                       @RequestBody String jsonRequest) {

        // log the parameters
        logger.debug(jsonRequest);
        logger.debug(Arrays.toString(transforms));
        logger.debug(Arrays.toString(toCut));
        logger.debug(Arrays.toString(toSave));

        Json json = new JsonData(jsonRequest);

        JsonTransformer transformer = new JsonTransformer(transforms, toCut, toSave);
        try{
            return transformer.transform(json);
        }
        catch (NoSuchMethodException e) {
            return e.getMessage();
        }
    }
}


