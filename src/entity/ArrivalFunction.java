import java.time.Duration;
import java.time.LocalDateTime;

public class ArrivalFunction {
    private LocalDateTime startTime;

    public ArrivalFunction() {
        startTime = LocalDateTime.now();
    }

    public int generateCars() {
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, currentTime);
        long secondsElapsed = duration.getSeconds();

        // Use secondsElapsed to calculate the number of cars to generate
        int numCars = (int) (Math.random() * (4 + secondsElapsed / 60));
        return numCars;
    }
}