
/**
 * @author Teodor Yanchev
 */
public class Dog {

    private String name;
    private String breed;
    private double weight;
    private String owner;

    /**
     * Default constructor.
     */
    public Dog() {
        this.name = "";
        this.breed = "";
        this.weight = 0.0;
        this.owner = "";
    }

    /**
     * Initializing Constructor
     *
     * @param name The name of the dog
     * @param breed The breed of the dog
     * @param weight The weight of the dog
     * @param owner The name of the owner
     */
    public Dog(String name, String breed, double weight, String owner) {
        this.name = name;
        this.breed = breed;
        this.weight = weight;
        this.owner = owner;
    }

    // Getters and setters for Dog properties
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Returns a formatted string containing Dog's properties.
     *
     * @return Formatted string with Dog's properties.
     */
    public String toString() {
        return String.format("%s(%s), %.1f pounds, owned by %s", name, breed, weight, owner);
    }
}
