package Model;

public class VisitedPlaces {

    private int id;
    private String city;
    private String name;
    private int isVisited;
    private double longitude;
    private double latitude;

    public VisitedPlaces()
    {

    }

    public VisitedPlaces(int id, String city, String name, int isVisited, double longitude, double latitude)
    {
        this.id = id;
        this.city = city;
        this.name = name;
        this.isVisited = isVisited;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public VisitedPlaces(String city, String name, int isVisited, double longitude, double latitude)
    {
        this.city = city;
        this.name = name;
        this.isVisited = isVisited;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
