package com.emon.qwash.Room.Model;

    public class Chamber {
        private String email;
        private String hospital;
        private String address;
        private String time;
        private String con_number;

        public void setLast_updated(long last_updated) {
            this.last_updated = last_updated;
        }

        private long last_updated;

        // Add this getter
        public long getLastUpdated() {
            return last_updated;
        }

        public String getEmail() { return email; }
        public String getHospital() { return hospital; }
        public String getAddress() { return address; }
        public String getTime() { return time; }
        public String getCon_number() { return con_number; }
    }

