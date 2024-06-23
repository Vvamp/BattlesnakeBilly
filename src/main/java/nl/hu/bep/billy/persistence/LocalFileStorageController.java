package nl.hu.bep.billy.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hu.bep.billy.authentication.User;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LocalFileStorageController implements IStorageController{
    private static String userDirectory = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/billy" + "/users/";
    @Override
    public void save(List<User> users) {
        System.out.println("Saving to path: " + userDirectory);
        System.out.println("Saving " + users.size() + " users" );
        try {
            Files.createDirectories(Paths.get(userDirectory));
        } catch (IOException e) {
            System.out.println("failed: " + e.getMessage());

            throw new RuntimeException(e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        for(User user : users) {
            File file = new File(userDirectory + user.getName());
            try {
                objectMapper.writeValue(file, user);
                System.out.println("Saved to " + file.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("failed: " + e.getMessage());

                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<User> load() {
        List<File> files;
        List<User> users = new ArrayList<>();
        if(!Files.exists(Paths.get(userDirectory))) {
            return users;
        }

        try {
            files = Files.walk(Paths.get(userDirectory))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(File file : files){
            ObjectMapper objectMapper = new ObjectMapper();
            User user;
            try {
                user = objectMapper.readValue(file, User.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            users.add(user);
        }
        return users;
    }
}
