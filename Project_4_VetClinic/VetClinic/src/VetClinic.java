import vetclinic.Dog;

/**
 * VetClinic class to test the Dog class.
 */
public class VetClinic {
    public static void main(String[] args) {
        Dog firstDog = new Dog();
        Dog secondDog = new Dog("Lucy", "Pit Bull Mix", 38.2, "Valerie");

        // Setting properties using setters
        firstDog.setName("Louie");
        firstDog.setBreed("Terrier");
        firstDog.setWeight(16.6);
        firstDog.setOwner("Dave");

        // Creating an array of Dogs
        Dog[] allDogs = new Dog[3];
        allDogs[0] = firstDog;
        allDogs[1] = secondDog;
        allDogs[2] = new Dog("Zuzu Sunshine", "Mystery Mix", 60.2, "Kate");

        // Printing all dogs in the array
        System.out.println("All dogs in the array:");
        for (Dog dog : allDogs) {
            System.out.println(dog.toString());
        }

        // Finding and printing the heaviest dog
        Dog heaviestDog = findHeaviestDog(allDogs);
        System.out.println("The heaviest dog is:");
        System.out.println(heaviestDog.toString());
    }

    /**
     * Finds the heaviest Dog in the array.
     * @param dogs Array of Dog objects.
     * @return Heaviest Dog.
     */
    public static Dog findHeaviestDog(Dog[] dogs) {
        Dog heaviest = dogs[0];
        for (Dog dog : dogs) {
            if (dog.getWeight() > heaviest.getWeight()) {
                heaviest = dog;
            }
        }
        return heaviest;
    }
}
