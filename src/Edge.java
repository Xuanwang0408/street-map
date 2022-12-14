public class Edge {
    private String roadID;
    private Node inter1;
    private Node inter2;

    public Edge(String roadID, Node inter1, Node inter2) {
        this.roadID = roadID;
        this.inter1 = inter1;
        this.inter2 = inter2;
    }

    public String getRoadID() {
        return this.roadID;
    }

    public Node getInter1() {
        return this.inter1;
    }
    
    public Node getInter2() {
        return this.inter2;
    }

    public double getDistance() {
        double radius = 6371000.0;
        double lat1 = inter1.getLatitude() * (Math.PI / 180.0);
        double lat2 = inter2.getLatitude() * (Math.PI / 180.0);
        double differenceLat = lat1 - lat2;
        double differenceLong = (inter1.getLongitude() - inter2.getLongitude()) * (Math.PI / 180.0);
        double a = Math.pow(Math.sin(differenceLat / 2.0), 2)
                + (Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(differenceLong / 2.0), 2));
        double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a));
        double d = radius * c;

        return (d * 0.00062137119);
    }

    public Node getNeighbor(Node inter) {
        if (inter.getId() == inter1.getId()) {
            return inter2;
        } else if(inter.getId() == inter2.getId()){
            return inter1;
        } else {
            return null;
        }
    }

    public boolean contains(Node inter) {
        return inter.getId() == inter1.getId() || inter.getId() == inter2.getId();
    }
}
