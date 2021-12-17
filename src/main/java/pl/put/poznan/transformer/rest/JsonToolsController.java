package pl.put.poznan.transformer.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.*;

@RestController
public class JsonToolsController {

    private static final Logger logger = LoggerFactory.getLogger(JsonToolsController.class);

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/{jsonRequest}")
    public String get(@PathVariable String jsonRequest,
                      @RequestParam(value="transforms", defaultValue="minify, cut") String[] transforms,
                      @RequestParam(value="cut", defaultValue="") String[] toCut,
                      @RequestParam(value="save", defaultValue="") String[] toSave) throws NoSuchMethodException {

        Json json = new JsonData(jsonRequest);
        JsonTransformer transformer = new JsonTransformer(transforms, toCut, toSave);
        return transformer.transform(json);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public String post(@RequestParam(value="transforms", defaultValue="minify, cut") String[] transforms,
                       @RequestParam(value="cut", defaultValue="") String[] toCut,
                       @RequestParam(value="save", defaultValue="") String[] toSave,
                       @RequestBody String jsonRequest) throws NoSuchMethodException {

        Json json = new JsonData(jsonRequest);
        JsonTransformer transformer = new JsonTransformer(transforms, toCut, toSave);
        return transformer.transform(json);
    }
}


