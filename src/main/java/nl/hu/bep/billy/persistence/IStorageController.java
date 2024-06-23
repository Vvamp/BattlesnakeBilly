package nl.hu.bep.billy.persistence;

import nl.hu.bep.billy.authentication.User;

import java.util.List;

public interface IStorageController {
    public void save(List<User> users);
    public List<User> load();
    
}
