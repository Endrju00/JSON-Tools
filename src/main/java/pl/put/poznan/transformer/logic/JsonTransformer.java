package pl.put.poznan.transformer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.transformer.logic.decorators.*;
import java.util.Arrays;

/**
 * Class stores parameters and calls selected decorators
 */
public class JsonTransformer {
    /**
     * Stores operations that have to be done on provided JSON
     */
    private String[] transforms;
    /**
     * Attributes that will be saved in output JSON
     */
    private String[] toSave;
    /**
     * Attributes that will be removed from final JSON
     */
    private String[] toCut;
    /**
     * Used for logging
     */
    private final Logger logger = LoggerFactory.getLogger(JsonTransformer.class);

    /**
     * Class constructor sets private parameters
     * @param transforms array of decorator names that will be used
     * @param toCut array of attributes used by {@link pl.put.poznan.transformer.logic.decorators.JsonCutterDecorator}
     * @param toSave array of attributes used by {@link pl.put.poznan.transformer.logic.decorators.JsonSaverDecorator}
     */
    public JsonTransformer (String[] transforms, String[] toCut, String[] toSave) {
        this.transforms = transforms;
        this.toCut = toCut;
        this.toSave = toSave;
    }

    /**
     * Method validates the data and uses all decorators in transforms
     * @param data Json object that will be transformed
     * @return transformed JSON as String
     * @throws NoSuchMethodException when given method was not found
     */
    public String transform(Json data) throws NoSuchMethodException {
        data = new JsonValidatorDecorator(data);

        for (String transform : transforms) {
            switch (transform) {
                case "minify":
                    data = new JsonMinifierDecorator(data);
                    break;
                case "clarify":
                    data = new JsonClarifierDecorator(data);
                    break;
                case "cut":
                    data = new JsonCutterDecorator(data, Arrays.asList(toCut));
                    break;
                case "save":
                    data = new JsonSaverDecorator(data, Arrays.asList(toSave));
                    break;
                default:
                    logger.debug("There is no such method as: " + transform);
                    throw new NoSuchMethodException("Invalid transform");
            }
        }

        return data.getData();
    }
}
