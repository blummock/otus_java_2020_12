package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.otus.model.Measurement;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class FileLoader implements Loader {

    private String fileName;

    public FileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(fileName)));
        return new Gson().fromJson(reader, new TypeToken<List<Measurement>>() {}.getType());
    }
}
