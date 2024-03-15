package application;

public class Novel implements Project {
    private String name;
    private String location;
    private String author;

    public Novel(String name, String location, String author) {
        this.name = name;
        this.location = location;
        this.author = author;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    // Add additional methods specific to Novel projects
}
