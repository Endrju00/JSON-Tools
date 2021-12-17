package pl.put.poznan.transformer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.transformer.logic.decorators.*;
import java.util.Arrays;

public class JsonTransformer {
    private String[] transforms;
    private String[] toSave;
    private String[] toCut;
    private final Logger logger = LoggerFactory.getLogger(JsonTransformer.class);

    public JsonTransformer (String[] transforms, String[] toCut, String[] toSave) {
        this.transforms = transforms;
        this.toCut = toCut;
        this.toSave = toSave;
    }

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
                    if(logger.isDebugEnabled())
                        logger.debug("There is no such method as: " + transform);

                    throw new NoSuchMethodException("Invalid transform");
            }
        }

        return data.getData();
    }
}
