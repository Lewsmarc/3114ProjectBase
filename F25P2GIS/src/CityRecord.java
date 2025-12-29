
public class CityRecord {
    private String cityName;
    private int xCoord, yCoord;

    public CityRecord(String name, int x, int y) {
        cityName = name;
        xCoord = x;
        yCoord = y;
    }


    public String getName() {
        return cityName;
    }


    public int[] getLocation() {
        int[] point = new int[2];
        point[0] = xCoord;
        point[1] = yCoord;
        return point;
    }


    public int getX() {
        return xCoord;
    }


    public int getY() {
        return yCoord;
    }


    public boolean equals(Object obj) {
        if (obj instanceof CityRecord) {
            CityRecord compareCity = (CityRecord)obj;
            return cityName.compareTo(compareCity.getName()) == 0
                && xCoord == compareCity.getX() && yCoord == compareCity.getY();
        }
        return false;
    }
}
