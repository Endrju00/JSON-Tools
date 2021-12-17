package pl.put.poznan.transformer.logic.decorators;


import pl.put.poznan.transformer.logic.Json;

public abstract class JsonDecorator implements Json {

    protected Json decoratedJson;

    JsonDecorator(Json content) {
        this.decoratedJson = content;
    }

    @Override
    public String getData() {
        return decoratedJson.getData();
    }
}
