import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class InternalPanelAgent extends Thread {
    static class InternalCall {
        private final int toFloor;

        InternalCall(int toFloor) {
            this.toFloor = toFloor;
        }
    }

    InternalPanelAgent(ElevatorCar elevatorCar) {
        this.elevatorCar = elevatorCar;
    }

    BlockingQueue<InternalCall> input = new ArrayBlockingQueue<>(100);
    ElevatorCar elevatorCar;

    public void run() {
        for (; ; ) {
            // odczytaj wezwanie z kolejki
            // w zależności od aktualnego piętra, na którym jest winda,
            // umieść przystanek w odpowiedniej tablicy ''EleveatorStops''
            InternalCall ic = null;
            try {
                ic = input.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (ic.toFloor == elevatorCar.getFloor()) continue;
            if (ic.toFloor > elevatorCar.getFloor())
                ElevatorStops.get().stopsUp[ic.toFloor] = true;//hasStopAbove(ic.toFloor);
            if (ic.toFloor < elevatorCar.getFloor())
                ElevatorStops.get().stopsDown[ic.toFloor] = true;//.hasStopBelow(ic.toFloor);
        }
    }

}