package com.hotelrosana.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.regex.*;


/**
 * Class for handling a passwords text file.
 */
public class Passwords {
    
    private String fileContent;
    private final String fileName;
    private ArrayList<User> users;
    
    /**
     * Loads the file and saves its file content.
     */
    private void loadFile() {
        try (FileInputStream file = new FileInputStream(fileName)) {
            BufferedReader bufferedReader;
            try (FileReader fileReader = new FileReader(file.getFD())) {
                bufferedReader = new BufferedReader(fileReader);
                String lnBuffer;
                while ((lnBuffer = bufferedReader.readLine()) != null) {
                    fileContent += lnBuffer + "\r\n";
                }
            }
            
            bufferedReader.close();
        } catch(IOException ex) {
            fileContent = "";
        }
    }
    
    /**
     * Gets loaded file content of the file.
     * 
     * @return The file content.
     */
    public final String getFileContent() {
        return fileContent;
    }
    
    /**
     * Saves the file content back to the file.
     * <br>Used when updating file content.
     * 
     * @return {@code true} on success and {@code false} on failure.
     */
    private boolean saveFileContent() {
        try (FileOutputStream file = new FileOutputStream(fileName)) {
            try (FileWriter fileWriter = new FileWriter(file.getFD())) {
                for (String line: fileContent.split("\\r\\n")) {
                    fileWriter.append(line + "\r\n");
                }
            }
            
            return true;
        } catch(IOException ex) {
            return false;
        }
    }
    
    /**
     * Uses the loaded file content to load users present in it.
     */
    private void loadUsers() {
        for (String line: fileContent.split("\\r\\n")) {
            Matcher lineMatcher = Pattern.compile(";(.*) = (.*)").matcher(line);
            if (lineMatcher.find()) {
                User user = new User();
                try {
                    user.setUsername(lineMatcher.group(1));
                    user.setPassword(lineMatcher.group(2));
                    users.add(user);
                } catch(Exception ex) {
                    users.remove(user);
                }
            }
        }
    }
    
    /**
     * Gets loaded users.
     * 
     * @return Loaded users.
     */
    public final ArrayList<User> getUsers() {
        return users;
    }
    
    /**
     * Adds a new user to the file.
     * 
     * @param username User's username.
     * @param password User's password.
     * @return {@code true} on success and {@code false} on failure.
     * @throws Exception If the username does not validate.
     */
    public final boolean add(String username, String password) throws Exception {
        if (username.isEmpty()) {
            throw new Exception("Username cannot be blank.");
        }
        if (!Pattern.matches("^\\w+$", username)) {
            throw new Exception("Invalid username. Only word characters are allowed.");
        }
        
        for (User user: users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                throw new Exception("This username already exists.");
            }
        }
        
        fileContent += ";" + username.toLowerCase() + " = " + encrypt(password) + "\r\n";
        return saveFileContent();
    }
    
    /**
     * Removes a user from the file.
     * 
     * @param username Username of a user to be removed.
     * @return {@code true} on success and {@code false} on failure.
     */
    public final boolean remove(String username) {        
        for (User user: users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                fileContent = fileContent.replace(";" + username.toLowerCase() + " = " + get(username) + "\r\n", "");
                users.remove(user);
                return saveFileContent();
            }
        }
        return false;
    }
    
    /**
     * Checks whether a user is present in the file.
     * 
     * @param username User's username.
     * @param password User's password.
     * @return {@code true} or {@code false}.
     */
    public final boolean find(String username, String password) {
        for (User user: users) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(encrypt(password))) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gets password of a user.
     * 
     * @param username Username of a user.
     * @return User's password if user exists and
     * an empty string if does not.
     */
    public final String get(String username) {
        String password = "";
        for (User user: users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                password = user.getPassword();
                break;
            }
        }
        return password;
    }
    
    /**
     * Encrypts a message using SHA-256 encryption algorithm.
     * 
     * @param message Message to be encrypted.
     * @return Encrypted message on success and the plain message
     * on failure or if the message is null or empty.
     */
    public final String encrypt(String message) {
        if (null == message || message.trim().length() < 1) {
            return message;
        }
        
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String salt = "WXFL9";
            md.update((salt + message).getBytes());
            byte[] hashBytes = md.digest();
            
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < hashBytes.length; i++) {
                sb.append(Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch(NoSuchAlgorithmException ex) {
            return message;
        }
    }
    
    
    public Passwords(String fileName) {
        this.fileName = fileName;
        fileContent = "";
        users = new ArrayList<>();
        loadFile();
        loadUsers();
    }
    
    public static final class User {
        protected String username;
        protected String password;
        
        public final void setUsername(String username) throws Exception {
            if (username.isEmpty()) {
                throw new Exception("Username cannot be blank.");
            }
            if (!Pattern.matches("^\\w+$", username)) {
                throw new Exception("Invalid username. Only word characters are allowed.");
            }
            this.username = username;
        }
        
        public final void setPassword(String password) {
            this.password = password;
        }
        
        public final String getUsername() {
            return username;
        }
        
        public final String getPassword() {
            return password;
        }
        
        public User() {
            username = "";
            password = "";
        }
    }
}
