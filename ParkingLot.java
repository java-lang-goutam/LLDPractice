/**

Design a parking lot
Constraints and assumptions

    What types of vehicles should we support?
        Motorcycle, Car, Bus
    Does each vehicle type take up a different amount of parking spots?
        Yes
        Motorcycle spot -> Motorcycle
        Compact spot -> Motorcycle, Car
        Large spot -> Motorcycle, Car
        Bus can park if we have 5 consecutive "large" spots
    Does the parking lot have multiple levels?
        Yes

*/

enum Space {
	SMALL(1), COMPACT(3), LARGE(5);

	private int minSpots;

	public Space(int minSpots) {
		this.minSpots = minSpots;
	}

	public getMinSpots() {
		return minSpots;
	}
}

interface Vehicle {
	String getRegistation();
	Space spaceRequired();
	Slot[] parkingSlots();
}

class MotorCycle implements Vehicle {

	final String registation;
	final Space minSpace = Space.SMALL;
	Slot[] slots;

	MotorCycle(final String registation) {
		this.registation = registation;
	}

	@Override
	String getRegistation() {
		return registation;
	}

	@Override
	Space spaceRequired() {
		return minSpace;
	}

	Slot[] parkingSlots() {
		return slots;
	}

	void setParkingSlots(Slot[] slots) {
		this.slots = slots;
	}
}

class Car implements Vehicle {

	final String registation;
	final Space minSpace = Space.COMPACT;
	Slot[] slots;

	Car(final String registation) {
		this.registation = registation;
	}

	@Override
	String getRegistation() {
		return registation;
	}

	@Override
	Space spaceRequired() {
		return minSpace;
	}

	Slot[] parkingSlots() {
		return slots;
	}

	void setParkingSlots(Slot[] slots) {
		this.slots = slots;
	}
}

class Bus implements Vehicle {

	final String registation;
	final Space minSpace = Space.COMPACT;
	Slot[] slots;

	Bus(final String registation) {
		this.registation = registation;
	}

	@Override
	String getRegistation() {
		return registation;
	}

	@Override
	Space spaceRequired() {
		return minSpace;
	}
	
	Slot[] parkingSlots() {
		return slots;
	}

	void setParkingSlots(Slot[] slots) {
		this.slots = slots;
	}
}

class NoParkingSpaceFoundException extends Exception {

	NoParkingSpaceFoundException(final String msg) {
		super(msg);
	}
}

public class Slot {

	private boolean available = true;
	private int id;
	private Vehicle vehicle;

	final Slot(final int id) {
		this.id = id;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public void getVehicle() {
		return this.vehicle;
	}

	public void availableNow() {
		this.available = true;
	}

	public void notAvailable() {
		this.available = false;
	}

	public boolean isAvailable() {
		return this.available;
	}

	public boolean equals(Obj o) {
		if (this == o) return true;
		if (o == null || o.getClass() != this.getClass()) return false;
		return o.id == this.id;
	}

	public int hashCode() {
		return Objects.hashCode(id);
	}
}


class ParkingService {

	private Slot[][] slots;

	ParkingService(final int totalLevels, final int maxCapacity) {
		slots = new Slot[totalLevels][maxCapacity];

		int count = 0;
		for (int i=0; i < totalLevels; i++) {
			for (int j=0; j<maxCapacity; j++) {
				slots[i][j] = new Slot(count++);
			}
		}
	}

	public boolean hasAvailableSpot(final Vehicle vehicle) {
		return getAvailableSlots != null;
	}

	private Slot[] getAvailableSlots(Vehicle vehicle) {
		int minSlotRequired = vehicle.spaceRequired().minSpots();
		for (int level = 0; level < slots.length; level++) {
			Slots[] currLevelSlots = slots[level];
			for (int i=0; i<currLevelSlots; i++) {
				int count = 0;
				while (currLevelSlots[count++].isAvailable());
				if (count >= minSlotRequired) {
					Slot[] res = new Slot[minSlotRequired];
					for (int s=0; s<minSlotRequired; s++) {
						res[s] = currLevelSlots[i++];
					}
					return res; 
				}
			}
		}
		return null;
	}

	public Slot[] park(final Vehicle vehicle) throws NoParkingSpaceFoundException{
		
		Slot[] slots = getAvailableSlots(vehicle);

		if (slots == null)
			throw new NoParkingSpaceFoundException("No available slot found !");

		for (Slot slot : slots) {
			slot.notAvailable();
			slot.setVehicle(vehicle);
		}
		vehicle.setParkingSlots(slots);

		return slot;
	}

	public void leave(final Vehicle vehicle) throws NoParkingSpaceFoundException{
		
		Slot[] slots = vehicle.parkingSlots();

		if (slots == null) return;

		for (Slot slot : slots) {
			slot.availableNow();
			slot.setVehicle(null);
		}
	}
}