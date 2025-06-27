package kg.attractor.java.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.attractor.java.user.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtil {

    private FileUtil() {
    }

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH_LIBRARY = Paths.get("data/library.json");
    private static final Path PATH_USERS = Paths.get("data/users.json");


    public static List<User> readFile() {
        if (!Files.exists(PATH_USERS)) {
            return new ArrayList<>();
        }
        try {
            String json = Files.readString(PATH_USERS);
            if (json.isBlank()) {
                return new ArrayList<>();
            }
            User[] users = GSON.fromJson(json, User[].class);
            return new ArrayList<>(Arrays.asList(users));

        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println(ioe.getMessage());
            return new ArrayList<>();
        }
    }

    public static void writeFile(User newUser) {
        List<User> users = readFile();

        users.add(newUser);

        String jsonToWrite = GSON.toJson(users);

        try {
            Files.writeString(PATH_USERS, jsonToWrite);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

}
