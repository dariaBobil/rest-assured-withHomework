package com.app.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Room {

        private String id;
        private String name;
        private String description;
        private String capacity;
        private String withTV;
        private String withWhiteBoard;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCapavity() {
            return capacity;
        }

        public void setCapavity(String capavity) {
            this.capacity = capavity;
        }

        public String getWithTV() {
            return withTV;
        }

        public void setWithTV(String withTV) {
            this.withTV = withTV;
        }

        public String getWithWhiteBoard() {
            return withWhiteBoard;
        }

        public void setWithWhiteBoard(String withWhiteBoard) {
            this.withWhiteBoard = withWhiteBoard;
        }

        @Override
        public String toString() {
            return "Room{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", capacity='" + capacity + '\'' +
                    ", withTV='" + withTV + '\'' +
                    ", withWhiteBoard='" + withWhiteBoard + '}';
        }

    }
