package com.emon.qwash.ModelClass;

public class LoginResponse {
    private String status;
    private String message;
    private User user;

    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public User getUser() { return user; }

    public static class User {
        private String id;
        private String name;
        private String email;
        private String number;
        private String image;

        public String getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getNumber() { return number; }
        public String getImage() { return image; }
    }
}
