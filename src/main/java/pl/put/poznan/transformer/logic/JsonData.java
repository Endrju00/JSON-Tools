package pl.put.poznan.transformer.logic;

public class JsonData implements Json {

    private String data;

    public JsonData(String data) {
        this.data = data;
    }

    @Override
    public String getData() {
        return data;
    }
}
