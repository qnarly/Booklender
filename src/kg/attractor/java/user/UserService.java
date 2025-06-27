package kg.attractor.java.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserService {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH_USERS = Paths.get("data/dataBase/users.json");
    private static final Path PATH_LIBRARY = Paths.get("data/dataBase/library.json");


    public boolean isUserExist(String email) {
        List<User> users = readFile();
        return users.stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }

    public Optional<User> loginChecker(String email) {
        return readFile().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public void addUser(User newUser) {
        List<User> users = readFile();
        users.add(newUser);
        writeFile(users);
    }

    private List<User> readFile() {
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
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void writeFile(List<User> users) {
        String jsonToWrite = GSON.toJson(users);
        try {
            Files.writeString(PATH_USERS, jsonToWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}