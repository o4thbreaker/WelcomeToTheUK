package Model;

public class VisitedPlaces {

    private int id;
    private String city;
    private String name;
    private int isVisited;

    public VisitedPlaces()
    {

    }

    public VisitedPlaces(int id, String city, String name, int isVisited)
    {
        this.id = id;
        this.city = city;
        this.name = name;
        this.isVisited = isVisited;
    }

    public VisitedPlaces(String city, String name, int isVisited)
    {
        this.city = city;
        this.name = name;
        this.isVisited = isVisited;
    }

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public int getIsVisited() {
        return isVisited;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVisited(int isVisited) {
        this.isVisited = isVisited;
    }
}
