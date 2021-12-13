package pl.put.poznan.transformer.logic;

public class JsonDecorator implements Json {

    protected Json decoratedJson;

    JsonDecorator(Json content) {
        this.decoratedJson = content;
    }

    @Override
    public String getData() {
        return decoratedJson.getData();
    }
}