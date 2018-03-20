import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            Building building = new Building();
            building.addRoom("Кухня", 300, 3);
            building.addRoom("Спальня", 300, 3);
            building.addLamp(0, 501);
            building.addLamp(0, 502);
            building.addTable(0, "Стол", 30);
            building.addSofa(0, "Диван", 50);
            building.addArmchair(0, "Кресло", 50);
            building.addCupboard(0, "Шкаф", 50);
            building.addLamp(1, 905);
            building.getInfo();
        } catch (SpaceLimitExeption e) {
            e.printStackTrace();
        } catch (IlluminationLimitExeption e) {
            e.printStackTrace();
        }
    }
}

class Building {
    private List<Room> rooms = new ArrayList<Room>();

    void addRoom(String name, int area, int numberOfWindows) {
        rooms.add(new Room(name, area, numberOfWindows));
    }

    void addLamp(int numberOfRoom, int power) throws IlluminationLimitExeption {
        rooms.get(numberOfRoom).addLamp(power);
    }

    void addTable(int numberOfRoom, String name, int area) throws SpaceLimitExeption {
        rooms.get(numberOfRoom).addTable(name, area);
    }

    void addArmchair(int numberOfRoom, String name, int area) throws SpaceLimitExeption {
        rooms.get(numberOfRoom).addArmchair(name, area);
    }

    void addSofa(int numberOfRoom, String name, int area) throws SpaceLimitExeption {
        rooms.get(numberOfRoom).addSofa(name, area);
    }

    void addCupboard(int numberOfRoom, String name, int area) throws SpaceLimitExeption {
        rooms.get(numberOfRoom).addCupboard(name, area);
    }

    void getInfo() {
        for (Room room : rooms) {
            room.getInfo();
        }
    }
}

class Room {
    private String name;
    private int area;
    private int numberOfWindows;
    private int sumPowerOfLight;
    private int freeArea;
    private List<Lamp> lamps = new ArrayList<Lamp>();
    private List<Furniture> furnitures = new ArrayList<>();

    Room(String name, int area, int numberOfWindows) {
        this.name = name;
        this.area = area;
        this.freeArea = area;
        this.numberOfWindows = numberOfWindows;
        sumPowerOfLight = numberOfWindows * 700;
    }

    public void addTable(String name, int area) throws SpaceLimitExeption {
        if (((this.freeArea - area) * 100 / this.area) > 30) {
            furnitures.add(new Table(name, area));
            this.freeArea -= area;
        } else {
            String error = "Нельзя поставить '" + name + "' в комнату '" + this.name + "' т.к. бедет мало свободного места.";
            throw new SpaceLimitExeption(error);
        }
    }

    public void addArmchair(String name, int area) throws SpaceLimitExeption {
        if (((this.freeArea - area) * 100 / this.area) > 30) {
            furnitures.add(new Armchair(name, area));
            this.freeArea -= area;
        } else {
            String error = "Нельзя поставить '" + name + "' в комнату '" + this.name + "' т.к. бедет мало свободного места.";
            throw new SpaceLimitExeption(error);
        }
    }

    public void addSofa(String name, int area) throws SpaceLimitExeption {
        if (((this.freeArea - area) * 100 / this.area) > 30) {
            furnitures.add(new Sofa(name, area));
            this.freeArea -= area;
        } else {
            String error = "Нельзя поставить '" + name + "' в комнату '" + this.name + "' т.к. бедет мало свободного места.";
            throw new SpaceLimitExeption(error);
        }
    }

    public void addCupboard(String name, int area) throws SpaceLimitExeption {
        if (((this.freeArea - area) * 100 / this.area) > 30) {
            furnitures.add(new Cupboard(name, area));
            this.freeArea -= area;
        } else {
            String error = "Нельзя поставить '" + name + "' в комнату '" + this.name + "' т.к. бедет мало свободного места.";
            throw new SpaceLimitExeption(error);
        }
    }

    void addLamp(int power) throws IlluminationLimitExeption {
        if (sumPowerOfLight + power < 4001) {
            lamps.add(new Lamp(power));
            sumPowerOfLight += power;
        } else {
            String error = "Нельзя поставить больше освещения в комнату '" + this.name + "' т.к. бедет привышен лимит освещения.";
            throw new IlluminationLimitExeption(error);
        }
    }

    String listOfLamps() {
        String list = " ";
        if (lamps.isEmpty())
            return "отсутствуют";
        else {
            Iterator<Lamp> lampIterator = lamps.iterator();
            while (lampIterator.hasNext()) {
                list = list + " " + lampIterator.next().power + "лк. ";
            }
            return list;
        }
    }

    void getInfo() {
        System.out.printf("%s :\nОсвещенность=  %d (%d окна *700лк, лампочки : %s )\nПлощадь= %d м^2 Свободно -> %d м^2   %d ", name, sumPowerOfLight, numberOfWindows, listOfLamps(), area, freeArea, freeArea * 100 / area);
        System.out.print("% площади \n");
        System.out.print("Мебель:\n");
        if (furnitures.isEmpty())
            System.out.println("отсутствует\n");
        else {
            Iterator<Furniture> furnitureIterator = furnitures.iterator();
            while (furnitureIterator.hasNext()) {
                Furniture furniture = furnitureIterator.next();
                System.out.printf("%s (площадь %d м^2)   \n", furniture.name, furniture.area);
            }
        }
    }
}

class Lamp {
    int power;

    Lamp(int power) {
        this.power = power;
    }
}

abstract class Furniture {
    int area;
    String name;
}

class Table extends Furniture {
    Table(String name, int area) {
        this.name = name;
        this.area = area;
    }
}

class Sofa extends Furniture {
    Sofa(String name, int area) {
        this.name = name;
        this.area = area;
    }
}

class Armchair extends Furniture {
    Armchair(String name, int area) {
        this.name = name;
        this.area = area;
    }
}

class Cupboard extends Furniture {
    Cupboard(String name, int area) {
        this.name = name;
        this.area = area;
    }
}

class IlluminationLimitExeption extends Exception {
    IlluminationLimitExeption(String error) {
        super(error);
    }
}

class SpaceLimitExeption extends Exception {
    SpaceLimitExeption(String error) {
        super(error);
    }
}