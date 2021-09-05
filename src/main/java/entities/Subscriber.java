package entities;

public class Subscriber implements Entity{
    private final Long id;
    private final String name;

    public Subscriber(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
