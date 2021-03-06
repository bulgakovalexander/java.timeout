package m4gshm.java.timeout;

import lombok.Getter;
import m4gshm.java.timeout.TimeLimitExecutor.DeadlineExceedConsumer;
import m4gshm.java.timeout.TimeLimitExecutor.DeadlineExceedFunction;

import java.time.Instant;


public class DeadlineExceededException extends RuntimeException {
    @Getter
    protected final Instant checkTime;
    @Getter
    private final Instant deadline;

    public DeadlineExceededException(Instant checkTime, Instant deadline) {
        super("Deadline exceed '" + deadline + "'. check time '" + checkTime + "'");
        this.checkTime = checkTime;
        this.deadline = deadline;
    }

    private final static DeadlineExceedFunction<?> throwFunc = (checkTime, deadline) -> {
        throw newDeadlineExceededException(checkTime, deadline);
    };

    static DeadlineExceededException newDeadlineExceededException(Instant checkTime, Instant deadline) {
        return new DeadlineExceededException(checkTime, deadline);
    }

    final static DeadlineExceedConsumer throwDefaultException = (checkTime, deadline) -> {
        throw newDeadlineExceededException(checkTime, deadline);
    };

    @SuppressWarnings("unchecked")
    static <T> DeadlineExceedFunction<T> throwDefaultException() {
        return (DeadlineExceedFunction<T>) throwFunc;
    }

}
